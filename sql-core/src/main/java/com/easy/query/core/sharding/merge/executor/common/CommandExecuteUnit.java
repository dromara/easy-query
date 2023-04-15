package com.easy.query.core.sharding.merge.executor.common;

import com.easy.query.core.sharding.common.SqlRouteUnit;
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
    private final SqlRouteUnit sqlRouteUnit;
    private final PreparedStatement preparedStatement;
    private final ConnectionModeEnum connectionMode;
    private final CommandTypeEnum commandType;

    public CommandExecuteUnit(SqlRouteUnit sqlRouteUnit, PreparedStatement preparedStatement, ConnectionModeEnum connectionMode, CommandTypeEnum commandType) {
        this.sqlRouteUnit = sqlRouteUnit;
        this.preparedStatement = preparedStatement;
        this.connectionMode = connectionMode;
        this.commandType = commandType;
    }

    public SqlRouteUnit getSqlRouteUnit() {
        return sqlRouteUnit;
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
