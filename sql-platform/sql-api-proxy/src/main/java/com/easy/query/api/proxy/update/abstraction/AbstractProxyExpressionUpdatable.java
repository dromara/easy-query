package com.easy.query.api.proxy.update.abstraction;

import com.easy.query.api.proxy.update.ProxyExpressionUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 */
public abstract class AbstractProxyExpressionUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> implements ProxyExpressionUpdatable<TProxy, T> {
    private final TProxy proxy;
    protected final ClientExpressionUpdatable<T> clientExpressionUpdatable;

    public AbstractProxyExpressionUpdatable(TProxy proxy, ClientExpressionUpdatable<T> expressionObjectUpdatable) {
        this.proxy = proxy;
        this.clientExpressionUpdatable = expressionObjectUpdatable;
    }

    @Override
    public TProxy getProxy() {
        return proxy;
    }

    @Override
    public ClientExpressionUpdatable<T> getClientUpdate() {
        return clientExpressionUpdatable;
    }

    @Override
    public long executeRows() {
        return clientExpressionUpdatable.executeRows();
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            clientExpressionUpdatable.withVersion(versionValue);
        }
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        clientExpressionUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        clientExpressionUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> asAlias(String alias) {
        clientExpressionUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> noInterceptor() {
        clientExpressionUpdatable.noInterceptor();
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> useInterceptor(String name) {
        clientExpressionUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> noInterceptor(String name) {
        clientExpressionUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> useInterceptor() {
        clientExpressionUpdatable.useInterceptor();
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> useLogicDelete(boolean enable) {
        clientExpressionUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientExpressionUpdatable.executeRows(expectRows, msg, code);
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> batch(boolean use) {
        clientExpressionUpdatable.batch(use);
        return this;
    }
}
