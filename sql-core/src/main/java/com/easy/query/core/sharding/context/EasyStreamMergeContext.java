package com.easy.query.core.sharding.context;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.enums.replica.ReplicaBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyOrder;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/4/17 12:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyStreamMergeContext implements StreamMergeContext {
    private static final Log log= LogFactory.getLog(EasyStreamMergeContext.class);
    protected final QueryRuntimeContext runtimeContext;
    protected final boolean isQuery;
    protected final Map<String/* data source name*/, Collection<CloseableConnection>> closeableDataSourceConnections = new HashMap<>();
    protected final ExecutorContext executorContext;
    protected final ExecutionContext executionContext;
    protected final EasyConnectionManager connectionManager;
    protected final EasyQueryOption easyQueryOption;

    public EasyStreamMergeContext(ExecutorContext executorContext, ExecutionContext executionContext) {
        this.executorContext = executorContext;
        this.executionContext = executionContext;
        this.runtimeContext = executorContext.getRuntimeContext();
        this.connectionManager = runtimeContext.getConnectionManager();
        this.isQuery = executorContext.isQuery();
        this.easyQueryOption = runtimeContext.getEasyQueryConfiguration().getEasyQueryOption();
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public boolean hasBehavior(MergeBehaviorEnum mergeBehavior) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSharding() {
        return false;
    }

    @Override
    public boolean configReplica() {
        return false;
    }

    @Override
    public EasyQueryOption getEasyQueryOption() {
        return easyQueryOption;
    }

    @Override
    public ConnectionModeEnum getConnectionMode() {
        return ConnectionModeEnum.SYSTEM_AUTO;
    }

    @Override
    public void terminatedBreak() {

    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public List<PropertyOrder> getOrders() {
        throw new NotImplementedException();
    }

    @Override
    public List<PropertyGroup> getGroups() {
        throw new NotImplementedException();
    }

    public List<ExecutionUnit> getExecutionUnits() {
        return executionContext.getExecutionUnits();
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public int getMaxShardingQueryLimit() {
        return 1;
    }

    public boolean isQuery() {
        return isQuery;
    }

    @Override
    public boolean isSeqQuery() {
        return false;
    }

    @Override
    public boolean isReverseMerge() {
        throw new UnsupportedOperationException();
    }

    /**
     * 创建执行所需的connection
     * 如果是并发操作那么将获取独立的connection
     * 如果是非并发顺序操作那么将使用当前共享的connection
     * 并发与否就是是否分表聚合查询
     *
     * @param connectionMode
     * @param dataSourceName
     * @param createDbConnectionCount
     * @return
     */
    public List<EasyConnection> getEasyConnections(ConnectionModeEnum connectionMode, String dataSourceName, int createDbConnectionCount) {
        List<EasyConnection> easyConnections = new ArrayList<>(createDbConnectionCount);
        //当前需要被回收的链接
        Collection<CloseableConnection> closeableConnections = this.closeableDataSourceConnections.computeIfAbsent(dataSourceName, o -> new ArrayList<>());
        ConnectionStrategyEnum connectionStrategy = getConnectionStrategy(createDbConnectionCount);
        for (int i = 0; i < createDbConnectionCount; i++) {
            EasyConnection easyConnection = connectionManager.getEasyConnection(dataSourceName, connectionStrategy);

            easyConnections.add(easyConnection);
            closeableConnections.add(new CloseableConnection(connectionStrategy, connectionManager, easyConnection));
        }
        return easyConnections;
    }

    private ConnectionStrategyEnum getConnectionStrategy(int createDbConnectionCount) {
        if (createDbConnectionCount <= 0) {
            throw new EasyQueryInvalidOperationException("cant get connection strategy");
        }
        //判断是否采用共享链接需要满足当前没有分片或者当前分片了但是是串行或者不是串行
        if (!isSharding()|| !isQuery()) {
            return ConnectionStrategyEnum.ShareConnection;
        }
        if(easyQueryOption.getReplicaOption()!=null){
            ReplicaBehaviorEnum replicaBehavior = easyQueryOption.getReplicaOption().getReplicaBehavior();
            if(ReplicaBehaviorEnum.DefaultEnable.equals(replicaBehavior)||(ReplicaBehaviorEnum.OutTransactionEnable.equals(replicaBehavior)&&!connectionManager.currentThreadInTransaction())){
                return ConnectionStrategyEnum.IndependentConnectionReplica;
            }
        }
        //那么就执行一条sql一张表就需要没有跨库跨表
        if(createDbConnectionCount == 1|| (!executionContext.isCrossTable()&&!executionContext.isCrossDataSource())){
            return ConnectionStrategyEnum.ShareConnection;
        }
        return ConnectionStrategyEnum.IndependentConnectionMaster;

    }

    @Override
    public boolean isPaginationQuery() {
        throw new NotImplementedException();
    }

    @Override
    public long getOriginalOffset() {
        throw new NotImplementedException();
    }

    @Override
    public long getOriginalRows() {
        throw new NotImplementedException();
    }

    @Override
    public long getMergeOffset() {
        throw new NotImplementedException();
    }

    @Override
    public long getMergeRows() {
        throw new NotImplementedException();
    }

    @Override
    public long getRewriteOffset() {
        throw new NotImplementedException();
    }

    @Override
    public long getRewriteRows() {
        throw new NotImplementedException();
    }

    @Override
    public SQLBuilderSegment getSelectColumns() {
        throw new NotImplementedException();
    }

    @Override
    public SQLBuilderSegment getGroupColumns() {
        throw new NotImplementedException();
    }

    @Override
    public ExecuteMethodEnum getExecuteMethod() {
        return executorContext.getExecuteMethod();
    }

    @Override
    public void close() throws Exception {
        for (Collection<CloseableConnection> value : closeableDataSourceConnections.values()) {
            for (CloseableConnection closeableConnection : value) {
               try {
                   closeableConnection.close();
               }catch (Exception ex){
                   log.error("close stream merge context error.",ex);
               }
            }
        }
    }

    /**
     * 可关闭的链接用于当前请求链接是否关闭
     */
    public static class CloseableConnection implements AutoCloseable {
        private final ConnectionStrategyEnum connectionStrategy;
        private final EasyConnectionManager easyConnectionManager;
        private final EasyConnection easyConnection;

        public CloseableConnection(ConnectionStrategyEnum connectionStrategy, EasyConnectionManager easyConnectionManager, EasyConnection easyConnection) {

            this.connectionStrategy = connectionStrategy;
            this.easyConnectionManager = easyConnectionManager;
            this.easyConnection = easyConnection;
        }

        @Override
        public void close() throws Exception {
            if (ConnectionStrategyEnum.ShareConnection.equals(connectionStrategy)) {
                easyConnectionManager.closeEasyConnection(easyConnection);
            } else {
                easyConnection.close();
            }
        }
    }
}
