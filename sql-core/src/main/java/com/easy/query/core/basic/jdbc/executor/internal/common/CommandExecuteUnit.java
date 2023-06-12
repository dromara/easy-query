package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.basic.jdbc.conn.EasyConnection;

/**
 * create time 2023/4/13 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public final class CommandExecuteUnit {
    private final ExecutionUnit executionUnit;
    private final EasyConnection easyConnection;

    public CommandExecuteUnit(ExecutionUnit executionUnit, EasyConnection easyConnection) {
        this.executionUnit = executionUnit;
        this.easyConnection = easyConnection;
    }

    public ExecutionUnit getExecutionUnit() {
        return executionUnit;
    }

    public EasyConnection getEasyConnection() {
        return easyConnection;
    }
}
