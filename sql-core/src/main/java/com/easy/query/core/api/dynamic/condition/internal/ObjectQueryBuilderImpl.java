package com.easy.query.core.api.dynamic.condition.internal;

import com.easy.query.core.api.dynamic.condition.ObjectQueryBuilder;
import com.easy.query.core.api.dynamic.condition.ObjectQueryPropertyBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: EntityQueryBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:15
 * @author xuejiaming
 */
public class ObjectQueryBuilderImpl<TObject> implements ObjectQueryBuilder<TObject> {
    private final Map<String, ObjectQueryPropertyNode> propertyMap=new HashMap<>();
    public <TEntity> ObjectQueryPropertyBuilder<TEntity,TObject> mapTo(Class<TEntity> entityClass){
        return new ObjectQueryPropertyBuilderImpl<>(entityClass,propertyMap);
    }

    public ObjectQueryPropertyNode getPropertyMapping(String propertyName){
        return propertyMap.get(propertyName);
    }
}
