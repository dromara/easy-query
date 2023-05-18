package com.easy.query.core.api.dynamic.condition.internal;

import com.easy.query.core.api.dynamic.condition.ObjectQueryPropertyBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Map;

/**
 * @FileName: EntityPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:18
 * @author xuejiaming
 */
public class ObjectQueryPropertyBuilderImpl<TEntity,TObject> implements ObjectQueryPropertyBuilder<TEntity,TObject> {
    private final Class<?> entityClass;
    private final Map<String, ObjectQueryPropertyNode> propertyMap;

    public ObjectQueryPropertyBuilderImpl(Class<?> entityClass, Map<String, ObjectQueryPropertyNode> propertyMap){
        this.entityClass = entityClass;
        this.propertyMap = propertyMap;
    }
    public ObjectQueryPropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty){
        String entityPropertyName = EasyLambdaUtil.getPropertyName(entityProperty);
        String propertyName = EasyLambdaUtil.getPropertyName(objectProperty);
        propertyMap.put(propertyName,new ObjectQueryPropertyNode(entityClass,entityPropertyName));
        return this;
    }
    public ObjectQueryPropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty1, Property<TObject,?> objectProperty2){
        String entityPropertyName = EasyLambdaUtil.getPropertyName(entityProperty);
        String propertyName1 = EasyLambdaUtil.getPropertyName(objectProperty1);
        String propertyName2 = EasyLambdaUtil.getPropertyName(objectProperty2);
        propertyMap.put(propertyName1,new ObjectQueryPropertyNode(entityClass,entityPropertyName));
        propertyMap.put(propertyName2,new ObjectQueryPropertyNode(entityClass,entityPropertyName));
        return this;
    }
}
