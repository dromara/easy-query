package com.easy.query.core.migration;

import java.util.Objects;

/**
 * create time 2025/1/14 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnDbTypeResult {
    /**
     * 比如varchar(255)
     */
    public final String columnType;
    /**
     * null表示没有默认值
     */
    public final String defValue;

    public ColumnDbTypeResult(String columnType) {
        this(columnType, null);
    }

    public ColumnDbTypeResult(String columnType, String defValue) {
        Objects.requireNonNull(columnType, "columnType cannot be null");
        this.columnType = columnType;
        this.defValue = defValue;
    }
}
