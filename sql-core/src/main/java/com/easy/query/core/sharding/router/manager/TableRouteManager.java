package com.easy.query.core.sharding.router.manager;

import com.easy.query.core.sharding.router.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.router.table.TableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRoute;

import java.util.Collection;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouteManager {
    Collection<TableRouteUnit> routeTo(DataSourceRouteResult dataSourceRouteResult, RouteDescriptor routeDescriptor);
    TableRoute<?> getRoute(Class<?> entityClass);
    boolean addRoute(TableRoute<?> tableRoute);
}
