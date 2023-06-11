package com.easy.query.api4j.dynamic.condition.impl;

import com.easy.query.api4j.dynamic.condition.ObjectQueryBuilder4J;
import com.easy.query.core.api.dynamic.condition.ObjectQueryBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.EasyLambdaUtil;

/**
 * create time 2023/6/11 09:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class ObjectQueryBuilder4JImpl<TObject,TEntity> implements ObjectQueryBuilder4J<TObject,TEntity> {
    private final ObjectQueryBuilder objectQueryBuilder;

    public ObjectQueryBuilder4JImpl(ObjectQueryBuilder objectQueryBuilder){

        this.objectQueryBuilder = objectQueryBuilder;
    }

    @Override
    public ObjectQueryBuilder4J<TObject, TEntity> property(Property<TEntity, ?> entityProperty, Property<TObject, ?> property) {
        objectQueryBuilder.property(EasyLambdaUtil.getPropertyName(entityProperty),EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public ObjectQueryBuilder4J<TObject, TEntity> property(Property<TEntity, ?> entityProperty, Property<TObject, ?> property1, Property<TObject, ?> property2) {
        objectQueryBuilder.property(EasyLambdaUtil.getPropertyName(entityProperty),EasyLambdaUtil.getPropertyName(property1),EasyLambdaUtil.getPropertyName(property2));
        return this;
    }
}
