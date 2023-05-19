package com.easy.query.core.sharding.route.table;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.route.table.abstraction.AbstractTableRoute;
import com.easy.query.core.sharding.rule.table.TableRouteRule;
import com.easy.query.core.util.EasyShardingUtil;

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
    public <T> Collection<TableRouteUnit> route0(TableRouteRule<T> tableRouteRule, DataSourceRouteResult dataSourceRouteResult, Collection<ActualTable> beforeTableNames, RouteDescriptor routeDescriptor) {
        RoutePredicateExpression<ActualTable> routePredicateExpression = EasyShardingUtil.getRoutePredicateExpression(routeDescriptor, tableRouteRule, true);
        RouteFunction<ActualTable> routePredicate = routePredicateExpression.getRoutePredicate();
        TableAvailable table = routeDescriptor.getTable();
        return filterTableNameWithDataSourceResult(dataSourceRouteResult, beforeTableNames)
                .filter(routePredicate::apply)
                .map(o->parseRouteWithTableName(table,o))
                .collect(Collectors.toList());
    }

    protected Stream<ActualTable> filterTableNameWithDataSourceResult(DataSourceRouteResult dataSourceRouteResult, Collection<ActualTable> beforeActualTables) {
        return beforeActualTables.stream().filter(actualTable -> {
            return dataSourceRouteResult.getIntersectDataSources().contains(actualTable.getDataSourceName());
        });
    }

    protected TableRouteUnit parseRouteWithTableName(TableAvailable table,ActualTable actualTable) {
        return new BaseTableRouteUnit(actualTable.getDataSourceName(),actualTable.getActualTableName(),table);
    }

}
