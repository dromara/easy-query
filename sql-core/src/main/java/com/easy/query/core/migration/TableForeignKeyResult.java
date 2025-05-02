package com.easy.query.core.migration;

/**
 * create time 2025/5/2 00:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableForeignKeyResult {
    public final String name;
    public final String action;
    public final String selfTable;
    public final String targetTable;
    public final String[] selfColumn;
    public final String[] targetColumn;
    public TableForeignKeyResult(String name,String action,String selfTable,String targetTable,String[] selfColumn,String[] targetColumn){
        this.name=name;
        this.action = action;
        this.selfTable=selfTable;
        this.targetTable=targetTable;
        this.selfColumn=selfColumn;
        this.targetColumn=targetColumn;
    }
}
