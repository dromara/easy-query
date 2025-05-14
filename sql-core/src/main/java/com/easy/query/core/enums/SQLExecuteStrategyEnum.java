package com.easy.query.core.enums;

/**
 * @Description: 文件说明
 * create time 2023/3/20 10:33
 * @author xuejiaming
 */
public enum SQLExecuteStrategyEnum {
    /**
     * 默认策略全部更新
     * INSERT:{@link SQLExecuteStrategyEnum#ONLY_NOT_NULL_COLUMNS}
     * UPDATE:{@link SQLExecuteStrategyEnum#ALL_COLUMNS}
     */
    DEFAULT,
    /**
     * 插入/更新全部列
     */
    ALL_COLUMNS,
    /**
     * 插入/更新非null的列 insert默认
     */
    ONLY_NOT_NULL_COLUMNS,
    /**
     * 插入/更新null值列
     */
    ONLY_NULL_COLUMNS;

    public static SQLExecuteStrategyEnum getDefaultStrategy(SQLExecuteStrategyEnum sqlExecuteStrategy, SQLExecuteStrategyEnum def){
        if(DEFAULT.equals(def)){
            throw new IllegalArgumentException("default strategy :[DEFAULT] error");
        }
        switch (sqlExecuteStrategy){
            case DEFAULT:return def;
            default: return sqlExecuteStrategy;
        }
    }
}
