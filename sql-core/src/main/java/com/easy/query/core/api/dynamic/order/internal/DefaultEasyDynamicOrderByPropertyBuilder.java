package com.easy.query.core.api.dynamic.order.internal;

import com.easy.query.core.api.dynamic.order.EasyDynamicOrderByPropertyBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.LambdaUtil;

import java.util.Map;

/**
 * @FileName: OrderByPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:24
 * @Created by xuejiaming
 */
public class DefaultEasyDynamicOrderByPropertyBuilder<TEntity> implements EasyDynamicOrderByPropertyBuilder<TEntity> {
    private final Map<String, Class<?>> orderEntityMap;
    private final Class<TEntity> entityClass;

    public DefaultEasyDynamicOrderByPropertyBuilder(Map<String,Class<?>> orderEntityMap, Class<TEntity> entityClass){

        this.orderEntityMap = orderEntityMap;
        this.entityClass = entityClass;
    }
    public EasyDynamicOrderByPropertyBuilder<TEntity> orderProperty(Property<TEntity,?> orderProperty){
        String orderPropertyName = LambdaUtil.getPropertyName(orderProperty);
        orderEntityMap.put(orderPropertyName,entityClass);
        return this;
    }
}
