package com.easy.query.core.api.dynamic.where.internal;

import com.easy.query.core.api.dynamic.where.EasyDynamicWhereBuilder;
import com.easy.query.core.api.dynamic.where.EasyDynamicWherePropertyBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: EntityQueryBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:15
 * @Created by xuejiaming
 */
public class DefaultEasyDynamicWhereBuilder<TObject> implements EasyDynamicWhereBuilder<TObject> {
    private final Map<String, DynamicWherePropertyNode> propertyMap=new HashMap<>();
    public <TEntity> EasyDynamicWherePropertyBuilder<TEntity,TObject> entity(Class<TEntity> entityClass){
        return new DefaultEasyDynamicWherePropertyBuilder<>(entityClass,propertyMap);
    }

    public DynamicWherePropertyNode getPropertyMapping(String propertyName){
        return propertyMap.get(propertyName);
    }
}
