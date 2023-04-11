package com.easy.query.core.sharding.route.datasource;

/**
 * create time 2023/4/11 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteEngine {
    DataSourceRouteResult route(DataSourceRouteContext routeContext);
}
