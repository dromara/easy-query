package com.easy.query.api.proxy.insert;

import com.easy.query.api.proxy.sql.expression.impl.MultiColumnOnlySelectorImpl;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/9/17 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyEntityInsertable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements ProxyEntityInsertable<T1Proxy, T1> {
    private final T1Proxy tProxy;
    private final ClientInsertable<T1> clientInsertable;

    public EasyProxyEntityInsertable(T1Proxy tProxy, ClientInsertable<T1> clientInsertable) {

        this.tProxy = tProxy;
        this.clientInsertable = clientInsertable;
    }

    @Override
    public ProxyEntityInsertable<T1Proxy, T1> onConflictDoUpdate() {
        clientInsertable.onConflictDoUpdate();
        return this;
    }

    @Override
    public ProxyEntityInsertable<T1Proxy, T1> onConflictDoUpdate(SQLColumn<T1Proxy, ?> constraintProperty) {
        clientInsertable.onConflictDoUpdate(constraintProperty.value());
        return this;
    }

    @Override
    public ProxyEntityInsertable<T1Proxy, T1> onConflictDoUpdate(SQLColumn<T1Proxy, ?> constraintProperty, SQLExpression1<MultiColumnOnlySelectorImpl<T1Proxy, T1>> columnOnlySelector) {
        clientInsertable.onConflictDoUpdate(constraintProperty.value(), selector -> {
            columnOnlySelector.apply(new MultiColumnOnlySelectorImpl<>(tProxy, selector.getOnlySelector()));
        });
        return this;
    }

    @Override
    public ProxyEntityInsertable<T1Proxy, T1> onConflictDoUpdate(SQLExpression1<MultiColumnOnlySelectorImpl<T1Proxy, T1>> columnOnlySelector) {
        clientInsertable.onConflictDoUpdate(selector -> {
            columnOnlySelector.apply(new MultiColumnOnlySelectorImpl<>(tProxy, selector.getOnlySelector()));
        });
        return this;
    }

    @Override
    public ProxyEntityInsertable<T1Proxy, T1> onDuplicateKeyUpdate() {
        clientInsertable.onDuplicateKeyUpdate();
        return this;
    }

    @Override
    public ProxyEntityInsertable<T1Proxy, T1> onDuplicateKeyUpdate(SQLExpression1<MultiColumnOnlySelectorImpl<T1Proxy, T1>> columnOnlySelector) {
        clientInsertable.onDuplicateKeyUpdate(selector -> {
            columnOnlySelector.apply(new MultiColumnOnlySelectorImpl<>(tProxy, selector.getOnlySelector()));
        });
        return this;
    }

    @Override
    public ClientInsertable<T1> getClientInsert() {
        return clientInsertable;
    }
}
