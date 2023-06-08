package com.easy.query.api4kt.update;

import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnSelectorImpl;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * @author xuejiaming
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 */
public interface KtEntityUpdatable<T> extends Updatable<T, KtEntityUpdatable<T>>, SQLExecuteStrategy<KtEntityUpdatable<T>> {
    ClientEntityUpdatable<T> getClientUpdate();

    default KtEntityUpdatable<T> setColumns(SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        getClientUpdate().setColumns(selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        getClientUpdate().setColumns(condition, selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> setIgnoreColumns(SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        getClientUpdate().setIgnoreColumns(selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {

        getClientUpdate().setIgnoreColumns(condition, selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> whereColumns(SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        getClientUpdate().whereColumns(selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        getClientUpdate().whereColumns(condition, selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSelectorImpl<>(selector));
        });
        return this;
    }

    default String toSQL(Object entity) {
        return getClientUpdate().toSQL(entity);
    }
}
