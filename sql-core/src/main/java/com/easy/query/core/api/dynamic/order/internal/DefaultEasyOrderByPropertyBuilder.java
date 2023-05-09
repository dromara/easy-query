package com.easy.query.core.api.dynamic.order.internal;

import com.easy.query.core.api.dynamic.order.EasyOrderByPropertyBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.LambdaUtil;

import java.util.Map;

/**
 * @FileName: OrderByPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:24
 * @author xuejiaming
 */
public class DefaultEasyOrderByPropertyBuilder<TEntity> implements EasyOrderByPropertyBuilder<TEntity> {
    private final Map<String, Class<?>> orderEntityMap;
    private final Class<TEntity> entityClass;

    public DefaultEasyOrderByPropertyBuilder(Map<String,Class<?>> orderEntityMap, Class<TEntity> entityClass){

        this.orderEntityMap = orderEntityMap;
        this.entityClass = entityClass;
    }
    public EasyOrderByPropertyBuilder<TEntity> orderProperty(Property<TEntity,?> orderProperty){
        String orderPropertyName = LambdaUtil.getPropertyName(orderProperty);
        orderEntityMap.put(orderPropertyName,entityClass);
        return this;
    }
}
