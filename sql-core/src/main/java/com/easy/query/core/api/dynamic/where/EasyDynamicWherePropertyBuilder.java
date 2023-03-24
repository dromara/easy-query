package com.easy.query.core.api.dynamic.where;

import com.easy.query.core.expression.lambda.Property;

/**
 * @FileName: DynamicWherePropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/24 07:49
 * @author xuejiaming
 */
public interface EasyDynamicWherePropertyBuilder<TEntity,TObject> {

     EasyDynamicWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty);
     EasyDynamicWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty1, Property<TObject,?> objectProperty2);
}
