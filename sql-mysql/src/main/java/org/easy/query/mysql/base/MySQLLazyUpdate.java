package org.easy.query.mysql.base;

import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.basic.api.update.EntityUpdate;

/**
 * @FileName: MySQLLazyUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 12:36
 * @Created by xuejiaming
 */
public class MySQLLazyUpdate<T> implements EntityUpdate<T> {
    @Override
    public EntityUpdate<T> setColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdate<T> setIgnoreColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdate<T> whereColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
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
