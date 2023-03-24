package com.easy.query.core.api.dynamic.where.internal;

import com.easy.query.core.api.dynamic.where.EasyDynamicWherePropertyBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.LambdaUtil;

import java.util.Map;

/**
 * @FileName: EntityPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:18
 * @Created by xuejiaming
 */
public class DefaultEasyDynamicWherePropertyBuilder<TEntity,TObject> implements EasyDynamicWherePropertyBuilder<TEntity,TObject> {
    private final Class<?> entityClass;
    private final Map<String, DynamicWherePropertyNode> propertyMap;

    public DefaultEasyDynamicWherePropertyBuilder(Class<?> entityClass, Map<String, DynamicWherePropertyNode> propertyMap){
        this.entityClass = entityClass;
        this.propertyMap = propertyMap;
    }
    public EasyDynamicWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty){
        String entityPropertyName = LambdaUtil.getPropertyName(entityProperty);
        String propertyName = LambdaUtil.getPropertyName(objectProperty);
        propertyMap.put(propertyName,new DynamicWherePropertyNode(entityClass,entityPropertyName));
        return this;
    }
    public EasyDynamicWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty1, Property<TObject,?> objectProperty2){
        String entityPropertyName = LambdaUtil.getPropertyName(entityProperty);
        String propertyName1 = LambdaUtil.getPropertyName(objectProperty1);
        String propertyName2 = LambdaUtil.getPropertyName(objectProperty2);
        propertyMap.put(propertyName1,new DynamicWherePropertyNode(entityClass,entityPropertyName));
        propertyMap.put(propertyName2,new DynamicWherePropertyNode(entityClass,entityPropertyName));
        return this;
    }
}
