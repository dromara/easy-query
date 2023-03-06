package org.easy.query.core.basic.api.update.impl;

import org.easy.query.core.basic.api.update.EntityUpdatable;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;

/**
 * @FileName: EasyEmptyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 * @Created by xuejiaming
 */
public class EasyEmptyEntityUpdatable<T> implements EntityUpdatable<T> {
    @Override
    public EntityUpdatable<T> setColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> setIgnoreColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> whereColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
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
