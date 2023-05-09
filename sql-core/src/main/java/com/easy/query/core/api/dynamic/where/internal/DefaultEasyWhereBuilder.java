package com.easy.query.core.api.dynamic.where.internal;

import com.easy.query.core.api.dynamic.where.EasyWhereBuilder;
import com.easy.query.core.api.dynamic.where.EasyWherePropertyBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: EntityQueryBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:15
 * @author xuejiaming
 */
public class DefaultEasyWhereBuilder<TObject> implements EasyWhereBuilder<TObject> {
    private final Map<String, DynamicWherePropertyNode> propertyMap=new HashMap<>();
    public <TEntity> EasyWherePropertyBuilder<TEntity,TObject> mapTo(Class<TEntity> entityClass){
        return new DefaultEasyWherePropertyBuilder<>(entityClass,propertyMap);
    }

    public DynamicWherePropertyNode getPropertyMapping(String propertyName){
        return propertyMap.get(propertyName);
    }
}
