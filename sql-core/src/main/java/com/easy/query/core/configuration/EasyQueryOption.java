package com.easy.query.core.configuration;

import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

/**
 * create time 2023/4/11 17:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryOption {
    private final boolean deleteThrowError;
    private final SQLExecuteStrategyEnum insertStrategy;
    private final SQLExecuteStrategyEnum updateStrategy;
    private final int maxShardingQueryLimit;
    private final int executorMaximumPoolSize;
    private final int executorCorePoolSize;
    private final ConnectionModeEnum connectionMode;
    private final boolean throwIfRouteNotMatch;
    private final long shardingExecuteTimeoutMillis;
    private final long shardingGroupExecuteTimeoutMillis;
    private final EasyQueryReplicaOption replicaOption;
    private final EasyQueryShardingOption shardingOption;
    private final String defaultDataSourceName;
    private final boolean queryLargeColumn;
    private final int maxShardingRouteCount;

    public EasyQueryOption(boolean deleteThrowError, SQLExecuteStrategyEnum insertStrategy, SQLExecuteStrategyEnum updateStrategy, ConnectionModeEnum connectionMode, int maxShardingQueryLimit, int executorMaximumPoolSize, int executorCorePoolSize,
                           boolean throwIfNotMatchRoute, long shardingExecuteTimeoutMillis, long shardingGroupExecuteTimeoutMillis,
                           EasyQueryShardingOption shardingOption, EasyQueryReplicaOption replicaOption, String defaultDataSourceName,boolean queryLargeColumn,int maxShardingRouteCount) {
        this.connectionMode = connectionMode;

        if(executorMaximumPoolSize<0){
            throw new IllegalArgumentException("executor size less than zero:"+executorMaximumPoolSize);
        }
        if(executorMaximumPoolSize>0){
            if(maxShardingQueryLimit>executorMaximumPoolSize){
                throw new IllegalArgumentException("maxShardingQueryLimit:"+maxShardingQueryLimit+" should less than executorMaximumPoolSize:"+executorMaximumPoolSize);
            }
            if(executorCorePoolSize>executorMaximumPoolSize){
                throw new IllegalArgumentException("executorCorePoolSize:"+executorCorePoolSize+" should less than executorMaximumPoolSize:"+executorMaximumPoolSize);
            }
        }
        if(shardingExecuteTimeoutMillis<=0){
            throw new IllegalArgumentException("shardingExecuteTimeoutMillis less than zero:"+shardingExecuteTimeoutMillis);
        }
        if(shardingGroupExecuteTimeoutMillis<=0){
            throw new IllegalArgumentException("shardingGroupExecuteTimeoutMillis less than zero:"+shardingGroupExecuteTimeoutMillis);
        }
        if(shardingExecuteTimeoutMillis<shardingGroupExecuteTimeoutMillis){
            throw new IllegalArgumentException("shardingExecuteTimeoutMillis:"+shardingExecuteTimeoutMillis+" should less than shardingGroupExecuteTimeoutMillis:"+shardingGroupExecuteTimeoutMillis);
        }
        this.deleteThrowError = deleteThrowError;
        this.insertStrategy = SQLExecuteStrategyEnum.getDefaultStrategy(insertStrategy, SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS);
        this.updateStrategy = SQLExecuteStrategyEnum.getDefaultStrategy(updateStrategy, SQLExecuteStrategyEnum.ALL_COLUMNS);
        this.maxShardingQueryLimit = maxShardingQueryLimit;
        this.executorMaximumPoolSize = executorMaximumPoolSize;
        this.executorCorePoolSize = executorCorePoolSize;
        this.throwIfRouteNotMatch = throwIfNotMatchRoute;
        this.shardingExecuteTimeoutMillis = shardingExecuteTimeoutMillis;
        this.shardingGroupExecuteTimeoutMillis = shardingGroupExecuteTimeoutMillis;
        this.shardingOption = shardingOption;
        this.replicaOption = replicaOption;
        this.defaultDataSourceName = defaultDataSourceName;
        this.queryLargeColumn = queryLargeColumn;
        this.maxShardingRouteCount = maxShardingRouteCount;
    }

    public int getMaxShardingRouteCount() {
        return maxShardingRouteCount;
    }

    public boolean isDeleteThrowError() {
        return deleteThrowError;
    }

    public SQLExecuteStrategyEnum getInsertStrategy() {
        return insertStrategy;
    }

    public SQLExecuteStrategyEnum getUpdateStrategy() {
        return updateStrategy;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public int getMaxShardingQueryLimit() {
        return maxShardingQueryLimit;
    }

    public int getExecutorMaximumPoolSize() {
        return executorMaximumPoolSize;
    }

    public int getExecutorCorePoolSize() {
        return executorCorePoolSize;
    }

    public boolean isThrowIfRouteNotMatch() {
        return throwIfRouteNotMatch;
    }

    public long getShardingExecuteTimeoutMillis() {
        return shardingExecuteTimeoutMillis;
    }

    public long getShardingGroupExecuteTimeoutMillis() {
        return shardingGroupExecuteTimeoutMillis;
    }

    public EasyQueryShardingOption getShardingOption() {
        return shardingOption;
    }

    public EasyQueryReplicaOption getReplicaOption() {
        return replicaOption;
    }

    public String getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    public boolean isQueryLargeColumn() {
        return queryLargeColumn;
    }
}
