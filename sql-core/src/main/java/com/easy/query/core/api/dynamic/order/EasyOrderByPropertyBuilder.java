package com.easy.query.core.api.dynamic.order;

import com.easy.query.core.expression.lambda.Property;

/**
 * @FileName: EasyOrderByPropertyBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:17
 * @author xuejiaming
 */
public interface EasyOrderByPropertyBuilder<TEntity> {
    EasyOrderByPropertyBuilder<TEntity> orderProperty(Property<TEntity,?> orderProperty);
}
