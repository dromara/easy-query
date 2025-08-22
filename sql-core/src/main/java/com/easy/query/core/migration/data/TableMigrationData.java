package com.easy.query.core.migration.data;

import java.util.List;

/**
 * create time 2025/8/21 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableMigrationData {
    private String key;
    private String schema;
    /**
     * 表名
     */
    private String tableName;
    private String oldTableName;
    private String comment;

    private List<ColumnMigrationData> columns;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOldTableName() {
        return oldTableName;
    }

    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ColumnMigrationData> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMigrationData> columns) {
        this.columns = columns;
    }
}
