package com.easy.query.core.configuration;

import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

/**
 * create time 2023/5/10 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryOptionBuilder {

    private boolean deleteThrowError;
    private SQLExecuteStrategyEnum insertStrategy;
    private SQLExecuteStrategyEnum updateStrategy;
    private int maxShardingQueryLimit;
    private int executorMaximumPoolSize;
    private int executorCorePoolSize;
    private ConnectionModeEnum connectionMode;
    private boolean throwIfRouteNotMatch;
    private long shardingExecuteTimeoutMillis;
    private EasyQueryReplicaOption replicaOption;
    private EasyQueryShardingOption shardingOption;
    private String defaultDataSourceName;
    private int defaultDataSourceMergePoolSize;
    private boolean queryLargeColumn;
    private int maxShardingRouteCount;
    private int executorQueueSize;
    /**
     * 分片聚合多个connection获取等待超时时间防止分片datasourcePoolSize过小导致假死
     */
    private long multiConnWaitTimeoutMillis;
    private boolean warningBusy;
    /**
     * 插入批处理阈值
     */
    private int insertBatchThreshold;
    /**
     * update批处理阈值
     */
    private int updateBatchThreshold;
    /**
     * 是否打印sql
     */
    private boolean printSql;
    /**
     * 按时间分表启动定时器
     */
    private boolean startTimeJob;
    /**
     * 默认是否使用追踪模式
     */
    private boolean defaultTrack;
    /**
     * 关联查询每次分组数量
     */
    private int relationGroupSize;
    /**
     * 无版本号报错
     */
    private boolean noVersionError;

    public EasyQueryOptionBuilder() {
        this.deleteThrowError = true;
        this.insertStrategy = SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS;
        this.updateStrategy = SQLExecuteStrategyEnum.ALL_COLUMNS;
        this.connectionMode = ConnectionModeEnum.SYSTEM_AUTO;
        this.maxShardingQueryLimit = 5;
        this.defaultDataSourceMergePoolSize = 0;
        this.executorMaximumPoolSize = 0;
        this.executorCorePoolSize = Math.min(Runtime.getRuntime().availableProcessors(), 4);
        this.throwIfRouteNotMatch = true;
        this.shardingExecuteTimeoutMillis = 60000L;
        this.defaultDataSourceName = "ds0";
        this.queryLargeColumn = true;
        this.maxShardingRouteCount = 128;
        this.executorQueueSize = 1024;
        this.multiConnWaitTimeoutMillis = 5000L;
        this.warningBusy = true;
        this.insertBatchThreshold = 1024;
        this.updateBatchThreshold = 1024;
        this.printSql = true;
        this.startTimeJob = false;
        this.defaultTrack = false;
        this.relationGroupSize = 512;
        this.noVersionError = true;
    }

    public void setDeleteThrowError(boolean deleteThrowError) {
        this.deleteThrowError = deleteThrowError;
    }

    public void setInsertStrategy(SQLExecuteStrategyEnum insertStrategy) {
        this.insertStrategy = insertStrategy;
    }

    public void setUpdateStrategy(SQLExecuteStrategyEnum updateStrategy) {
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

    public void setReplicaOption(EasyQueryReplicaOption replicaOption) {
        this.replicaOption = replicaOption;
    }

    public void setDefaultDataSourceName(String defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }

    public void setShardingOption(EasyQueryShardingOption shardingOption) {
        this.shardingOption = shardingOption;
    }

    public boolean isUseReplica() {
        return replicaOption != null;
    }

    public void setQueryLargeColumn(boolean queryLargeColumn) {
        this.queryLargeColumn = queryLargeColumn;
    }

    public void setMaxShardingRouteCount(int maxShardingRouteCount) {
        this.maxShardingRouteCount = maxShardingRouteCount;
    }

    public void setExecutorQueueSize(int executorQueueSize) {
        this.executorQueueSize = executorQueueSize;
    }

    /**
     * 如果当前没有分表操作建议设置为0
     *
     * @param defaultDataSourceMergePoolSize
     */
    public void setDefaultDataSourceMergePoolSize(int defaultDataSourceMergePoolSize) {
        this.defaultDataSourceMergePoolSize = defaultDataSourceMergePoolSize;
    }

    public void setMultiConnWaitTimeoutMillis(long multiConnWaitTimeoutMillis) {
        this.multiConnWaitTimeoutMillis = multiConnWaitTimeoutMillis;
    }

    /**
     * @param warningBusy
     */
    public void setWarningBusy(boolean warningBusy) {
        this.warningBusy = warningBusy;
    }

    public void setInsertBatchThreshold(int insertBatchThreshold) {
        this.insertBatchThreshold = insertBatchThreshold;
    }

    public void setUpdateBatchThreshold(int updateBatchThreshold) {
        this.updateBatchThreshold = updateBatchThreshold;
    }

    public void setPrintSql(boolean printSql) {
        this.printSql = printSql;
    }

    public void setStartTimeJob(boolean startTimeJob) {
        this.startTimeJob = startTimeJob;
    }

    public void setDefaultTrack(boolean defaultTrack) {
        this.defaultTrack = defaultTrack;
    }

    public void setRelationGroupSize(int relationGroupSize) {
        this.relationGroupSize = relationGroupSize;
    }

    public void setNoVersionError(boolean noVersionError) {
        this.noVersionError = noVersionError;
    }

    public EasyQueryOption build() {
        return new EasyQueryOption(this.deleteThrowError,
                this.insertStrategy,
                this.updateStrategy,
                this.connectionMode,
                this.maxShardingQueryLimit,
                this.executorMaximumPoolSize,
                this.executorCorePoolSize,
                this.throwIfRouteNotMatch,
                this.shardingExecuteTimeoutMillis,
                this.shardingOption,
                this.replicaOption,
                this.defaultDataSourceName,
                this.defaultDataSourceMergePoolSize,
                this.queryLargeColumn,
                this.maxShardingRouteCount,
                this.executorQueueSize,
                this.multiConnWaitTimeoutMillis,
                this.warningBusy,
                this.insertBatchThreshold,
                this.updateBatchThreshold,
                this.printSql,
                this.startTimeJob,
                this.defaultTrack,
                this.relationGroupSize,
                this.noVersionError);
    }
}
