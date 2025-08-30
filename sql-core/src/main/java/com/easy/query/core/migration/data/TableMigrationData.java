package com.easy.query.core.migration.data;

import java.util.Collections;
import java.util.List;

/**
 * create time 2025/8/21 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableMigrationData {
    private String schema;
    /**
     * 表名
     */
    private String tableName;
    private String oldTableName;
    private String comment;

    private List<ColumnMigrationData> columns;
    private List<ForeignKeyMigrationData> foreignKeys;
    private List<IndexMigrationData> indexes;



    public String getSchema() {
        return schema;
    }
    public String getSchemaOrDefault(String defaultSchema) {
        if(schema==null){
            return defaultSchema;
        }
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
        if(columns==null){
            return Collections.emptyList();
        }
        return columns;
    }

    public void setColumns(List<ColumnMigrationData> columns) {
        this.columns = columns;
    }

    public List<ForeignKeyMigrationData> getForeignKeys() {
        if(foreignKeys==null){
            return Collections.emptyList();
        }
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKeyMigrationData> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public List<IndexMigrationData> getIndexes() {
        if(indexes==null){
            return Collections.emptyList();
        }
        return indexes;
    }

    public void setIndexes(List<IndexMigrationData> indexes) {
        this.indexes = indexes;
    }
}
