package com.easy.query.core.api.dynamic.where.internal;

import com.easy.query.core.api.dynamic.where.EasyWherePropertyBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.LambdaUtil;

import java.util.Map;

/**
 * @FileName: EntityPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/22 22:18
 * @author xuejiaming
 */
public class DefaultEasyWherePropertyBuilder<TEntity,TObject> implements EasyWherePropertyBuilder<TEntity,TObject> {
    private final Class<?> entityClass;
    private final Map<String, DynamicWherePropertyNode> propertyMap;

    public DefaultEasyWherePropertyBuilder(Class<?> entityClass, Map<String, DynamicWherePropertyNode> propertyMap){
        this.entityClass = entityClass;
        this.propertyMap = propertyMap;
    }
    public EasyWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty){
        String entityPropertyName = LambdaUtil.getPropertyName(entityProperty);
        String propertyName = LambdaUtil.getPropertyName(objectProperty);
        propertyMap.put(propertyName,new DynamicWherePropertyNode(entityClass,entityPropertyName));
        return this;
    }
    public EasyWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty1, Property<TObject,?> objectProperty2){
        String entityPropertyName = LambdaUtil.getPropertyName(entityProperty);
        String propertyName1 = LambdaUtil.getPropertyName(objectProperty1);
        String propertyName2 = LambdaUtil.getPropertyName(objectProperty2);
        propertyMap.put(propertyName1,new DynamicWherePropertyNode(entityClass,entityPropertyName));
        propertyMap.put(propertyName2,new DynamicWherePropertyNode(entityClass,entityPropertyName));
        return this;
    }
}
