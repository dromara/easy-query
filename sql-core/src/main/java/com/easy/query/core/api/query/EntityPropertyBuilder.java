package com.easy.query.core.api.query;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.LambdaUtil;

import java.util.Map;

/**
 * @FileName: EntityPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:18
 * @Created by xuejiaming
 */
public class EntityPropertyBuilder<TEntity,TObject> {
    private final Class<?> entityClass;
    private final Map<String, EntityPropertyNode> propertyMap;

    public EntityPropertyBuilder(Class<?> entityClass,Map<String,EntityPropertyNode> propertyMap){
        this.entityClass = entityClass;

        this.propertyMap = propertyMap;
    }
    public EntityPropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty,Property<TObject,?> objectProperty){
        String entityPropertyName = LambdaUtil.getPropertyName(entityProperty);
        String propertyName = LambdaUtil.getPropertyName(objectProperty);
        propertyMap.put(propertyName,new EntityPropertyNode(entityClass,entityPropertyName));
        return this;
    }
    public EntityPropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty,Property<TObject,?> objectProperty1,Property<TObject,?> objectProperty2){
        String entityPropertyName = LambdaUtil.getPropertyName(entityProperty);
        String propertyName1 = LambdaUtil.getPropertyName(objectProperty1);
        String propertyName2 = LambdaUtil.getPropertyName(objectProperty2);
        propertyMap.put(propertyName1,new EntityPropertyNode(entityClass,entityPropertyName));
        propertyMap.put(propertyName2,new EntityPropertyNode(entityClass,entityPropertyName));
        return this;
    }
}
