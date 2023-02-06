package org.jdqc.sql.core.common;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: TableinFO.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:40
 * @Created by xuejiaming
 */
public class TableInfo {
    public TableInfo(Class<?> tableType) {
        this.tableType = tableType;
    }

    private final Class<?> tableType;

    public Class<?> getTableType() {
        return tableType;
    }
}
