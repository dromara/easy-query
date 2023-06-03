package com.easy.query.api4kt.update;

import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * @author xuejiaming
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 */
public interface KtEntityUpdatable<T> extends Updatable<T, KtEntityUpdatable<T>>, SQLExecuteStrategy<KtEntityUpdatable<T>> {
    default KtEntityUpdatable<T> setColumns(SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        return setColumns(true, columnSelectorExpression);
    }

    KtEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression);

    default KtEntityUpdatable<T> setIgnoreColumns(SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        return setIgnoreColumns(true, columnSelectorExpression);
    }

    KtEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression);

    default KtEntityUpdatable<T> whereColumns(SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    KtEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression);

    String toSQL(Object entity);
}
