package com.easy.query.core.sharding.router.manager;

import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;

import java.util.Collection;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteManager {
    Collection<String> routeTo(RouteDescriptor routeDescriptor);
    DataSourceRoute<?> getRoute(Class<?> entityClass);
    boolean addRoute(DataSourceRoute<?> dataSourceRoute);
}
