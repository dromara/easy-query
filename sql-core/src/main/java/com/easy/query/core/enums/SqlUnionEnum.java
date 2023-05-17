package com.easy.query.core.enums;

/**
 * create time 2023/5/17 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public enum SqlUnionEnum {
    UNION("UNION"),
    UNION_ALL("UNION ALL");
    private final String sql;

    SqlUnionEnum(String sql){

        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
