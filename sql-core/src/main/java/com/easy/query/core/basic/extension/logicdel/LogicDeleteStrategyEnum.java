package com.easy.query.core.basic.extension.logicdel;

/**
 * create time 2023/3/6 22:36
 * 逻辑删除策略
 *
 * @author xuejiaming
 */
public enum LogicDeleteStrategyEnum {
    /**
     * 用户自定义
     */
    CUSTOM("CUSTOM"),
    /**
     * TRUE:表示被删除 deleted
     * FALSE:表示未被删除 not delete
     */
    BOOLEAN("BOOLEAN"),
    /**
     * 0:表示未被删除
     * !=0:不等于0表示被删除
     */
    DELETE_LONG_TIMESTAMP("DELETE_LONG_TIMESTAMP"),
    /**
     * 删除时间
     * null 表示未被删除
     * not null 表示被删除
     */
    LOCAL_DATE_TIME("LOCAL_DATE_TIME"),
    /**
     * 删除日期
     * null 表示未被删除
     * not null 表示被删除
     */
    LOCAL_DATE("LOCAL_DATE");
    private final String strategy;

    LogicDeleteStrategyEnum(String strategy) {

        this.strategy = strategy;
    }

    public String getStrategy() {
        return strategy;
    }
}
