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
    protected final ClientExpressionUpdatable<T> expressionObjectUpdatable;

    public AbstractProxyExpressionUpdatable(TProxy proxy, ClientExpressionUpdatable<T> expressionObjectUpdatable) {
        this.proxy = proxy;
        this.expressionObjectUpdatable = expressionObjectUpdatable;
    }

    @Override
    public TProxy getProxy() {
        return proxy;
    }

    @Override
    public ClientExpressionUpdatable<T> getClientUpdate() {
        return expressionObjectUpdatable;
    }

    @Override
    public long executeRows() {
        return expressionObjectUpdatable.executeRows();
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            expressionObjectUpdatable.withVersion(versionValue);
        }
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        expressionObjectUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        expressionObjectUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> asAlias(String alias) {
        expressionObjectUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> noInterceptor() {
        expressionObjectUpdatable.noInterceptor();
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> useInterceptor(String name) {
        expressionObjectUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> noInterceptor(String name) {
        expressionObjectUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> useInterceptor() {
        expressionObjectUpdatable.useInterceptor();
        return this;
    }

    @Override
    public ProxyExpressionUpdatable<TProxy, T> useLogicDelete(boolean enable) {
        expressionObjectUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        expressionObjectUpdatable.executeRows(expectRows, msg, code);
    }
}
