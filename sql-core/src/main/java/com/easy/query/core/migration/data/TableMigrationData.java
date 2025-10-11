package com.easy.query.core.migration.data;

import com.easy.query.core.util.EasyStringUtil;

import java.util.Collections;
import java.util.List;

/**
 * create time 2025/8/21 22:15
 * 表迁移数据
 *
 * @author xuejiaming
 */
public class TableMigrationData {
    /**
     * schema
     */
    private String schema;
    /**
     * 表名不能为空
     */
    private String tableName;
    /**
     * 原表名如果有表示要修改表名
     */
    private String oldTableName;
    /**
     * 表注释信息
     */
    private String comment;

    /**
     * 列信息
     */
    private List<ColumnMigrationData> columns;
    /**
     * 外键信息
     */
    private List<ForeignKeyMigrationData> foreignKeys;
    /**
     * 索引信息
     */
    private List<IndexMigrationData> indexes;



    public String getSchema() {
        return schema;
    }
    public String getSchemaOrDefault(String defaultSchema) {
        return EasyStringUtil.defaultIfBank(schema, defaultSchema);
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
