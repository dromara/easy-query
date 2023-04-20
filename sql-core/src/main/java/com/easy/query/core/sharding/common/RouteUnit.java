package com.easy.query.core.sharding.common;

import java.util.List;

/**
 * create time 2023/4/5 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class RouteUnit {
    private final String dataSource;
    private final List<RouteMapper> routeMappers;

    public RouteUnit(String dataSource, List<RouteMapper> routeMappers){
        this.dataSource = dataSource;

        this.routeMappers = routeMappers;
    }

    public String getDataSource() {
        return dataSource;
    }

    public List<RouteMapper> getRouteMappers() {
        return routeMappers;
    }
}
