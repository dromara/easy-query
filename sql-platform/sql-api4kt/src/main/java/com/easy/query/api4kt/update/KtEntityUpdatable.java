package com.easy.query.api4kt.update;

import com.easy.query.api4kt.sql.SQLKtColumnConfigurer;
import com.easy.query.api4kt.sql.SQLKtColumnSetSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnConfigurerImpl;
import com.easy.query.api4kt.sql.impl.SQLKtColumnSetSelectorImpl;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
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
public interface KtEntityUpdatable<T> extends Updatable<T, KtEntityUpdatable<T>>, SQLExecuteStrategy<KtEntityUpdatable<T>>, ConfigureVersionable<KtEntityUpdatable<T>> {
    ClientEntityUpdatable<T> getClientUpdate();
    default KtEntityUpdatable<T> columnConfigure(SQLExpression1<SQLKtColumnConfigurer<T>> columnConfigureExpression){
        getClientUpdate().columnConfigure(configurer->{
            columnConfigureExpression.apply(new SQLKtColumnConfigurerImpl<>(configurer));
        });
        return this;
    }

    default KtEntityUpdatable<T> setColumns(SQLExpression1<SQLKtColumnSetSelector<T>> columnSelectorExpression) {
        getClientUpdate().setColumns(selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSetSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<SQLKtColumnSetSelector<T>> columnSelectorExpression) {
        getClientUpdate().setColumns(condition, selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSetSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> setIgnoreColumns(SQLExpression1<SQLKtColumnSetSelector<T>> columnSelectorExpression) {
        getClientUpdate().setIgnoreColumns(selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSetSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<SQLKtColumnSetSelector<T>> columnSelectorExpression) {

        getClientUpdate().setIgnoreColumns(condition, selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSetSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> whereColumns(SQLExpression1<SQLKtColumnSetSelector<T>> columnSelectorExpression) {
        getClientUpdate().whereColumns(selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSetSelectorImpl<>(selector));
        });
        return this;
    }

    default KtEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<SQLKtColumnSetSelector<T>> columnSelectorExpression) {
        getClientUpdate().whereColumns(condition, selector -> {
            columnSelectorExpression.apply(new SQLKtColumnSetSelectorImpl<>(selector));
        });
        return this;
    }

    default String toSQL(Object entity) {
        return getClientUpdate().toSQL(entity);
    }
}
