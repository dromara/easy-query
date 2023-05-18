package com.easy.query.core.api.dynamic.sort.internal;

/**
 * @FileName: OrderByPropertyNode.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:50
 * @author xuejiaming
 */
public class ObjectSortPropertyNode {

    private final Class<?> entityClass;
    private final boolean asc;

    public ObjectSortPropertyNode(Class<?> entityClass, boolean asc){

        this.entityClass = entityClass;
        this.asc = asc;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public boolean isAsc() {
        return asc;
    }
}
