package com.easy.query.core.migration.data;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * create time 2025/8/21 22:16
 * 列迁移数据
 *
 * @author xuejiaming
 */
public class ColumnMigrationData {
    /**
     * 列名不能为空
     */
    private String name;
    /**
     * 列注释
     */
    private String comment;
    /**
     * 数据库类型不能为空
     */
    private String dbType;
    /**
     * 是否非空
     */
    private boolean notNull;
    /**
     * 是否是自增
     */
    private boolean generatedKey;
    /**
     * 默认值可为空
     */
    private String defValue;
    /**
     * 是否主键
     */
    private boolean primary;
    /**
     * 旧列名
     */
    private String oldColumnName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(boolean generatedKey) {
        this.generatedKey = generatedKey;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getOldColumnName() {
        return oldColumnName;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }
}
