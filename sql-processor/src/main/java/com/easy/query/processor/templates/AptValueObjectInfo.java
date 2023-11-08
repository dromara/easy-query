package com.easy.query.processor.templates;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/11/8 16:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptValueObjectInfo {
    private final String entityName;
    private final List<AptPropertyInfo> properties;
    private final List<AptValueObjectInfo> children;

    public AptValueObjectInfo(String entityName){

        this.entityName = entityName;
        this.properties = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    /**
     * valueObject属性所属类名
     * @return
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * 这个类有哪些属性
     * @return
     */
    public List<AptPropertyInfo> getProperties() {
        return properties;
    }

    /**
     * 嵌套valueObject
     * @return
     */
    public List<AptValueObjectInfo> getChildren() {
        return children;
    }

    public String toFileString() {
        return null;
    }
}
