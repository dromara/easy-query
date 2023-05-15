package com.easy.query.core.basic.jdbc.executor.internal.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.CommandQueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

/**
 * create time 2023/5/13 00:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCommandQueryExecuteResult extends DefaultQueryExecuteResult implements CommandQueryExecuteResult {
    private final CommandExecuteUnit commandExecuteUnit;

    public DefaultCommandQueryExecuteResult(StreamResultSet streamResultSet, CommandExecuteUnit commandExecuteUnit) {
        super(streamResultSet);
        this.commandExecuteUnit = commandExecuteUnit;
    }

    @Override
    public CommandExecuteUnit getCommandExecuteUnit() {
        return commandExecuteUnit;
    }
}
