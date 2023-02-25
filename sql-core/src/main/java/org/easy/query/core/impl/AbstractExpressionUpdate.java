package org.easy.query.core.impl;

import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.base.SqlColumnSetter;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.basic.api.ExpressionUpdate;
import org.easy.query.core.basic.api.Update;

/**
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 * @Created by xuejiaming
 */
public class AbstractExpressionUpdate<T> implements ExpressionUpdate<T> {
    private final Class<T> clazz;
    private final UpdateContext updateContext;

    public AbstractExpressionUpdate(Class<T> clazz, UpdateContext updateContext){

        this.clazz = clazz;
        this.updateContext = updateContext;
    }
    @Override
    public Update<T> set(SqlExpression<SqlColumnSetter<T>> setExpression) {
        return null;
    }

    @Override
    public Update<T> where(SqlExpression<SqlPredicate<T>> whereExpression) {
        return null;
    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public void executeRows(Long expectRow, String error) {

    }

    @Override
    public String toSql() {
        return null;
    }
}
