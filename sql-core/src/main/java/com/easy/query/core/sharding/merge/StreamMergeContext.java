package com.easy.query.core.sharding.merge;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/4/13 11:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class StreamMergeContext {
    private final List<ExecutionUnit> executionUnits;
    private final EntityExpression entityExpression;
    private final EasyQueryRuntimeContext runtimeContext;
    private final boolean serialExecute;
    private final Map<String/* data source name*/,EasyConnection> parallelConnections=new HashMap<>();

    public StreamMergeContext(List<ExecutionUnit> executionUnits, EntityExpression entityExpression){

        this.executionUnits = executionUnits;
        this.entityExpression = entityExpression;
        this.runtimeContext=entityExpression.getRuntimeContext();
        serialExecute=!(entityExpression instanceof EntityQueryExpression);
    }

    public List<ExecutionUnit> getExecutionUnits() {
        return executionUnits;
    }

    public EntityExpression getEntityExpression() {
        return entityExpression;
    }

    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public boolean isSerialExecute() {
        return serialExecute;
    }
    public boolean isSeqQuery(){
        return false;
    }

    public List<EasyConnection> getEasyConnections(ConnectionModeEnum connectionMode,String dataSourceName,int createDbConnectionCount){
        return null;
    }
}
