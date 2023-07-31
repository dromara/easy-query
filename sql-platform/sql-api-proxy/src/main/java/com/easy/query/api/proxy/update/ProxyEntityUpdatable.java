package com.easy.query.api.proxy.update;

import com.easy.query.api.proxy.sql.ProxyUpdateSetSelector;
import com.easy.query.api.proxy.sql.impl.ProxyUpdateSetSelectorImpl;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * @author xuejiaming
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 */
public interface ProxyEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends ProxyUpdatable<TProxy, T, ProxyEntityUpdatable<TProxy, T>>, SQLExecuteStrategy<ProxyEntityUpdatable<TProxy, T>>, ConfigureVersionable<ProxyEntityUpdatable<TProxy, T>> {
    ClientEntityUpdatable<T> getClientUpdate();

    default ProxyEntityUpdatable<TProxy, T> setColumns(SQLExpression2<ProxyUpdateSetSelector, TProxy> columnSelectorExpression) {
        setColumns(true, columnSelectorExpression);
        return this;
    }

    default ProxyEntityUpdatable<TProxy, T> setColumns(boolean condition, SQLExpression2<ProxyUpdateSetSelector, TProxy> columnSelectorExpression) {
        if (condition) {
            getClientUpdate().setColumns(selector -> {
                columnSelectorExpression.apply(new ProxyUpdateSetSelectorImpl(selector.getUpdateSetSelector()), getProxy());
            });
        }
        return this;
    }

    default ProxyEntityUpdatable<TProxy, T> setIgnoreColumns(SQLExpression2<ProxyUpdateSetSelector, TProxy> columnSelectorExpression) {
        return setIgnoreColumns(true, columnSelectorExpression);
    }

    default ProxyEntityUpdatable<TProxy, T> setIgnoreColumns(boolean condition, SQLExpression2<ProxyUpdateSetSelector, TProxy> columnSelectorExpression) {
        if (condition) {
            getClientUpdate().setIgnoreColumns(selector -> {
                columnSelectorExpression.apply(new ProxyUpdateSetSelectorImpl(selector.getUpdateSetSelector()), getProxy());
            });
        }
        return this;
    }

    default ProxyEntityUpdatable<TProxy, T> whereColumns(SQLExpression2<ProxyUpdateSetSelector, TProxy> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    default ProxyEntityUpdatable<TProxy, T> whereColumns(boolean condition, SQLExpression2<ProxyUpdateSetSelector, TProxy> columnSelectorExpression) {
        if (condition) {
            getClientUpdate().whereColumns(selector -> {
                columnSelectorExpression.apply(new ProxyUpdateSetSelectorImpl(selector.getUpdateSetSelector()), getProxy());
            });
        }
        return this;
    }

    default String toSQL(Object entity) {
        return getClientUpdate().toSQL(entity);
    }
}
