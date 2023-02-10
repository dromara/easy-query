package org.jdqc.core.metadata;

/**
 * @FileName: ColumnInfo.java
 * @Description: 文件说明
 * @Date: 2023/2/10 23:35
 * @Created by xuejiaming
 */
public class ColumnInfo {
    private final TableInfo tableInfo;
    private final String name;
    private final boolean primary;
    private final boolean increment;
    private final boolean version;

    public ColumnInfo(TableInfo tableInfo, String name, boolean primary, boolean increment, boolean version) {
        this.tableInfo = tableInfo;
        this.name = name;
        this.primary = primary;
        this.increment = increment;
        this.version = version;
    }
    public ColumnInfo(TableInfo tableInfo, String name, boolean primary, boolean increment) {
        this(tableInfo,name,primary,increment,false);
    }
    public ColumnInfo(TableInfo tableInfo, String name, boolean primary) {
        this(tableInfo,name,primary,false,false);
    }
    public ColumnInfo(TableInfo tableInfo, String name) {
        this(tableInfo,name,false,false,false);
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

    public boolean isIncrement() {
        return increment;
    }

    public boolean isVersion() {
        return version;
    }
}
