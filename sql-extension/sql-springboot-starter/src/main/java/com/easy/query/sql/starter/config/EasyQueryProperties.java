package com.easy.query.sql.starter.config;


import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.sql.starter.option.BeanCallerTypeEnum;
import com.easy.query.sql.starter.option.DatabaseEnum;
import com.easy.query.sql.starter.option.NameConversionEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.Executors;

/**
 * @author xuejiaming
 * @FileName: EasyQueryProperties.java
 * @Description: 文件说明
 * @Date: 2023/3/11 14:25
 */
@ConfigurationProperties(prefix = "easy-query")
public class EasyQueryProperties {

    private Boolean enable = false;
    private Boolean deleteThrow = true;
    private DatabaseEnum database = DatabaseEnum.MYSQL;
    private BeanCallerTypeEnum beanCallerType = BeanCallerTypeEnum.LAMBDA;
    private NameConversionEnum nameConversion;
    private SQLExecuteStrategyEnum insertStrategy = SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS;
    private SQLExecuteStrategyEnum updateStrategy = SQLExecuteStrategyEnum.ALL_COLUMNS;
    private ConnectionModeEnum connectionMode = ConnectionModeEnum.SYSTEM_AUTO;
    /**
     * 仅分片时有效默认同时5个线程5
     */
    private int maxShardingQueryLimit = 5;
    /**
     * 仅分片时有效默认0如果需要建议大于 {@link com.easy.query.sql.starter.config.EasyQueryProperties#maxShardingQueryLimit} * 分库数目
     * 执行线程数 如果为0那么采用无界线程池{@link Executors#newCachedThreadPool},如果是大于0采用长度为{@link com.easy.query.sql.starter.config.EasyQueryProperties#executorQueueSize}的有界队列
     * 核心线程数采用{@link com.easy.query.sql.starter.config.EasyQueryProperties#executorCorePoolSize}并且需要比 {@link com.easy.query.sql.starter.config.EasyQueryProperties#executorCorePoolSize}值大
     */
    private int executorMaximumPoolSize = 0;
    /**
     * 当且仅当{@link com.easy.query.sql.starter.config.EasyQueryProperties#executorMaximumPoolSize}>0生效
     */
    private int executorCorePoolSize = Math.min(Runtime.getRuntime().availableProcessors(), 4);
    /**
     * 当且仅当{@link com.easy.query.sql.starter.config.EasyQueryProperties#executorMaximumPoolSize}>0生效
     */
    private int executorQueueSize = 1024;
    private String logClass = "com.easy.query.sql.starter.logging.Slf4jImpl";
    /**
     * 当查询没有路由匹配的时候查询是否报错
     * true:表示报错
     * false:表示返回默认值
     */
    private boolean throwIfRouteNotMatch = true;

    /**
     * 分片聚合超时时间默认60秒单位(ms)
     */
    private long shardingExecuteTimeoutMillis = 60000L;

    private boolean queryLargeColumn = true;
    /**
     * 当出现条件分片大于多少时报错默认128,
     * 就是比如select where update where delete where路由到过多的表就会报错
     * entity操作比如update对象，insert，delete对象不会判断这个条件
     */
    private int maxShardingRouteCount = 128;
    /**
     * 默认数据源分库有效
     */
    private String defaultDataSourceName = "ds0";
    /**
     * 默认数据源的数据源连接池大小分表有效,一般设置为最少最少 >= maxShardingQueryLimit
     * 如果当前没有分表操作建议设置为0
     * 当小于maxShardingQueryLimit后启动会抛出警告
     */
    private int defaultDataSourceMergePoolSize = 0;
    /**
     * 默认5秒分表聚合多链接获取分表插入更新删除同理多个线程间等待获取时间单位毫秒(ms)
     */
    private long multiConnWaitTimeoutMillis = 5000L;
    private boolean warningBusy = true;


