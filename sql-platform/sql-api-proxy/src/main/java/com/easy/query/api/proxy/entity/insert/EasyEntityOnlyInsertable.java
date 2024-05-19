package com.easy.query.api.proxy.entity.insert;

import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2024/5/19 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityOnlyInsertable<T> implements EntityOnlyInsertable<T> {
    private final ClientInsertable<T> clientInsertable;

    public EasyEntityOnlyInsertable(ClientInsertable<T> clientInsertable) {

        this.clientInsertable = clientInsertable;
    }
    @Override
    public EntityOnlyInsertable<T> insert(T entity) {
        clientInsertable.insert(entity);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> insert(Collection<T> entities) {
        clientInsertable.insert(entities);
        return this;
    }

    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return clientInsertable.getEntityInsertExpressionBuilder();
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return clientInsertable.executeRows(fillAutoIncrement);
    }

    @Override
    public String toSQL(T entity) {
        return clientInsertable.toSQL(entity);
    }

    @Override
    public String toSQL(T entity, ToSQLContext toSQLContext) {
        return clientInsertable.toSQL(entity,toSQLContext);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>> EntityInsertable<TProxy, T> useProxy(TProxy proxy) {
        return new EasyEntityInsertable<>(proxy,clientInsertable);
    }

    @Override
    public EntityOnlyInsertable<T> noInterceptor() {
        clientInsertable.noInterceptor();
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> useInterceptor(String name) {
        clientInsertable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> noInterceptor(String name) {
        clientInsertable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> useInterceptor() {
        clientInsertable.useInterceptor();
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> batch(boolean use) {
        clientInsertable.batch(use);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        clientInsertable.setSQLStrategy(sqlStrategy);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> onDuplicateKeyIgnore() {
        clientInsertable.onDuplicateKeyIgnore();
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> asTable(Function<String, String> tableNameAs) {
        clientInsertable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> asSchema(Function<String, String> schemaAs) {
        clientInsertable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> asAlias(String alias) {
        clientInsertable.asAlias(alias);
        return this;
    }

    @Override
    public EntityOnlyInsertable<T> asTableLink(Function<String, String> linkAs) {
        clientInsertable.asTableLink(linkAs);
        return this;
    }
}
