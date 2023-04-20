package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;

import java.util.Collection;


/**
 * create time 2023/4/18 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExecutionContext {
    private final CommandTypeEnum commandType;
    private final Collection<ExecutionUnit> executionUnits;

    public ExecutionContext(CommandTypeEnum commandType, Collection<ExecutionUnit> executionUnits){
        this.commandType = commandType;

        this.executionUnits = executionUnits;
    }


    public Collection<ExecutionUnit> getExecutionUnits() {
        return executionUnits;
    }

    public CommandTypeEnum getCommandType() {
        return commandType;
    }
}
