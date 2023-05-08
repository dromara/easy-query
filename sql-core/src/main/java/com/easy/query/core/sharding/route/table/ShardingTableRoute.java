package com.easy.query.core.sharding.route.table;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.abstraction.AbstractTableRoute;
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
public class ShardingTableRoute extends AbstractTableRoute {


    @Override
    public <T> Collection<TableRouteUnit> route0(TableRouteRule<T> tableRouteRule, TableAvailable table, DataSourceRouteResult dataSourceRouteResult, Collection<String> beforeTableNames, PrepareParseResult prepareParseResult) {
        RoutePredicateExpression routePredicateExpression = ShardingUtil.getRoutePredicateExpression(prepareParseResult, table, tableRouteRule, true);
        RouteFunction<String> routePredicate = routePredicateExpression.getRoutePredicate();
        return filterTableNameWithDataSourceResult(dataSourceRouteResult, beforeTableNames)
                .filter(routePredicate::apply)
                .map(o->parseRouteWithTableName(table,o))
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

    protected TableRouteUnit parseRouteWithTableName(TableAvailable table,String actualTableName) {
        if (!actualTableName.contains(".")) {
            throw new EasyQueryInvalidOperationException("table name not full name eg.(dataSource.tableName) :" + actualTableName);
        }
        String[] tableInfos = actualTableName.split("\\.");
        return new BaseTableRouteUnit(tableInfos[0],table.getTableName() ,tableInfos[1],table.getEntityClass(),table.getIndex());
    }

}
