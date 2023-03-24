package com.easy.query.core.api.dynamic.order.internal;

/**
 * @FileName: OrderByPropertyNode.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:50
 * @author xuejiaming
 */
public class DynamicOrderByPropertyNode {

    private final Class<?> entityClass;
    private final boolean asc;

    public DynamicOrderByPropertyNode(Class<?> entityClass, boolean asc){

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
