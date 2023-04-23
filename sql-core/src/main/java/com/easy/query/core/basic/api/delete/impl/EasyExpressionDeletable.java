package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.abstraction.AbstractExpressionDeletable;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;

/**
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 * @author xuejiaming
 */
public class EasyExpressionDeletable<T> extends AbstractExpressionDeletable<T> {
    public EasyExpressionDeletable(Class<T> clazz, EntityDeleteExpressionBuilder entityDeleteExpression) {
        super(clazz, entityDeleteExpression);
    }
}
