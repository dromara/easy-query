package com.easy.query.core.metadata;


/**
 * @FileName: ColumnInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/10 23:35
 * @author xuejiaming
 */
public class ColumnInfo {
    private final TableInfo tableInfo;
    private final String name;
    private  boolean primary=false;
    private  boolean increment=false;


    private  boolean nullable=false;
    private  boolean version=false;

    public ColumnInfo(TableInfo tableInfo, String name) {
        this.tableInfo = tableInfo;
        this.name = name;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }
    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }
}
