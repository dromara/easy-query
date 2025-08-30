package com.easy.query.core.migration.data;

/**
 * create time 2025/8/21 22:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnMigrationData {
    private String name;
    private String comment;
    private String dbType;
    private boolean notNull;
    private boolean generatedKey;
    private String defValue;
    private boolean primary;
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
