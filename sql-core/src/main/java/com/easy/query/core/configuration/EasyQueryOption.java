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
    /**
     * 最大分片执行线程数,如果executorMaximumPoolSize<=0 那么将使用Executors.newCachedThreadPool
     */
    private final int executorMaximumPoolSize;
    private final int executorCorePoolSize;
    private final ConnectionModeEnum connectionMode;
    private final boolean throwIfRouteNotMatch;
    private final long shardingExecuteTimeoutMillis;
    private final long shardingGroupExecuteTimeoutMillis;
    private final EasyQueryReplicaOption replicaOption;
    private final EasyQueryShardingOption shardingOption;
    private final String defaultDataSourceName;
    private final int defaultDataSourcePoolSize;
    /**
     * 默认查询是否查询large column默认true
     */
    private final boolean queryLargeColumn;
    /**
     * 默认最大分片路由数默认128仅限制条件predicate
     */
    private final int maxShardingRouteCount;
    /**
     * 指定线程池队列数目默认1024长度
     */
    private final int executorQueueSize;
    /**
     * 分片聚合多个connection获取等待超时时间防止分片datasourcePoolSize过小导致假死
     */
    private final long multiConnWaitTimeoutMillis;

    public EasyQueryOption(boolean deleteThrowError, SQLExecuteStrategyEnum insertStrategy, SQLExecuteStrategyEnum updateStrategy, ConnectionModeEnum connectionMode, int maxShardingQueryLimit, int executorMaximumPoolSize, int executorCorePoolSize,
                           boolean throwIfNotMatchRoute, long shardingExecuteTimeoutMillis, long shardingGroupExecuteTimeoutMillis,
                           EasyQueryShardingOption shardingOption, EasyQueryReplicaOption replicaOption, String defaultDataSourceName, int defaultDataSourcePoolSize, boolean queryLargeColumn, int maxShardingRouteCount, int executorQueueSize, long multiConnWaitTimeoutMillis) {

        if (executorMaximumPoolSize > 0) {
            if (executorCorePoolSize > executorMaximumPoolSize) {
                throw new IllegalArgumentException("Invalid arguments: executorCorePoolSize > executorMaximumPoolSize");
            }
            if (maxShardingQueryLimit > executorMaximumPoolSize) {
                throw new IllegalArgumentException("Invalid arguments: maxShardingQueryLimit > executorMaximumPoolSize");
            }
            if (executorQueueSize <= 0) {
                throw new IllegalArgumentException("Invalid arguments: executorQueueSize <= 0");
            }
            if (executorQueueSize < maxShardingQueryLimit) {
                throw new IllegalArgumentException("Invalid arguments: executorQueueSize < maxShardingQueryLimit");
            }
        }
        if (shardingExecuteTimeoutMillis <= 0) {
            throw new IllegalArgumentException("shardingExecuteTimeoutMillis less than zero:" + shardingExecuteTimeoutMillis);
        }
        if (shardingGroupExecuteTimeoutMillis <= 0) {
            throw new IllegalArgumentException("shardingGroupExecuteTimeoutMillis less than zero:" + shardingGroupExecuteTimeoutMillis);
        }
        if (shardingExecuteTimeoutMillis < shardingGroupExecuteTimeoutMillis) {
            throw new IllegalArgumentException("shardingExecuteTimeoutMillis:" + shardingExecuteTimeoutMillis + " should less than shardingGroupExecuteTimeoutMillis:" + shardingGroupExecuteTimeoutMillis);
        }
        if(multiConnWaitTimeoutMillis <= 0){
            throw new IllegalArgumentException("multiConnWaitTimeoutMillis <= 0");
        }
        this.deleteThrowError = deleteThrowError;
        this.insertStrategy = SQLExecuteStrategyEnum.getDefaultStrategy(insertStrategy, SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS);
        this.updateStrategy = SQLExecuteStrategyEnum.getDefaultStrategy(updateStrategy, SQLExecuteStrategyEnum.ALL_COLUMNS);
        this.connectionMode = connectionMode;
        this.maxShardingQueryLimit = maxShardingQueryLimit;
        this.executorMaximumPoolSize = executorMaximumPoolSize;
        this.executorCorePoolSize = executorCorePoolSize;
        this.throwIfRouteNotMatch = throwIfNotMatchRoute;
        this.shardingExecuteTimeoutMillis = shardingExecuteTimeoutMillis;
        this.shardingGroupExecuteTimeoutMillis = shardingGroupExecuteTimeoutMillis;
        this.shardingOption = shardingOption;
        this.replicaOption = replicaOption;
        this.defaultDataSourceName = defaultDataSourceName;
        this.defaultDataSourcePoolSize = defaultDataSourcePoolSize;
        this.queryLargeColumn = queryLargeColumn;
        this.maxShardingRouteCount = maxShardingRouteCount;
        this.executorQueueSize = executorQueueSize;
        this.multiConnWaitTimeoutMillis = multiConnWaitTimeoutMillis;
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

    public int getExecutorQueueSize() {
        return executorQueueSize;
    }

    public int getDefaultDataSourcePoolSize() {
        return defaultDataSourcePoolSize;
    }

    public long getMultiConnWaitTimeoutMillis() {
        return multiConnWaitTimeoutMillis;
    }
}
