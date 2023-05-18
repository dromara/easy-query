package com.easy.query.core.api.dynamic.condition;

import com.easy.query.core.expression.lambda.Property;

/**
 * @FileName: DynamicWherePropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/24 07:49
 * @author xuejiaming
 */
public interface ObjectQueryPropertyBuilder<TEntity,TObject> {

     ObjectQueryPropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty);
     ObjectQueryPropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty1, Property<TObject,?> objectProperty2);
}
