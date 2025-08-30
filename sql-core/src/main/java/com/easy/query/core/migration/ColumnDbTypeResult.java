package com.easy.query.core.migration;

import java.util.Objects;

/**
 * create time 2025/1/14 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnDbTypeResult {
    public final String columnType;
    public final String defValue;

    public ColumnDbTypeResult(String columnType, String defValue) {
        Objects.requireNonNull(columnType, "columnType cannot be null");
        this.columnType = columnType;
        this.defValue = defValue;
    }
}
