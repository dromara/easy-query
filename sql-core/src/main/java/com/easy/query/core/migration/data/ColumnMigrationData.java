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
    private Boolean nullable;
    private Boolean generatedKey;
    private String defValue;
    private Boolean primary;
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

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(Boolean generatedKey) {
        this.generatedKey = generatedKey;
    }

    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getOldColumnName() {
        return oldColumnName;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }
}
