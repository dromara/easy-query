package com.easy.query.core.sharding.merge.executor.common;

/**
 * create time 2023/4/13 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ExecutionUnit {
    private final String dataSourceName;
    private final SqlUnit sqlUnit;

    public ExecutionUnit(String dataSourceName, SqlUnit sqlUnit) {
        this.dataSourceName = dataSourceName;
        this.sqlUnit = sqlUnit;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public SqlUnit getSqlUnit() {
        return sqlUnit;
    }
}
