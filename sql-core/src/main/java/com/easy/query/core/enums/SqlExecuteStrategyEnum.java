package com.easy.query.core.enums;

/**
 * @FileName: SqlExecuteStrategyEnum.java
 * @Description: 文件说明
 * @Date: 2023/3/20 10:33
 * @author xuejiaming
 */
public enum SqlExecuteStrategyEnum {
    /**
     * 默认策略全部更新
     */
    DEFAULT,
    /**
     * 插入/更新全部列
     */
    ALL_COLUMNS,
    /**
     * 插入/更新非null的列
     */
    ONLY_NOT_NULL_COLUMNS,
    /**
     * 插入/更新null值列
     */
    ONLY_NULL_COLUMNS;

    public static SqlExecuteStrategyEnum getDefaultStrategy(SqlExecuteStrategyEnum sqlExecuteStrategy,SqlExecuteStrategyEnum def){
        if(DEFAULT.equals(def)){
            throw new IllegalArgumentException("default strategy :[DEFAULT] error");
        }
        switch (sqlExecuteStrategy){
            case DEFAULT:return def;
            default: return sqlExecuteStrategy;
        }
    }
}
