package com.easy.query.core.sharding.merge;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;

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
    private final Map<String/* data source name*/, Collection<CloseableConnection>> closeableDataSourceConnections =new HashMap<>();
    private final ExecutorContext executorContext;
    private final ExecutionContext executionContext;
    private final EasyConnectionManager connectionManager;

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
        this.connectionManager=runtimeContext.getConnectionManager();
        this.serialExecute= executorContext.isSerialExecute();
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
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

    /**
     * 创建执行所需的connection
     * 如果是并发操作那么将获取独立的connection
     * 如果是非并发顺序操作那么将使用当前共享的connection
     * 并发与否就是是否分表聚合查询
     * @param connectionMode
     * @param dataSourceName
     * @param createDbConnectionCount
     * @return
     */
    public List<EasyConnection> getEasyConnections(ConnectionModeEnum connectionMode, String dataSourceName, int createDbConnectionCount){
        List<EasyConnection> easyConnections = new ArrayList<>(createDbConnectionCount);
        //当前需要被回收的链接
        Collection<CloseableConnection> closeableConnections = this.closeableDataSourceConnections.computeIfAbsent(dataSourceName, o -> new ArrayList<>());
        ConnectionStrategyEnum connectionStrategy = getConnectionStrategy(createDbConnectionCount);
        for (int i = 0; i < createDbConnectionCount; i++) {
            EasyConnection easyConnection = connectionManager.getEasyConnection(dataSourceName, connectionStrategy);

            easyConnections.add(easyConnection);
            closeableConnections.add(new CloseableConnection(connectionStrategy,connectionManager,easyConnection));
        }
        return easyConnections;
    }
    private ConnectionStrategyEnum getConnectionStrategy(int createDbConnectionCount){
        if(createDbConnectionCount<=0){
            throw new EasyQueryInvalidOperationException("cant get connection strategy");
        }
        if(createDbConnectionCount==1){
            return ConnectionStrategyEnum.ShareConnection;
        }
        return ConnectionStrategyEnum.IndependentConnectionMaster;

    }

    @Override
    public void close() throws Exception {
        for (Collection<CloseableConnection> value : closeableDataSourceConnections.values()) {
            for (CloseableConnection closeableConnection : value) {
                closeableConnection.close();
            }
        }
    }

    /**
     * 可关闭的链接用于当前请求链接是否关闭
     */
    public static class CloseableConnection implements AutoCloseable{
        private final ConnectionStrategyEnum connectionStrategy;
        private final EasyConnectionManager easyConnectionManager;
        private final EasyConnection easyConnection;

        public CloseableConnection(ConnectionStrategyEnum connectionStrategy, EasyConnectionManager easyConnectionManager, EasyConnection easyConnection){

            this.connectionStrategy = connectionStrategy;
            this.easyConnectionManager = easyConnectionManager;
            this.easyConnection = easyConnection;
        }

        @Override
        public void close() throws Exception {
            if(ConnectionStrategyEnum.ShareConnection.equals(connectionStrategy)){
                easyConnectionManager.closeEasyConnection(easyConnection);
            }else{
                easyConnection.close();
            }
        }
    }
}
