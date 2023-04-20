package com.easy.query.core.sharding.merge.executor.common;

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
    private final PreparedStatement preparedStatement;
    private final ConnectionModeEnum connectionMode;
    private final CommandTypeEnum commandType;

    public CommandExecuteUnit(ExecutionUnit executionUnit, PreparedStatement preparedStatement, ConnectionModeEnum connectionMode, CommandTypeEnum commandType) {
        this.executionUnit = executionUnit;
        this.preparedStatement = preparedStatement;
        this.connectionMode = connectionMode;
        this.commandType = commandType;
    }

    public ExecutionUnit getExecutionUnit() {
        return executionUnit;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public CommandTypeEnum getCommandType() {
        return commandType;
    }
}
