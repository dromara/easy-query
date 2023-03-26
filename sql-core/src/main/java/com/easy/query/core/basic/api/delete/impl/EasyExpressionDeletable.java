package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.abstraction.AbstractExpressionDeletable;
import com.easy.query.core.expression.sql.SqlEntityDeleteExpression;

/**
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 * @author xuejiaming
 */
public class EasyExpressionDeletable<T> extends AbstractExpressionDeletable<T> {
    public EasyExpressionDeletable(Class<T> clazz, SqlEntityDeleteExpression sqlEntityDeleteExpression) {
        super(clazz, sqlEntityDeleteExpression);
    }

    @Override
    public String toSql() {
        return sqlEntityDeleteExpression.toSql();
    }
}
