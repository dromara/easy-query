package com.easy.query.core.migration;

/**
 * create time 2025/1/14 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnTypeResult {
    public final String columnType;
    public final String columnTypeNotNull;
    public final Object defValue;

    public ColumnTypeResult(String columnType, String columnTypeNotNull,Object defValue) {
        this.columnType = columnType;
        this.columnTypeNotNull = columnTypeNotNull;
        this.defValue = defValue;
    }
}
