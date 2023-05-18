package com.easy.query.core.api.dynamic.sort;

import com.easy.query.core.expression.lambda.Property;

/**
 * @FileName: EasyOrderByPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:17
 * @author xuejiaming
 */
public interface ObjectSortPropertyBuilder<TEntity> {
    /**
     * 允许的排序字段
     * @param property
     * @return
     */
    ObjectSortPropertyBuilder<TEntity> property(Property<TEntity,?> property);
}
