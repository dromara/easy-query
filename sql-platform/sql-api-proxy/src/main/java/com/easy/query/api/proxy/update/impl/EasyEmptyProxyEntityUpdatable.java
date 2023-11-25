package com.easy.query.api.proxy.update.impl;

import com.easy.query.api.proxy.sql.expression.MultiColumnOnlySelector;
import com.easy.query.api.proxy.update.ProxyEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.function.Function;

/**
 * create time 2023/6/25 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyProxyEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> implements ProxyEntityUpdatable<TProxy,T> {

    private final ClientEntityUpdatable<T> clientEntityUpdatable;

    public EasyEmptyProxyEntityUpdatable(ClientEntityUpdatable<T> clientEntityUpdatable){

        this.clientEntityUpdatable = clientEntityUpdatable;
    }
    @Override
    public ProxyEntityUpdatable<TProxy, T> ignoreVersion(boolean ignored) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> noInterceptor() {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> useInterceptor(String name) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> noInterceptor(String name) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> useInterceptor() {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> batch(boolean use) {
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {

    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> asAlias(String alias) {
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        return this;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return null;
    }

    @Override
    public ClientEntityUpdatable<T> getClientUpdate() {
        return clientEntityUpdatable;
    }

    @Override
    public TProxy getProxy() {
        return null;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> setColumns(boolean condition, SQLExpression1<MultiColumnOnlySelector<TProxy, T>> columnSelectorExpression) {
        if(getProxy()!=null){
            return ProxyEntityUpdatable.super.setColumns(condition, columnSelectorExpression);
        }
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> setIgnoreColumns(boolean condition, SQLExpression1<MultiColumnOnlySelector<TProxy, T>> columnSelectorExpression) {
        if(getProxy()!=null){
            return ProxyEntityUpdatable.super.setIgnoreColumns(condition, columnSelectorExpression);
        }
        return this;
    }

    @Override
    public ProxyEntityUpdatable<TProxy, T> whereColumns(boolean condition, SQLExpression1<MultiColumnOnlySelector<TProxy, T>> columnSelectorExpression) {
        if(getProxy()!=null){
            return ProxyEntityUpdatable.super.whereColumns(condition, columnSelectorExpression);
        }
        return this;
    }
}
