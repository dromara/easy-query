package org.easy.query.core.enums;


import org.easy.query.core.expression.segment.SqlSegment;

/**
 * @FileName: SqlKeywordEnum.java
 * @Description: 文件说明
 * @Date: 2023/2/13 21:40
 * @Created by xuejiaming
 */
public enum SqlKeywordEnum implements SqlSegment {

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

    SqlKeywordEnum(String keyword){

        this.keyword = keyword;
    }

    @Override
    public String toSql() {
        return keyword;
    }
}
