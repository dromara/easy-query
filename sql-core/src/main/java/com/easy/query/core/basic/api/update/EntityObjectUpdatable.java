package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;

/**
 * @author xuejiaming
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 */
public interface EntityObjectUpdatable<T> extends Updatable<T, EntityObjectUpdatable<T>>, SQLExecuteStrategy<EntityObjectUpdatable<T>> {
    default EntityObjectUpdatable<T> setColumns(SQLExpression1<ColumnSelector<T>> columnSelectorExpression) {
        return setColumns(true, columnSelectorExpression);
    }

    EntityObjectUpdatable<T> setColumns(boolean condition, SQLExpression1<ColumnSelector<T>> columnSelectorExpression);

    default EntityObjectUpdatable<T> setIgnoreColumns(SQLExpression1<ColumnSelector<T>> columnSelectorExpression) {
        return setIgnoreColumns(true, columnSelectorExpression);
    }

    EntityObjectUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<ColumnSelector<T>> columnSelectorExpression);

    default EntityObjectUpdatable<T> whereColumns(SQLExpression1<ColumnSelector<T>> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    EntityObjectUpdatable<T> whereColumns(boolean condition, SQLExpression1<ColumnSelector<T>> columnSelectorExpression);

    String toSQL(Object entity);
}
