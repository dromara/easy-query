package org.jdqc.sql.core.schema;

/**
 *
 * @FileName: ColumnInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:17
 * @Created by xuejiaming
 */
public class ColumnInfo {
    private final TableInfo tableInfo;
    private final String columnName;

    public ColumnInfo(TableInfo tableInfo,String columnName) {
        this.tableInfo = tableInfo;
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }
}
