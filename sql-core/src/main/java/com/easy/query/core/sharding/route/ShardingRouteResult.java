package com.easy.query.core.sharding.route;

import com.easy.query.core.sharding.common.SqlRouteUnit;

import java.util.List;

/**
 * create time 2023/4/5 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ShardingRouteResult {

    private final List<SqlRouteUnit> routeUnits;
    private final boolean isEmpty;
    private final boolean isCrossDataSource;
    private final boolean isCrossTable;
    private final boolean existCrossTableTails;

    public ShardingRouteResult(List<SqlRouteUnit> routeUnits, boolean isEmpty, boolean isCrossDataSource, boolean isCrossTable, boolean existCrossTableTails){

        this.routeUnits = routeUnits;
        this.isEmpty = isEmpty;
        this.isCrossDataSource = isCrossDataSource;
        this.isCrossTable = isCrossTable;
        this.existCrossTableTails = existCrossTableTails;
    }

    public List<SqlRouteUnit> getRouteUnits() {
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

    public boolean isExistCrossTableTails() {
        return existCrossTableTails;
    }
}
