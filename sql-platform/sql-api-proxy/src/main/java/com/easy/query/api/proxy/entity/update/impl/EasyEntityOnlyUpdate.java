package com.easy.query.api.proxy.entity.update.impl;

import com.easy.query.api.proxy.entity.update.EntityOnlyUpdatable;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.function.Function;

/**
 * create time 2024/5/19 09:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityOnlyUpdate<T> implements EntityOnlyUpdatable<T> {
    private final ClientEntityUpdatable<T> clientEntityUpdatable;

    public EasyEntityOnlyUpdate(ClientEntityUpdatable<T> clientEntityUpdatable){
        this.clientEntityUpdatable = clientEntityUpdatable;
    }
    @Override
    public ClientEntityUpdatable<T> getClientUpdate() {
        return clientEntityUpdatable;
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>> EntityUpdatable<TProxy, T> useProxy(TProxy proxy) {
        return new EasyEntityUpdatable<>(proxy,clientEntityUpdatable);
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return clientEntityUpdatable.getUpdateExpressionBuilder();
    }

    @Override
    public EntityOnlyUpdatable<T> noInterceptor() {
        clientEntityUpdatable.noInterceptor();
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> useInterceptor(String name) {
        clientEntityUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> noInterceptor(String name) {
        clientEntityUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> useInterceptor() {
        clientEntityUpdatable.useInterceptor();
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> useLogicDelete(boolean enable) {
        clientEntityUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> batch(boolean use) {
        clientEntityUpdatable.batch(use);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientEntityUpdatable.executeRows(expectRows,msg,code);
    }

    @Override
    public long executeRows() {
        return clientEntityUpdatable.executeRows();
    }

    @Override
    public EntityOnlyUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        clientEntityUpdatable.setSQLStrategy(sqlStrategy);
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> asTable(Function<String, String> tableNameAs) {
        clientEntityUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> asSchema(Function<String, String> schemaAs) {
        clientEntityUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> asAlias(String alias) {
        clientEntityUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public EntityOnlyUpdatable<T> asTableLink(Function<String, String> linkAs) {
        clientEntityUpdatable.asTableLink(linkAs);
        return this;
    }
}
