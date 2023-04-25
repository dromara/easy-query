package com.easy.query.core.sharding.route.table;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.abstraction.AbstractFilterTableRoute;
import com.easy.query.core.sharding.rule.table.TableRouteRule;
import com.easy.query.core.util.ShardingUtil;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * create time 2023/4/18 23:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingTableRoute extends AbstractFilterTableRoute {

    public ShardingTableRoute(EntityMetadataManager entityMetadataManager) {
        super(entityMetadataManager);
    }

    @Override
    public Collection<TableRouteUnit> route0(TableRouteRule tableRouteRule,EntityMetadata entityMetadata,DataSourceRouteResult dataSourceRouteResult, Collection<String> beforeTableNames, PrepareParseResult prepareParseResult) {
        RoutePredicateExpression routePredicateExpression = ShardingUtil.getRoutePredicateExpression(prepareParseResult, entityMetadata, tableRouteRule, true);
        RouteFunction<String> routePredicate = routePredicateExpression.getRoutePredicate();
        return filterTableNameWithDataSourceResult(dataSourceRouteResult, beforeTableNames)
                .filter(routePredicate::apply)
                .map(o->parseRouteWithTableName(entityMetadata,o))
                .collect(Collectors.toList());
    }

    protected Stream<String> filterTableNameWithDataSourceResult(DataSourceRouteResult dataSourceRouteResult, Collection<String> beforeTableNames) {
        return beforeTableNames.stream().filter(tableName -> {
            String dataSourceName = parseDataSourceWithTableName(tableName);
            return dataSourceRouteResult.getIntersectDataSources().contains(dataSourceName);
        });
    }

    protected String parseDataSourceWithTableName(String tableName) {
        return tableName.split("\\.")[0];
    }

    protected TableRouteUnit parseRouteWithTableName(EntityMetadata entityMetadata,String tableName) {
        if (!tableName.contains(".")) {
            throw new EasyQueryInvalidOperationException("table name not full name eg.(dataSource.tableName) :" + tableName);
        }
        String[] tableInfos = tableName.split("\\.");
        return new BaseTableRouteUnit(tableInfos[0],entityMetadata.getTableName() ,tableInfos[1],entityMetadata.getEntityClass());
    }

}
