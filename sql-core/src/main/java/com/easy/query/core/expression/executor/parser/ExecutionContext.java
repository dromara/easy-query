package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;

import java.util.Collection;
import java.util.List;


/**
 * create time 2023/4/18 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExecutionContext {
//    private final CommandTypeEnum commandType;
    private final List<ExecutionUnit> executionUnits;
    private final boolean sequenceQuery;
    private final boolean isCrossDataSource;
    private final boolean isCrossTable;

    public ExecutionContext(List<ExecutionUnit> executionUnits,boolean sequenceQuery,boolean isCrossTable,boolean isCrossDataSource){

        this.executionUnits = executionUnits;
        this.sequenceQuery = sequenceQuery;
        this.isCrossTable = isCrossTable;
        this.isCrossDataSource = isCrossDataSource;
    }


    public List<ExecutionUnit> getExecutionUnits() {
        return executionUnits;
    }
    public boolean isSequenceQuery() {
        return sequenceQuery;
    }

    public boolean isCrossDataSource() {
        return isCrossDataSource;
    }

    public boolean isCrossTable() {
        return isCrossTable;
    }
}
