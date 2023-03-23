package com.easy.query.core.api.query.order.internal;

/**
 * @FileName: OrderByPropertyNode.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:50
 * @Created by xuejiaming
 */
public class OrderByPropertyNode {

    private final Class<?> entityClass;
    private final boolean asc;

    public OrderByPropertyNode(Class<?> entityClass, boolean asc){

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
