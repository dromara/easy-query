package com.easy.query.core.api.query;

/**
 * @FileName: EntityPropertyNode.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:57
 * @Created by xuejiaming
 */
public class EntityPropertyNode {
    private final Class<?> entityClass;
    private final String property;

    public EntityPropertyNode(Class<?> entityClass, String property){

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
