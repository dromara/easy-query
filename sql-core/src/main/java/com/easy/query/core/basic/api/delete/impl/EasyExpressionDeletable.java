package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.abstraction.AbstractExpressionDeletable;
import com.easy.query.core.expression.sql.EntityDeleteExpression;

/**
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 * @author xuejiaming
 */
public class EasyExpressionDeletable<T> extends AbstractExpressionDeletable<T> {
    public EasyExpressionDeletable(Class<T> clazz, EntityDeleteExpression entityDeleteExpression) {
        super(clazz, entityDeleteExpression);
    }

    @Override
    public String toSql() {
        return entityDeleteExpression.toSql();
    }
}
