package com.easy.query.core.configuration;

import com.easy.query.core.enums.IncludeLimitModeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.ShardingQueryInTransactionEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

/**
 * create time 2023/4/11 17:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryOption {
    /**
     * 物理删除是否抛出错误
     */
    private final boolean deleteThrowError;
    /**
     * 默认的插入策略
     */
    private final SQLExecuteStrategyEnum insertStrategy;
    /**
     * 默认的更新策略
     */
    private final SQLExecuteStrategyEnum updateStrategy;
    /**
     * 默认的分片同库单次最大查询使用连接数
     */
    private final int maxShardingQueryLimit;
    /**
     * 最大分片执行线程数,如果executorMaximumPoolSize<=0 那么将使用Executors.newCachedThreadPool
     */
    private final int executorMaximumPoolSize;
    /**
     * 分片并行执行线程池核心数
     */
    private final int executorCorePoolSize;
    /**
     * 链接默认 会影响数据聚合是内存还是stream
     */
    private final ConnectionModeEnum connectionMode;
    /**
     * 如果路由没有匹配到是否报错
     */
    private final boolean throwIfRouteNotMatch;
    /**
     * 分片执行超时时间
     */
    private final long shardingExecuteTimeoutMillis;
    /**
     * 读写分离配置
     */
    private final EasyQueryReplicaOption replicaOption;
    /**
     * 分片分库配置
     */
    private final EasyQueryShardingOption shardingOption;
    /**
     * 默认数据源名称
     */
    private final String defaultDataSourceName;
    /**
     * 默认数据源合并可用连接数大小
     */
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
    /**
     * 如果分片连接数获取是繁忙的是否需要warning日志输出
     */
    private final boolean warningBusy;
    /**
     * 当插入对象达到多少阈值启用batch
     */
    private final int insertBatchThreshold;
    /**
     * 当更新对象达到多少阈值启用batch
     */
    private final int updateBatchThreshold;
    /**
     * 是否需要打印sql，以log.info打印
     */
    private final boolean printSql;
    /**
     * 是否需要打印关联子查询sql，以log.info打印
     */
    private final boolean printNavSql;
    /**
     * 如果使用按时间分表的路由需要开启定时任务
     */
    private final boolean startTimeJob;

    /**
     * 默认是否启用追踪模式
     */
    private final boolean defaultTrack;
    /**
     * include的关联查询每次用多少关联id去关联查询也就是in里面最多多少个关联id
     * 如果超出部分则另起一个查询
     */
    private final int relationGroupSize;
    /**
     * 使用sqlNativeSegment时如果使用单引号默认改为双引号
     */
    private final boolean keepNativeStyle;
    /**
     * 启用反向排序的偏移量阈值
     * 小于等于0表示不启用
     */
    private final long reverseOffsetThreshold;
    /**
     * 属性映射丢失警告
     */
    private final boolean warningColumnMiss;
    private final int shardingFetchSize;
    private final boolean mapToBeanStrict;
    private final String defaultSchema;
    /**
     * 限制最大返回数据量
     */
    private final long resultSizeLimit;
    private final ShardingQueryInTransactionEnum shardingQueryInTransaction;

    /**
     * 建议19
     */
    private final int mssqlMinBigDecimalScale;
    /**
     * 一对多拉取带limit的时候使用哪种模式默认UNION_ALL
     */
    private final IncludeLimitModeEnum includeLimitMode;

    public EasyQueryOption(boolean deleteThrowError, SQLExecuteStrategyEnum insertStrategy,
                           SQLExecuteStrategyEnum updateStrategy, ConnectionModeEnum connectionMode,
                           int maxShardingQueryLimit, int executorMaximumPoolSize, int executorCorePoolSize,
                           boolean throwIfNotMatchRoute, long shardingExecuteTimeoutMillis,
                           EasyQueryShardingOption shardingOption, EasyQueryReplicaOption replicaOption,
                           String defaultDataSourceName, int defaultDataSourceMergePoolSize, boolean queryLargeColumn,
                           int maxShardingRouteCount, int executorQueueSize, long multiConnWaitTimeoutMillis,
                           boolean warningBusy, int insertBatchThreshold, int updateBatchThreshold,
                           boolean printSql, boolean startTimeJob, boolean defaultTrack,
                           int relationGroupSize, boolean keepNativeStyle, long reverseOffsetThreshold,
                           boolean warningColumnMiss, int shardingFetchSize, boolean mapToBeanStrict,
                           String defaultSchema, long resultSizeLimit, boolean printNavSql,
                           ShardingQueryInTransactionEnum shardingQueryInTransaction, int mssqlMinBigDecimalScale,
                           IncludeLimitModeEnum includeLimitMode) {


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
        if (insertBatchThreshold <= 2) {
            throw new IllegalArgumentException("invalid arguments: insertBatchThreshold <= 2.");
        }
        if (updateBatchThreshold <= 2) {
            throw new IllegalArgumentException("invalid arguments: updateBatchThreshold <= 2.");
        }
        if (relationGroupSize < 1) {
            throw new IllegalArgumentException("invalid arguments: relationGroupSize < 1.");
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
        this.insertBatchThreshold = insertBatchThreshold;
        this.updateBatchThreshold = updateBatchThreshold;
        this.printSql = printSql;
        this.printNavSql = printNavSql;
        this.startTimeJob = startTimeJob;
        this.defaultTrack = defaultTrack;
        this.relationGroupSize = relationGroupSize;
        this.keepNativeStyle = keepNativeStyle;
        this.reverseOffsetThreshold = reverseOffsetThreshold;
        this.warningColumnMiss = warningColumnMiss;
        this.shardingFetchSize = shardingFetchSize;
        this.mapToBeanStrict = mapToBeanStrict;
        this.defaultSchema = defaultSchema;
        this.resultSizeLimit = resultSizeLimit;
        this.shardingQueryInTransaction = shardingQueryInTransaction;
        this.mssqlMinBigDecimalScale = mssqlMinBigDecimalScale;
        this.includeLimitMode = includeLimitMode;
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

    public int getInsertBatchThreshold() {
        return insertBatchThreshold;
    }

    public int getUpdateBatchThreshold() {
        return updateBatchThreshold;
    }

    public boolean isPrintSql() {
        return printSql;
    }

    public boolean isPrintNavSql() {
        return printNavSql;
    }

    public boolean isStartTimeJob() {
        return startTimeJob;
    }

    public boolean isDefaultTrack() {
        return defaultTrack;
    }

    public int getRelationGroupSize() {
        return relationGroupSize;
    }

    public boolean isKeepNativeStyle() {
        return keepNativeStyle;
    }

    public boolean enableReverseOrder(long offset) {
        if (this.reverseOffsetThreshold <= 0) {
            return false;
        }
        return offset >= this.reverseOffsetThreshold;
    }

    public boolean isWarningColumnMiss() {
        return warningColumnMiss;
    }

    public int getShardingFetchSize() {
        return shardingFetchSize;
    }

    public boolean isMapToBeanStrict() {
        return mapToBeanStrict;
    }

    public String getDefaultSchema() {
        return defaultSchema;
    }

    public long getResultSizeLimit() {
        return resultSizeLimit;
    }

    public long getReverseOffsetThreshold() {
        return reverseOffsetThreshold;
    }

    public ShardingQueryInTransactionEnum getShardingQueryInTransaction() {
        return shardingQueryInTransaction;
    }

    public int getMssqlMinBigDecimalScale() {
        return mssqlMinBigDecimalScale;
    }

    public IncludeLimitModeEnum getIncludeLimitMode() {
        return includeLimitMode;
    }
}
