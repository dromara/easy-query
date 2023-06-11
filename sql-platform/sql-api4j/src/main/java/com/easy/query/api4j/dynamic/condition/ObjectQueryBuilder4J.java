package com.easy.query.api4j.dynamic.condition;

import com.easy.query.core.expression.lambda.Property;

/**
 * create time 2023/6/11 09:40
 * 文件说明
 *
 * @author xuejiaming
 */

/**
 * 映射查询对象和数据库对象的属性
 * @param <TObject> 查询对象
 * @param <TEntity> query结果对象
 */
public interface ObjectQueryBuilder4J<TObject,TEntity> {
    ObjectQueryBuilder4J<TObject,TEntity> property(Property<TEntity,?> entityProperty, Property<TObject,?> property);
    ObjectQueryBuilder4J<TObject,TEntity> property(Property<TEntity,?> entityProperty, Property<TObject,?> property1, Property<TObject,?> property2);
}

