package com.easy.query.core.sharding.router.table;

import com.easy.query.core.sharding.router.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.route.table.TableRoute;

import java.util.Collection;

/**
 * create time 2023/4/5 13:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouter {
    <T> Collection<TableRouteUnit> route(TableRoute<T> tableRoute, DataSourceRouteResult dataSourceRouteResult, RouteDescriptor routeDescriptor);
}
