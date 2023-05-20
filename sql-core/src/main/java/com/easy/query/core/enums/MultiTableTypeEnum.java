package com.easy.query.core.enums;

/**
 * 用于表示查询时候的表所处定位是什么
 * @FileName: SelectTableInfoTypeEnum.java
 * @Description: 文件说明
 * @Date: 2023/2/7 11:40
 * @author xuejiaming
 */
public enum MultiTableTypeEnum {
    NONE(""),
    FROM(" FROM "),
    LEFT_JOIN(" LEFT JOIN "),
    INNER_JOIN(" INNER JOIN "),
    RIGHT_JOIN(" RIGHT JOIN ");

    private final String appendSql;

    MultiTableTypeEnum(String appendSql) {
        this.appendSql = appendSql;
    }

    public String getAppendSql() {
        return appendSql;
    }
}
