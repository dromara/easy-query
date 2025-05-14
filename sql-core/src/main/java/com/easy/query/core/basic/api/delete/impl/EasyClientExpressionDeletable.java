package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.abstraction.AbstractClientExpressionDeletable;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * create time 2023/3/6 13:21
 */
public class EasyClientExpressionDeletable<T> extends AbstractClientExpressionDeletable<T> {
    public EasyClientExpressionDeletable(Class<T> clazz, EntityDeleteExpressionBuilder entityDeleteExpression) {
        super(clazz, entityDeleteExpression);
    }
}
