package com.easy.query.core.api.dynamic.sort.internal;

import com.easy.query.core.api.dynamic.sort.ObjectSortPropertyBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Map;

/**
 * @FileName: OrderByPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:24
 * @author xuejiaming
 */
public class ObjectSortPropertyBuilderImpl<TEntity> implements ObjectSortPropertyBuilder<TEntity> {
    private final Map<String, Class<?>> orderEntityMap;
    private final Class<TEntity> entityClass;

    public ObjectSortPropertyBuilderImpl(Map<String,Class<?>> orderEntityMap, Class<TEntity> entityClass){

        this.orderEntityMap = orderEntityMap;
        this.entityClass = entityClass;
    }
    public ObjectSortPropertyBuilder<TEntity> property(Property<TEntity,?> property){
        String propertyName = EasyLambdaUtil.getPropertyName(property);
        orderEntityMap.put(propertyName,entityClass);
        return this;
    }
}
