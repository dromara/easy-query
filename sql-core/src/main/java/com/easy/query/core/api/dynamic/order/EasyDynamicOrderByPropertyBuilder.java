package com.easy.query.core.api.dynamic.order;

import com.easy.query.core.expression.lambda.Property;

/**
 * @FileName: EasyOrderByPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:17
 * @Created by xuejiaming
 */
public interface EasyDynamicOrderByPropertyBuilder<TEntity> {
    EasyDynamicOrderByPropertyBuilder<TEntity> orderProperty(Property<TEntity,?> orderProperty);
}
