package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.enums.sharding.ConnectionModeEnum;

import java.util.List;

/**
 * create time 2023/4/13 22:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class DataSourceSQLExecutorUnit {
    private final String dataSourceName;
    private final ConnectionModeEnum connectionMode;
    private final List<SQLExecutorGroup<CommandExecuteUnit>> sqlExecutorGroups;

    public DataSourceSQLExecutorUnit(String dataSourceName,ConnectionModeEnum connectionMode, List<SQLExecutorGroup<CommandExecuteUnit>> sqlExecutorGroups) {
        this.dataSourceName = dataSourceName;
        this.connectionMode = connectionMode;
        this.sqlExecutorGroups = sqlExecutorGroups;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public List<SQLExecutorGroup<CommandExecuteUnit>> getSQLExecutorGroups() {
        return sqlExecutorGroups;
    }
}
