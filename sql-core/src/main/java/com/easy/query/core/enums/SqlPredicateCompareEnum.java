package com.easy.query.core.enums;


/**
 * @FileName: SqlKeywordEnum.java
 * @Description: 文件说明
 * @Date: 2023/2/13 21:40
 * @Created by xuejiaming
 */
public enum SqlPredicateCompareEnum implements SqlPredicateCompare {

    IN("IN"),
    NOT_IN("NOT IN"),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    EQ("="),
    NE("<>"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),;
    private final String sql;

    SqlPredicateCompareEnum(String sql){

        this.sql = sql;
    }

    @Override
    public String getSql() {
        return sql;
    }
}
