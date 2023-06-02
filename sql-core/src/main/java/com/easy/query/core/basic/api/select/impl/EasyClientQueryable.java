package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable1;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * create time 2023/6/1 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyClientQueryable<T> extends AbstractClientQueryable1<T> {
    public EasyClientQueryable(Class<T> tClass, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(tClass, sqlEntityExpression);
    }
}
