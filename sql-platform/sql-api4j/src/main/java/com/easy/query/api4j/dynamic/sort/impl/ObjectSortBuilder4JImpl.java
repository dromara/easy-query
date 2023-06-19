package com.easy.query.api4j.dynamic.sort.impl;

import com.easy.query.api4j.dynamic.sort.ObjectSortBuilder4J;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.api4j.util.EasyLambdaUtil;

/**
 * create time 2023/6/11 11:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class ObjectSortBuilder4JImpl<TEntity> implements ObjectSortBuilder4J<TEntity> {
    private final ObjectSortBuilder objectSortBuilder;

    public ObjectSortBuilder4JImpl(ObjectSortBuilder objectSortBuilder){

        this.objectSortBuilder = objectSortBuilder;
    }

    @Override
    public ObjectSortBuilder4J<TEntity> orderBy(String propertyName, boolean asc) {
        objectSortBuilder.orderBy(propertyName,asc);
        return this;
    }

    @Override
    public ObjectSortBuilder4J<TEntity> allowed(Property<TEntity, ?> property) {
        objectSortBuilder.allowed(EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public ObjectSortBuilder4J<TEntity> notAllowed(Property<TEntity, ?> property) {
        objectSortBuilder.notAllowed(EasyLambdaUtil.getPropertyName(property));
        return this;
    }
}
