package com.easy.query.core.basic.jdbc.executor.internal.common;

/**
 * create time 2023/4/13 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ExecutionUnit {
    private final String dataSourceName;
    private final SQLRouteUnit sqlRouteUnit;

    public ExecutionUnit(String dataSourceName, SQLRouteUnit sqlRouteUnit) {
        this.dataSourceName = dataSourceName;
        this.sqlRouteUnit = sqlRouteUnit;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public SQLRouteUnit getSQLRouteUnit() {
        return sqlRouteUnit;
    }
}
