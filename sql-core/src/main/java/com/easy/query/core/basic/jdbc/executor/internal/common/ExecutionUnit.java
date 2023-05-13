package com.easy.query.core.basic.jdbc.executor.internal.common;

/**
 * create time 2023/4/13 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ExecutionUnit {
    private final String dataSourceName;
    private final SqlRouteUnit sqlRouteUnit;

    public ExecutionUnit(String dataSourceName,SqlRouteUnit sqlRouteUnit) {
        this.dataSourceName = dataSourceName;
        this.sqlRouteUnit = sqlRouteUnit;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public SqlRouteUnit getSqlRouteUnit() {
        return sqlRouteUnit;
    }
}
