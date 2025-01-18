package com.easy.query.core.migration;

/**
 * create time 2025/1/14 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnDbTypeResult {
    public final String columnType;
    public final Object defValue;

    public ColumnDbTypeResult(String columnType, Object defValue) {
        this.columnType = columnType;
        this.defValue = defValue;
    }
}
