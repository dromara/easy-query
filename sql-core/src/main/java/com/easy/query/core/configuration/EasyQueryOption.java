package com.easy.query.core.configuration;

import com.easy.query.core.enums.SqlExecuteStrategyEnum;

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

    public EasyQueryOption(boolean deleteThrowError, SqlExecuteStrategyEnum insertStrategy, SqlExecuteStrategyEnum updateStrategy){
        this.deleteThrowError = deleteThrowError;
        this.insertStrategy =SqlExecuteStrategyEnum.getDefaultStrategy(insertStrategy,SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS);
        this.updateStrategy = SqlExecuteStrategyEnum.getDefaultStrategy(updateStrategy,SqlExecuteStrategyEnum.ALL_COLUMNS);
    }
    public static EasyQueryOption defaultEasyQueryOption(){
        return new EasyQueryOption(true,SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS,SqlExecuteStrategyEnum.ALL_COLUMNS);
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
}
