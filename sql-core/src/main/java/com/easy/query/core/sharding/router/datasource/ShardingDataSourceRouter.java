package com.easy.query.core.sharding.router.datasource;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.sharding.router.RoutePredicateExpression;
import com.easy.query.core.sharding.router.datasource.abstraction.AbstractDataSourceRouter;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.util.EasyShardingUtil;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * create time 2023/4/19 13:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingDataSourceRouter extends AbstractDataSourceRouter {

    @Override
    public <T> Collection<String> route0(DataSourceRoute<T> dataSourceRoute, Collection<String> beforeTableNames, RouteDescriptor routeDescriptor) {
        RoutePredicateExpression<String> routePredicateExpression = EasyShardingUtil.getRoutePredicateExpression(routeDescriptor, dataSourceRoute, false);
        RouteFunction<String> routePredicate = routePredicateExpression.getRoutePredicate();
        return beforeTableNames.stream()
                .filter(routePredicate::apply)
                .collect(Collectors.toList());
    }
}
