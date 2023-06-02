package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable1.java
 * @Description: 文件说明
 * @Date: 2023/3/4 14:12
 */
public abstract class AbstractClientQueryable1<T> extends AbstractObjectQueryable<T> {
    public AbstractClientQueryable1(Class<T> tClass, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        super(tClass, entityQueryExpressionBuilder);
    }

}
