package com.easy.query.core.enums;



/**
 * @FileName: SqlKeywordEnum.java
 * @Description: 文件说明
 * @Date: 2023/2/13 21:40
 * @author xuejiaming
 */
public enum SQLKeywordEnum {

    AND("AND"),
    OR("OR"),
    NOT("NOT"),
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
    IS_NOT_NULL("IS NOT NULL"),
    GROUP_BY("GROUP BY"),
    HAVING("HAVING"),
    ORDER_BY("ORDER BY"),
    EXISTS("EXISTS"),
    NOT_EXISTS("NOT EXISTS"),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    ASC("ASC"),
    DESC("DESC"),
    DOT(",");
    private final String keyword;

    SQLKeywordEnum(String keyword){

        this.keyword = keyword;
    }

    public String toSQL() {
        return keyword;
    }
}
