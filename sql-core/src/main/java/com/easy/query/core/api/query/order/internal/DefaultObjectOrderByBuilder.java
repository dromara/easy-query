package com.easy.query.core.api.query.order.internal;

import com.easy.query.core.api.query.order.EasyOrderByBuilder;
import com.easy.query.core.api.query.order.EasyOrderByPropertyBuilder;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @FileName: ObjectOrderBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:24
 * @Created by xuejiaming
 */
public class DefaultObjectOrderByBuilder implements EasyOrderByBuilder, OrderByPropertyGetter {
    private final Map<String,Class<?>> orderEntityMap=new HashMap<>();
    private final Map<String, OrderByPropertyNode> orderPropertyMap=new LinkedHashMap<>();
    public <TEntity> EasyOrderByPropertyBuilder<TEntity> entity(Class<TEntity> entityClass){
        return new OrderByPropertyBuilder<>(orderEntityMap,entityClass);
    }

    @Override
    public EasyOrderByBuilder orderBy(String propertyName, boolean asc) {
        Class<?> entityClass = orderEntityMap.get(propertyName);
        if(entityClass==null){
            throw new EasyQueryOrderByInvalidOperationException(propertyName,"object order by unknown property "+propertyName);
        }
        orderPropertyMap.put(propertyName,new OrderByPropertyNode(entityClass,asc));
        return this;
    }

    @Override
    public Map<String, OrderByPropertyNode> getOrderProperties() {
        return orderPropertyMap;
    }
}
