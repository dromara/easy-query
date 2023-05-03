package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;

import java.util.Collection;


/**
 * create time 2023/4/18 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExecutionContext {
//    private final CommandTypeEnum commandType;
    private final Collection<ExecutionUnit> executionUnits;

    public ExecutionContext(Collection<ExecutionUnit> executionUnits){
//        this.commandType = commandType;

        this.executionUnits = executionUnits;
    }


    public Collection<ExecutionUnit> getExecutionUnits() {
        return executionUnits;
    }

//    public CommandTypeEnum getCommandType() {
//        return commandType;
//    }
}
