package com.easy.query.core.sharding.route.datasource;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.route.datasource.abstraction.AbstractDataSourceRoute;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;
import com.easy.query.core.util.ShardingUtil;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * create time 2023/4/19 13:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingDataSourceRoute extends AbstractDataSourceRoute {

    @Override
    public <T> Collection<String> route0(DataSourceRouteRule<T> dataSourceRouteRule, TableAvailable table, Collection<String> beforeTableNames, PrepareParseResult prepareParseResult) {
        RoutePredicateExpression<String> routePredicateExpression = ShardingUtil.getRoutePredicateExpression(prepareParseResult, table, dataSourceRouteRule, false);
        RouteFunction<String> routePredicate = routePredicateExpression.getRoutePredicate();
        return beforeTableNames.stream()
                .filter(routePredicate::apply)
                .collect(Collectors.toList());
    }
}
