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
    private final EasyQueryReplicaOption replicaOption;
    private final EasyQueryShardingOption shardingOption;
    private final String defaultDataSourceName;
    private final int defaultDataSourceMergePoolSize;
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
    private final boolean warningBusy;
    private final int insertBatchGroup;
    private final int updateBatchGroup;
    private final boolean printSql;

    public EasyQueryOption(boolean deleteThrowError, SQLExecuteStrategyEnum insertStrategy, SQLExecuteStrategyEnum updateStrategy, ConnectionModeEnum connectionMode, int maxShardingQueryLimit, int executorMaximumPoolSize, int executorCorePoolSize,
                           boolean throwIfNotMatchRoute, long shardingExecuteTimeoutMillis,
                           EasyQueryShardingOption shardingOption, EasyQueryReplicaOption replicaOption, String defaultDataSourceName, int defaultDataSourceMergePoolSize, boolean queryLargeColumn, int maxShardingRouteCount, int executorQueueSize, long multiConnWaitTimeoutMillis,
                           boolean warningBusy, int insertBatchGroup, int updateBatchGroup, boolean printSql) {


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
            if ((executorMaximumPoolSize + executorQueueSize) < maxShardingQueryLimit) {
                throw new IllegalArgumentException("Invalid arguments: (executorMaximumPoolSize+executorQueueSize) < maxShardingQueryLimit");
            }
        }
        if (shardingExecuteTimeoutMillis <= 0) {
            throw new IllegalArgumentException("shardingExecuteTimeoutMillis less than zero:" + shardingExecuteTimeoutMillis);
        }
        if (multiConnWaitTimeoutMillis <= 0) {
            throw new IllegalArgumentException("multiConnWaitTimeoutMillis <= 0");
        }
        if (defaultDataSourceMergePoolSize > 0 && defaultDataSourceMergePoolSize < maxShardingQueryLimit) {
            throw new IllegalArgumentException("invalid arguments: defaultDataSourceMergePoolSize > 0 && defaultDataSourceMergePoolSize < maxShardingQueryLimit.");
        }
        if (insertBatchGroup <= 2) {
            throw new IllegalArgumentException("invalid arguments: insertBatchGroup <= 2.");
        }
        if (updateBatchGroup <= 2) {
            throw new IllegalArgumentException("invalid arguments: updateBatchGroup <= 2.");
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
        this.shardingOption = shardingOption;
        this.replicaOption = replicaOption;
        this.defaultDataSourceName = defaultDataSourceName;
        this.defaultDataSourceMergePoolSize = defaultDataSourceMergePoolSize;
        this.queryLargeColumn = queryLargeColumn;
        this.maxShardingRouteCount = maxShardingRouteCount;
        this.executorQueueSize = executorQueueSize;
        this.multiConnWaitTimeoutMillis = multiConnWaitTimeoutMillis;
        this.warningBusy = warningBusy;
        this.insertBatchGroup = insertBatchGroup;
        this.updateBatchGroup = updateBatchGroup;
        this.printSql = printSql;
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

    public int getDefaultDataSourceMergePoolSize() {
        return defaultDataSourceMergePoolSize;
    }

    public long getMultiConnWaitTimeoutMillis() {
        return multiConnWaitTimeoutMillis;
    }

    public boolean isWarningBusy() {
        return warningBusy;
    }

    public int getInsertBatchGroup() {
        return insertBatchGroup;
    }

    public int getUpdateBatchGroup() {
        return updateBatchGroup;
    }

    public boolean isPrintSql() {
        return printSql;
    }
}
