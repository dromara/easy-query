package org.easy.query.core.basic.enums;

/**
 * @FileName: LogicDeleteStrategyEnum.java
 * @Description: 逻辑删除策略
 * @Date: 2023/3/6 22:36
 * @Created by xuejiaming
 */
public enum LogicDeleteStrategyEnum {
    CUSTOM("CUSTOM"),
    BOOLEAN("BOOLEAN"),//TRUE FALSE
    LONG_TIMESTAMP("LONG_TIMESTAMP"),//O OR NOT O
    LOCAL_DATE_TIME("LOCAL_DATE_TIME"),//NULL OR NOT NULL
    LOCAL_DATE("LOCAL_DATE");//NULL OR NOT NULL
    private final String strategy;

    LogicDeleteStrategyEnum(String strategy){

        this.strategy = strategy;
    }

    public String getStrategy() {
        return strategy;
    }
}
