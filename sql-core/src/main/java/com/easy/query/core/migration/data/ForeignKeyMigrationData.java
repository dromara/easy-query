package com.easy.query.core.migration.data;

/**
 * create time 2025/8/29 19:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class ForeignKeyMigrationData {

    /**
     * 外键明不能为空
     */
    private String name;
    /**
     * 外键行为
     */
    private String action;
    /**
     * 当前表
     */
    private String selfTable;
    /**
     * 外键表
     */
    private String targetTable;
    /**
     * 当前表表字段
     */
    private String[] selfColumn;

    /**
     * 外键表字段
     */
    private String[] targetColumn;

    public String[] getTargetColumn() {
        if (targetColumn == null) {
            return new String[0];
        }
        return targetColumn;
    }

    public void setTargetColumn(String[] targetColumn) {
        this.targetColumn = targetColumn;
    }

    public String[] getSelfColumn() {
        if (selfColumn == null) {
            return new String[0];
        }
        return selfColumn;
    }

    public void setSelfColumn(String[] selfColumn) {
        this.selfColumn = selfColumn;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getSelfTable() {
        return selfTable;
    }

    public void setSelfTable(String selfTable) {
        this.selfTable = selfTable;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
