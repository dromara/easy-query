package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @FileName: AbstractQueryable1.java
 * @Description: 文件说明
 * @Date: 2023/3/4 14:12
 * @author xuejiaming
 */
public abstract class AbstractQueryable1<T> extends AbstractQueryable<T> {
    public AbstractQueryable1(Class<T> tClass, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        super(tClass, entityQueryExpressionBuilder);  }

}
