package com.easy.query.api4j.insert;

import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/6/2 16:15
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntityInsertable<T> implements EntityInsertable<T> {
    private final ClientInsertable<T> clientInsertable;

    public AbstractEntityInsertable(ClientInsertable<T> clientInsertable) {

        this.clientInsertable = clientInsertable;
    }

    @Override
    public EntityInsertable<T> insert(T entity) {
        clientInsertable.insert(entity);
        return this;
    }

    @Override
    public EntityInsertable<T> insert(Collection<T> entities) {
        clientInsertable.insert(entities);
        return this;
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return clientInsertable.executeRows(fillAutoIncrement);
    }

    @Override
    public EntityInsertable<T> noInterceptor() {
        clientInsertable.noInterceptor();
        return this;
    }

    @Override
    public EntityInsertable<T> useInterceptor(String name) {
        clientInsertable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityInsertable<T> noInterceptor(String name) {
        clientInsertable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityInsertable<T> useInterceptor() {
        clientInsertable.useInterceptor();
        return this;
    }

    @Override
    public EntityInsertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        clientInsertable.setSQLStrategy(condition, sqlStrategy);
        return this;
    }

    @Override
    public EntityInsertable<T> asTable(Function<String, String> tableNameAs) {
        clientInsertable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityInsertable<T> asSchema(Function<String, String> schemaAs) {
        clientInsertable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityInsertable<T> asAlias(String alias) {
        clientInsertable.asAlias(alias);
        return this;
    }

    @Override
    public EntityInsertable<T> onDuplicateKeyIgnore() {
        clientInsertable.onDuplicateKeyIgnore();
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return clientInsertable.toSQL(entity);
    }
}
