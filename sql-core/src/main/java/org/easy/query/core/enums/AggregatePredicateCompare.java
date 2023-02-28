package org.easy.query.core.enums;

/**
 * @FileName: EasyAggregate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:18
 * @Created by xuejiaming
 */
public enum AggregatePredicateCompare implements SqlPredicateCompare {
    EQ("="),
    NE("<>"),
    /**
     * 大于 >
     */
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<=");
    private final String sql;

     AggregatePredicateCompare(String sql){

        this.sql = sql;
    }

    @Override
    public String getSql() {
        return sql;
    }
}