    /**
     * 对象插入数量到达多少后使用批处理
     */
    private int insertBatchThreshold = 512;
    /**
     * 对象修改数量达到多少后使用批量处理
     */
    private int updateBatchThreshold = 512;
    private boolean printSql = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }


    public Boolean getDeleteThrow() {
        return deleteThrow;
    }

    public void setDeleteThrow(Boolean deleteThrow) {
        this.deleteThrow = deleteThrow;
    }


    public DatabaseEnum getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseEnum database) {
        this.database = database;
    }

    public NameConversionEnum getNameConversion() {
        return nameConversion;
    }

    public void setNameConversion(NameConversionEnum nameConversion) {
        this.nameConversion = nameConversion;
    }

    public SQLExecuteStrategyEnum getInsertStrategy() {
        return insertStrategy;
    }

    public void setInsertStrategy(SQLExecuteStrategyEnum insertStrategy) {
        this.insertStrategy = insertStrategy;
    }

    public SQLExecuteStrategyEnum getUpdateStrategy() {
        return updateStrategy;
    }

    public void setUpdateStrategy(SQLExecuteStrategyEnum updateStrategy) {
        this.updateStrategy = updateStrategy;
    }


    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public void setConnectionMode(ConnectionModeEnum connectionMode) {
        this.connectionMode = connectionMode;
    }

    public int getMaxShardingQueryLimit() {
        return maxShardingQueryLimit;
    }

    public void setMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.maxShardingQueryLimit = maxShardingQueryLimit;
    }

    public int getExecutorMaximumPoolSize() {
        return executorMaximumPoolSize;
    }

    public void setExecutorMaximumPoolSize(int executorMaximumPoolSize) {
        this.executorMaximumPoolSize = executorMaximumPoolSize;
    }

    public int getExecutorCorePoolSize() {
        return executorCorePoolSize;
    }

    public void setExecutorCorePoolSize(int executorCorePoolSize) {
        this.executorCorePoolSize = executorCorePoolSize;
    }

    public boolean isThrowIfRouteNotMatch() {
        return throwIfRouteNotMatch;
    }

    public void setThrowIfRouteNotMatch(boolean throwIfRouteNotMatch) {
        this.throwIfRouteNotMatch = throwIfRouteNotMatch;
    }

    public long getShardingExecuteTimeoutMillis() {
        return shardingExecuteTimeoutMillis;
    }

    public void setShardingExecuteTimeoutMillis(long shardingExecuteTimeoutMillis) {
        this.shardingExecuteTimeoutMillis = shardingExecuteTimeoutMillis;
    }

    public boolean isQueryLargeColumn() {
        return queryLargeColumn;
    }

    public void setQueryLargeColumn(boolean queryLargeColumn) {
        this.queryLargeColumn = queryLargeColumn;
    }

    public int getMaxShardingRouteCount() {
        return maxShardingRouteCount;
    }

    public void setMaxShardingRouteCount(int maxShardingRouteCount) {
        this.maxShardingRouteCount = maxShardingRouteCount;
    }

    public int getExecutorQueueSize() {
        return executorQueueSize;
    }

    public void setExecutorQueueSize(int executorQueueSize) {
        this.executorQueueSize = executorQueueSize;
    }

    public String getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    public void setDefaultDataSourceName(String defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }

    public int getDefaultDataSourceMergePoolSize() {
        return defaultDataSourceMergePoolSize;
    }

    public void setDefaultDataSourceMergePoolSize(int defaultDataSourceMergePoolSize) {
        this.defaultDataSourceMergePoolSize = defaultDataSourceMergePoolSize;
    }

    public long getMultiConnWaitTimeoutMillis() {
        return multiConnWaitTimeoutMillis;
    }

    public void setMultiConnWaitTimeoutMillis(long multiConnWaitTimeoutMillis) {
        this.multiConnWaitTimeoutMillis = multiConnWaitTimeoutMillis;
    }

    public boolean isWarningBusy() {
        return warningBusy;
    }

    public void setWarningBusy(boolean warningBusy) {
        this.warningBusy = warningBusy;
    }

    public int getInsertBatchThreshold() {
        return insertBatchThreshold;
    }

    public void setInsertBatchThreshold(int insertBatchThreshold) {
        this.insertBatchThreshold = insertBatchThreshold;
    }

    public int getUpdateBatchThreshold() {
        return updateBatchThreshold;
    }

    public void setUpdateBatchThreshold(int updateBatchThreshold) {
        this.updateBatchThreshold = updateBatchThreshold;
    }

    public boolean isPrintSql() {
        return printSql;
    }

    public void setPrintSql(boolean printSql) {
        this.printSql = printSql;
    }

    public BeanCallerTypeEnum getBeanCallerType() {
        return beanCallerType;
    }

    public void setBeanCallerType(BeanCallerTypeEnum beanCallerType) {
        this.beanCallerType = beanCallerType;
    }

    public EasyQueryProperties() {
    }
}
