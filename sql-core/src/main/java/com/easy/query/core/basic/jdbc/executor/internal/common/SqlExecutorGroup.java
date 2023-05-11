package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.enums.sharding.ConnectionModeEnum;

import java.util.List;

/**
 * create time 2023/4/13 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public final class SqlExecutorGroup<T> {
    private final ConnectionModeEnum connectionMode;
    private final List<T> groups;

    public SqlExecutorGroup(ConnectionModeEnum connectionMode, List<T> groups){

        this.connectionMode = connectionMode;
        this.groups = groups;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public List<T> getGroups() {
        return groups;
    }
}
