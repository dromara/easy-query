package com.easy.query.api4j.dynamic.sort;

import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;

/**
 * create time 2023/6/11 11:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ObjectSortBuilder4J<TEntity> {
    /**
     * 添加字段属性排序
     * @exception EasyQueryOrderByInvalidOperationException 当添加的属性 {@param propertyName} 不存在本次查询被允许的排序字段里面时抛出
     * @param propertyName
     * @param asc
     * @return
     */
  ObjectSortBuilder4J<TEntity> orderBy(String propertyName, boolean asc);
    ObjectSortBuilder4J<TEntity> allowed(Property <TEntity,?> property);
    ObjectSortBuilder4J<TEntity> notAllowed(Property <TEntity,?> property);

}
