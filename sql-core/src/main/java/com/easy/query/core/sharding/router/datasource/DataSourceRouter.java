package com.easy.query.core.sharding.router.datasource;

import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;

import java.util.Collection;

/**
 * create time 2023/4/12 12:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouter {
    <T> Collection<String> route(DataSourceRoute<T> dataSourceRoute, RouteDescriptor routeDescriptor);
}
