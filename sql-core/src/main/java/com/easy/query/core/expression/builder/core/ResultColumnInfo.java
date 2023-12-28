package com.easy.query.core.expression.builder.core;

import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/12/28 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class ResultColumnInfo {
    private final ColumnMetadata columnMetadata;
    private final String columnAsName;

    public ResultColumnInfo(ColumnMetadata columnMetadata, String columnAsName){

        this.columnMetadata = columnMetadata;
        this.columnAsName = columnAsName;
    }

    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    public String getColumnAsName() {
        return columnAsName;
    }
}
