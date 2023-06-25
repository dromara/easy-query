package com.easy.query.api.proxy.update.abstraction;

import com.easy.query.api.proxy.update.ProxyEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractProxyEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> implements ProxyEntityUpdatable<TProxy,T> {

    private final TProxy proxy;
    protected final ClientEntityUpdatable<T> clientEntityUpdatable;

    public AbstractProxyEntityUpdatable(TProxy proxy, ClientEntityUpdatable<T> clientEntityUpdatable) {
        this.proxy = proxy;
        this.clientEntityUpdatable = clientEntityUpdatable;
    }

    @Override
    public TProxy getProxy() {
        return proxy;
    }

    @Override
    public ClientEntityUpdatable<T> getClientUpdate() {
        return clientEntityUpdatable;
    }

    @Override
    public long executeRows() {
        return clientEntityUpdatable.executeRows();
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> asTable(Function<String, String> tableNameAs) {
        clientEntityUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> asSchema(Function<String, String> schemaAs) {
        clientEntityUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> asAlias(String alias) {
        clientEntityUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            clientEntityUpdatable.setSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> noInterceptor() {
        clientEntityUpdatable.noInterceptor();
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> useInterceptor(String name) {
        clientEntityUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> noInterceptor(String name) {
        clientEntityUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> useInterceptor() {
        clientEntityUpdatable.useInterceptor();
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy,T> useLogicDelete(boolean enable) {
        clientEntityUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientEntityUpdatable.executeRows(expectRows, msg, code);
    }
}
