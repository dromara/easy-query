package com.easy.query.core.sharding.merge.executor.common;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.sharding.common.RouteUnit;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;

import java.sql.PreparedStatement;

/**
 * create time 2023/4/13 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public final class CommandExecuteUnit {
    private final ExecutionUnit executionUnit;
    private final EasyConnection easyConnection;
    private final ConnectionModeEnum connectionMode;

    public CommandExecuteUnit(ExecutionUnit executionUnit, EasyConnection easyConnection, ConnectionModeEnum connectionMode) {
        this.executionUnit = executionUnit;
        this.easyConnection = easyConnection;
        this.connectionMode = connectionMode;
    }

    public ExecutionUnit getExecutionUnit() {
        return executionUnit;
    }


    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public EasyConnection getEasyConnection() {
        return easyConnection;
    }
}
