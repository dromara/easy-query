package com.easy.query.core.sharding.route.table;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/25 14:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class BaseTableRouteUnit implements TableRouteUnit{
    private final String dataSource;
    private final String logicTableName;
    private final TableAvailable table;
    private final String actualTableName;

    public BaseTableRouteUnit(String dataSource, String actualTableName, TableAvailable table){

        this.dataSource = dataSource;
        this.actualTableName = actualTableName;
        this.logicTableName = table.getTableName();
        this.table = table;
    }

    public String getDataSourceName() {
        return dataSource;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    public String getLogicTableName() {
        return logicTableName;
    }

    public String getActualTableName() {
        return actualTableName;
    }

}
