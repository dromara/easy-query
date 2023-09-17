package com.easy.query.api.proxy.update.abstraction;

import com.easy.query.api.proxy.update.ProxyOnlyEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

import java.util.function.Function;

/**
 * create time 2023/6/25 21:43
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyOnlyEntityUpdatable<T> implements ProxyOnlyEntityUpdatable<T> {

    private final ClientEntityUpdatable<T> clientEntityUpdatable;

    public AbstractProxyOnlyEntityUpdatable(ClientEntityUpdatable<T> clientEntityUpdatable){

        this.clientEntityUpdatable = clientEntityUpdatable;
    }

    @Override
    public EntityUpdateExpressionBuilder getEntityUpdateExpressionBuilder() {
        return clientEntityUpdatable.getEntityUpdateExpressionBuilder();
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
    public ProxyOnlyEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        clientEntityUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        clientEntityUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> asAlias(String alias) {
        clientEntityUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            clientEntityUpdatable.setSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> noInterceptor() {
        clientEntityUpdatable.noInterceptor();
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> useInterceptor(String name) {
        clientEntityUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> noInterceptor(String name) {
        clientEntityUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> useInterceptor() {
        clientEntityUpdatable.useInterceptor();
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> useLogicDelete(boolean enable) {
        clientEntityUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientEntityUpdatable.executeRows(expectRows, msg, code);
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> batch(boolean use) {
        clientEntityUpdatable.batch(use);
        return this;
    }

    @Override
    public ProxyOnlyEntityUpdatable<T> asTableLink(Function<String, String> linkAs) {
        clientEntityUpdatable.asTableLink(linkAs);
        return this;
    }
}
