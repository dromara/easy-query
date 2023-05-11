package com.easy.query.sql.starter.config;


import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.sql.starter.option.DatabaseEnum;
import com.easy.query.sql.starter.option.NameConversionEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.Executors;

/**
 * @FileName: EasyQueryProperties.java
 * @Description: 文件说明
 * @Date: 2023/3/11 14:25
 * @author xuejiaming
 */
@ConfigurationProperties(prefix = "easy-query")
public class EasyQueryProperties {

    private Boolean enable =false;
    private Boolean deleteThrow =true;
    private DatabaseEnum database;
    private NameConversionEnum nameConversion;
    private SqlExecuteStrategyEnum insertStrategy=SqlExecuteStrategyEnum.DEFAULT;
    private SqlExecuteStrategyEnum updateStrategy=SqlExecuteStrategyEnum.DEFAULT;
    private ConnectionModeEnum connectionMode=ConnectionModeEnum.SYSTEM_AUTO;
    /**
     * 仅分片时有效
     * 最大查询限制链接数默认cpu核心数当小于4取4
     */
    private int maxShardingQueryLimit =Math.max(Runtime.getRuntime().availableProcessors(), 4);
    /**
     * 仅分片时有效默认0如果需要建议大于 maxQueryConnectionsLimit * 分库数目
     * 执行线程数 如果为0那么采用无界线程池{@link Executors#newCachedThreadPool},如果是大于0采用固定线程池{@link Executors#newFixedThreadPool}
     */
    private int executorMaximumPoolSize =0;
    private int executorCorePoolSize =Math.min(Runtime.getRuntime().availableProcessors(), 4);
    private String logClass="com.easy.query.sql.starter.logging.Slf4jImpl";
    /**
     * 当没有路由匹配的时候查询是否报错
     * true:表示报错
     * false:表示返回默认值
     */
    private boolean throwIfRouteNotMatch =true;

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

    public SqlExecuteStrategyEnum getInsertStrategy() {
        return insertStrategy;
    }

    public void setInsertStrategy(SqlExecuteStrategyEnum insertStrategy) {
        this.insertStrategy = insertStrategy;
    }

    public SqlExecuteStrategyEnum getUpdateStrategy() {
        return updateStrategy;
    }

    public void setUpdateStrategy(SqlExecuteStrategyEnum updateStrategy) {
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

    public EasyQueryProperties() {
    }
}
