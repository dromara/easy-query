package com.easy.query.core.sharding.router;

import com.easy.query.core.sharding.router.table.TableRouteUnit;

import java.util.List;

/**
 * create time 2023/4/5 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class RouteUnit {
    private final String dataSource;
    private final List<TableRouteUnit> tableRouteUnits;

    public RouteUnit(String dataSource, List<TableRouteUnit> tableRouteUnits){
        this.dataSource = dataSource;

        this.tableRouteUnits = tableRouteUnits;
    }

    public String getDataSource() {
        return dataSource;
    }

    public List<TableRouteUnit> getTableRouteUnits() {
        return tableRouteUnits;
    }
}
