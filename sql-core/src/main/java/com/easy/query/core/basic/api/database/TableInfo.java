package com.easy.query.core.basic.api.database;

/**
 * create time 2026/4/29 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableInfo {
    private final String schema;
    private final String tableName;
    public TableInfo(String schema,String tableName){
        this.schema = schema;
        this.tableName = tableName;
    }

    public String getSchema() {
        return schema;
    }

    public String getTableName() {
        return tableName;
    }
}
