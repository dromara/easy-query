package com.easy.query.core.api.dynamic.condition.internal;

/**
 * @FileName: EntityPropertyNode.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:57
 * @author xuejiaming
 */
public class ObjectQueryPropertyNode {
    private final Class<?> entityClass;
    private final String property;

    public ObjectQueryPropertyNode(Class<?> entityClass, String property){

        this.entityClass = entityClass;
        this.property = property;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getProperty() {
        return property;
    }
}
