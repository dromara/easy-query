package com.easy.query.core.configuration;

import com.easy.query.core.enums.SqlExecuteStrategyEnum;

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

    public EasyQueryOptionBuilder() {
        this.deleteThrowError = true;
        this.insertStrategy = SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS;
        this.updateStrategy = SqlExecuteStrategyEnum.ALL_COLUMNS;
        this.maxShardingQueryLimit = Math.max(Runtime.getRuntime().availableProcessors(), 4);
        this.executorMaximumPoolSize = 0;
        this.executorCorePoolSize = Math.min(Runtime.getRuntime().availableProcessors(), 4);
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
    public void setMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.maxShardingQueryLimit = maxShardingQueryLimit;
    }

    public void setExecutorMaximumPoolSize(int executorMaximumPoolSize) {
        this.executorMaximumPoolSize = executorMaximumPoolSize;
    }

    public void setExecutorCorePoolSize(int executorCorePoolSize) {
        this.executorCorePoolSize = executorCorePoolSize;
    }

    public EasyQueryOption build(){

        return new EasyQueryOption(this.deleteThrowError,this.insertStrategy,this.updateStrategy,this.maxShardingQueryLimit,this.executorMaximumPoolSize,this.executorCorePoolSize);
    }
}
