package com.easy.query.core.common;

/**
 * create time 2024/10/16 15:54
 * 文件说明
 *
 * @author xuejiaming
 */
public final class RelationColumnResult {
    private final String property;
    private boolean exists;

    public RelationColumnResult(String property){
        this.property = property;
        this.exists = false;
    }

    public String getProperty() {
        return property;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
