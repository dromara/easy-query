package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnUpdateSetSelector;

/**
 * @author xuejiaming
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 */
public interface ClientEntityUpdatable<T> extends Updatable<T, ClientEntityUpdatable<T>>, SQLExecuteStrategy<ClientEntityUpdatable<T>> {
    default ClientEntityUpdatable<T> setColumns(SQLExpression1<ColumnUpdateSetSelector<T>> columnSelectorExpression) {
        return setColumns(true, columnSelectorExpression);
    }

    ClientEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<ColumnUpdateSetSelector<T>> columnSelectorExpression);

    default ClientEntityUpdatable<T> setIgnoreColumns(SQLExpression1<ColumnUpdateSetSelector<T>> columnSelectorExpression) {
        return setIgnoreColumns(true, columnSelectorExpression);
    }

    ClientEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<ColumnUpdateSetSelector<T>> columnSelectorExpression);

    default ClientEntityUpdatable<T> whereColumns(SQLExpression1<ColumnUpdateSetSelector<T>> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    ClientEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<ColumnUpdateSetSelector<T>> columnSelectorExpression);

    String toSQL(Object entity);
}
