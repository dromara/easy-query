package com.easy.query.core.sharding.route;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/4/5 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ShardingRouteResult {

    private final List<RouteUnit> routeUnits;
    private final boolean isEmpty;
    private final boolean isCrossDataSource;
    private final boolean isCrossTable;

    public ShardingRouteResult(List<RouteUnit> routeUnits,  boolean isCrossDataSource, boolean isCrossTable){

        this.routeUnits = routeUnits;
        this.isEmpty = EasyCollectionUtil.isEmpty(routeUnits);
        this.isCrossDataSource = isCrossDataSource;
        this.isCrossTable = isCrossTable;
    }

    public List<RouteUnit> getRouteUnits() {
        return routeUnits;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isCrossDataSource() {
        return isCrossDataSource;
    }

    public boolean isCrossTable() {
        return isCrossTable;
    }

}
