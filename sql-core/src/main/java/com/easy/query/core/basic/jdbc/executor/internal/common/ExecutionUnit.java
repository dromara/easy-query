package com.easy.query.core.basic.jdbc.executor.internal.common;

/**
 * create time 2023/4/13 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ExecutionUnit {
    private final String dataSourceName;
    private final int index;
    private final SqlRouteUnit sqlRouteUnit;

    public ExecutionUnit(String dataSourceName,int index, SqlRouteUnit sqlRouteUnit) {
        this.dataSourceName = dataSourceName;
        this.index = index;
        this.sqlRouteUnit = sqlRouteUnit;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public SqlRouteUnit getSqlRouteUnit() {
        return sqlRouteUnit;
    }

    public int getIndex() {
        return index;
    }
}
