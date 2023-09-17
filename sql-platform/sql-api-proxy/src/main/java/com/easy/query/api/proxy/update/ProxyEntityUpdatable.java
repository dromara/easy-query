package com.easy.query.api.proxy.update;

import com.easy.query.api.proxy.sql.expression.MultiColumnOnlySelector;
import com.easy.query.api.proxy.sql.expression.impl.MultiColumnOnlySelectorImpl;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * @author xuejiaming
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 */
public interface ProxyEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends ProxyUpdatable<TProxy, T, ProxyEntityUpdatable<TProxy, T>>, SQLExecuteStrategy<ProxyEntityUpdatable<TProxy, T>>, ConfigureVersionable<ProxyEntityUpdatable<TProxy, T>> {
    ClientEntityUpdatable<T> getClientUpdate();

    default ProxyEntityUpdatable<TProxy, T> setColumns(SQLExpression1<MultiColumnOnlySelector<TProxy,T>> columnSelectorExpression) {
        setColumns(true, columnSelectorExpression);
        return this;
    }

    default ProxyEntityUpdatable<TProxy, T> setColumns(boolean condition, SQLExpression1<MultiColumnOnlySelector<TProxy,T>> columnSelectorExpression) {
        if (condition) {
            getClientUpdate().setColumns(selector -> {
                columnSelectorExpression.apply(new MultiColumnOnlySelectorImpl<>(getProxy(),selector.getOnlySelector()));
            });
        }
        return this;
    }

    default ProxyEntityUpdatable<TProxy, T> setIgnoreColumns(SQLExpression1<MultiColumnOnlySelector<TProxy,T>> columnSelectorExpression) {
        return setIgnoreColumns(true, columnSelectorExpression);
    }

    default ProxyEntityUpdatable<TProxy, T> setIgnoreColumns(boolean condition, SQLExpression1<MultiColumnOnlySelector<TProxy,T>> columnSelectorExpression) {
        if (condition) {
            getClientUpdate().setIgnoreColumns(selector -> {
                columnSelectorExpression.apply(new MultiColumnOnlySelectorImpl<>(getProxy(),selector.getOnlySelector()));
            });
        }
        return this;
    }

    default ProxyEntityUpdatable<TProxy, T> whereColumns(SQLExpression1<MultiColumnOnlySelector<TProxy,T>> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    default ProxyEntityUpdatable<TProxy, T> whereColumns(boolean condition, SQLExpression1<MultiColumnOnlySelector<TProxy,T>> columnSelectorExpression) {
        if (condition) {
            getClientUpdate().whereColumns(selector -> {
                columnSelectorExpression.apply(new MultiColumnOnlySelectorImpl<>(getProxy(),selector.getOnlySelector()));
            });
        }
        return this;
    }

    default String toSQL(Object entity) {
        return getClientUpdate().toSQL(entity);
    }
}
