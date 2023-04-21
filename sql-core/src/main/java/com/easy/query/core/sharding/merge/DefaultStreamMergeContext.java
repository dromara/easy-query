package com.easy.query.core.sharding.merge;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.query.QueryCompilerContext;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;
import com.easy.query.core.sharding.route.ShardingRouteResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2023/4/17 12:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamMergeContext implements StreamMergeContext {
//    private final List<ExecutionUnit> executionUnits;
//    private final ShardingRouteResult shardingRouteResult;
//    private final EntityExpression entityExpression;
    private final EasyQueryRuntimeContext runtimeContext;
    private final boolean serialExecute;
    private final Map<String/* data source name*/, Collection<EasyConnection>> parallelConnections=new HashMap<>();
    private final ExecutorContext executorContext;
    private final ExecutionContext executionContext;

    //    public DefaultStreamMergeContext(List<ExecutionUnit> executionUnits, EntityExpression entityExpression){
//
//        this.executionUnits = executionUnits;
//        this.entityExpression = entityExpression;
//        this.runtimeContext=entityExpression.getRuntimeContext();
//        serialExecute=!(entityExpression instanceof EntityQueryExpression);
//    }
    public DefaultStreamMergeContext(ExecutorContext executorContext, ExecutionContext executionContext){
        this.executorContext = executorContext;
        this.executionContext = executionContext;
//        this.shardingRouteResult = shardingRouteResult;
//
//        this.entityExpression = entityExpression;
        this.runtimeContext= executorContext.getRuntimeContext();
        serialExecute= !Objects.equals(CommandTypeEnum.QUERY,executionContext.getCommandType());
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public CommandTypeEnum getCommandType() {
        return executionContext.getCommandType();
    }

    public Collection<ExecutionUnit> getExecutionUnits() {
        return executionContext.getExecutionUnits();
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

    public List<EasyConnection> getEasyConnections(ConnectionModeEnum connectionMode, String dataSourceName, int createDbConnectionCount){
        List<EasyConnection> easyConnections = new ArrayList<>(createDbConnectionCount);

        for (int i = 0; i < createDbConnectionCount; i++) {
            EasyConnection easyConnection = runtimeContext.getConnectionManager().getEasyConnection(dataSourceName, ConnectionStrategyEnum.IndependentConnectionMaster);
            Collection<EasyConnection> connections = parallelConnections.computeIfAbsent(dataSourceName, o -> new ArrayList<>());
            connections.add(easyConnection);
            easyConnections.add(easyConnection);
        }
        return easyConnections;
    }

    @Override
    public void close() throws Exception {
        for (String s : parallelConnections.keySet()) {
            Collection<EasyConnection> easyConnections = parallelConnections.get(s);
            for (EasyConnection easyConnection : easyConnections) {
                easyConnection.close();
            }
        }
    }
}
