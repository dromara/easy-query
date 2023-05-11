package com.easy.query.core.configuration;

import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;

/**
 * create time 2023/4/11 17:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryOption {
    private final boolean deleteThrowError;
    private final SqlExecuteStrategyEnum insertStrategy;
    private final SqlExecuteStrategyEnum updateStrategy;
    private final int maxShardingQueryLimit;
    private final int executorMaximumPoolSize;
    private final int executorCorePoolSize;
    private final ConnectionModeEnum connectionMode;

    public EasyQueryOption(boolean deleteThrowError, SqlExecuteStrategyEnum insertStrategy, SqlExecuteStrategyEnum updateStrategy, ConnectionModeEnum connectionMode, int maxShardingQueryLimit, int executorMaximumPoolSize, int executorCorePoolSize) {
        this.connectionMode = connectionMode;

        if(executorMaximumPoolSize<0){
            throw new IllegalArgumentException("executor size less than zero");
        }
        if(executorMaximumPoolSize>0){
            if(maxShardingQueryLimit>executorMaximumPoolSize){
                throw new IllegalArgumentException("maxShardingQueryLimit:"+maxShardingQueryLimit+" should less than executorMaximumPoolSize:"+executorMaximumPoolSize);
            }
            if(executorCorePoolSize>executorMaximumPoolSize){
                throw new IllegalArgumentException("executorCorePoolSize:"+executorCorePoolSize+" should less than executorMaximumPoolSize:"+executorMaximumPoolSize);
            }
        }
        this.deleteThrowError = deleteThrowError;
        this.insertStrategy = SqlExecuteStrategyEnum.getDefaultStrategy(insertStrategy, SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS);
        this.updateStrategy = SqlExecuteStrategyEnum.getDefaultStrategy(updateStrategy, SqlExecuteStrategyEnum.ALL_COLUMNS);
        this.maxShardingQueryLimit = maxShardingQueryLimit;
        this.executorMaximumPoolSize = executorMaximumPoolSize;
        this.executorCorePoolSize = executorCorePoolSize;
    }

    public boolean isDeleteThrowError() {
        return deleteThrowError;
    }

    public SqlExecuteStrategyEnum getInsertStrategy() {
        return insertStrategy;
    }

    public SqlExecuteStrategyEnum getUpdateStrategy() {
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
}
