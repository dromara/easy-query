package com.easy.query.core.configuration;

import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

/**
 * create time 2023/5/10 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryOptionBuilder {

    private boolean deleteThrowError;
    private SqlExecuteStrategyEnum insertStrategy;
    private SqlExecuteStrategyEnum updateStrategy;
    private int maxShardingQueryLimit;
    private int executorMaximumPoolSize;
    private int executorCorePoolSize;
    private ConnectionModeEnum connectionMode;
    private boolean throwIfRouteNotMatch;
    private  long shardingExecuteTimeoutMillis;
    private  long shardingGroupExecuteTimeoutMillis;
    private  EasyQueryReplicaOption replicaOption;
    private  EasyQueryShardingOption shardingOption;
    private  String defaultDataSourceName;

    public EasyQueryOptionBuilder() {
        this.deleteThrowError = true;
        this.insertStrategy = SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS;
        this.updateStrategy = SqlExecuteStrategyEnum.ALL_COLUMNS;
        this.connectionMode=ConnectionModeEnum.SYSTEM_AUTO;
        this.maxShardingQueryLimit = Math.max(Runtime.getRuntime().availableProcessors(), 4);
        this.executorMaximumPoolSize = 0;
        this.executorCorePoolSize = Math.min(Runtime.getRuntime().availableProcessors(), 4);
        this.throwIfRouteNotMatch = true;
        this.shardingExecuteTimeoutMillis = 30000L;
        this.shardingGroupExecuteTimeoutMillis = 20000L;
        this.defaultDataSourceName = "ds0";
    }
    public void setDeleteThrowError(boolean deleteThrowError) {
        this.deleteThrowError = deleteThrowError;
    }
    public void setInsertStrategy(SqlExecuteStrategyEnum insertStrategy) {
        this.insertStrategy = insertStrategy;
    }
    public void setUpdateStrategy(SqlExecuteStrategyEnum updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public void setConnectionMode(ConnectionModeEnum connectionMode) {
        this.connectionMode = connectionMode;
    }

    public void setMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.maxShardingQueryLimit = maxShardingQueryLimit;
    }

    public void setExecutorMaximumPoolSize(int executorMaximumPoolSize) {
        this.executorMaximumPoolSize = executorMaximumPoolSize;
    }

    public void setExecutorCorePoolSize(int executorCorePoolSize) {
        this.executorCorePoolSize = executorCorePoolSize;
    }

    public void setThrowIfRouteNotMatch(boolean throwIfRouteNotMatch) {
        this.throwIfRouteNotMatch = throwIfRouteNotMatch;
    }

    public void setShardingExecuteTimeoutMillis(long shardingExecuteTimeoutMillis) {
        this.shardingExecuteTimeoutMillis = shardingExecuteTimeoutMillis;
    }

    public void setShardingGroupExecuteTimeoutMillis(long shardingGroupExecuteTimeoutMillis) {
        this.shardingGroupExecuteTimeoutMillis = shardingGroupExecuteTimeoutMillis;
    }

    public void setReplicaOption(EasyQueryReplicaOption replicaOption) {
        this.replicaOption = replicaOption;
    }

    public void setDefaultDataSourceName(String defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }

    public void setShardingOption(EasyQueryShardingOption shardingOption) {
        this.shardingOption = shardingOption;
    }

    public boolean isUseReplica(){
        return replicaOption!=null;
    }
    public EasyQueryOption build(){
        return new EasyQueryOption(this.deleteThrowError,this.insertStrategy,this.updateStrategy,this.connectionMode,this.maxShardingQueryLimit,this.executorMaximumPoolSize,this.executorCorePoolSize,this.throwIfRouteNotMatch,
                this.shardingExecuteTimeoutMillis,this.shardingGroupExecuteTimeoutMillis,this.shardingOption,this.replicaOption,this.defaultDataSourceName);
    }
}
