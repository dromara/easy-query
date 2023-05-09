package com.easy.query.core.api.dynamic.where;

import com.easy.query.core.expression.lambda.Property;

/**
 * @FileName: DynamicWherePropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/24 07:49
 * @author xuejiaming
 */
public interface EasyWherePropertyBuilder<TEntity,TObject> {

     EasyWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty);
     EasyWherePropertyBuilder<TEntity,TObject> property(Property<TEntity,?> entityProperty, Property<TObject,?> objectProperty1, Property<TObject,?> objectProperty2);
}
