package org.jdqc.sql.core.common;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: ColumnInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:17
 * @Created by xuejiaming
 */
public class ColumnInfo {
    private final String columnName;

    public ColumnInfo(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
