package com.easy.query.api4kt.update.abstraction;

import com.easy.query.api4kt.update.KtEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractKtEntityUpdatable<T> implements KtEntityUpdatable<T> {

    protected final ClientEntityUpdatable<T> entityObjectUpdatable;

    public AbstractKtEntityUpdatable(ClientEntityUpdatable<T> entityObjectUpdatable) {
        this.entityObjectUpdatable = entityObjectUpdatable;
    }

    @Override
    public long executeRows() {
        return entityObjectUpdatable.executeRows();
    }

    @Override
    public KtEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityObjectUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        entityObjectUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asAlias(String alias) {
        entityObjectUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityObjectUpdatable.setSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public KtEntityUpdatable<T> noInterceptor() {
        entityObjectUpdatable.noInterceptor();
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useInterceptor(String name) {
        entityObjectUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> noInterceptor(String name) {
        entityObjectUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useInterceptor() {
        entityObjectUpdatable.useInterceptor();
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useLogicDelete(boolean enable) {
        entityObjectUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        entityObjectUpdatable.executeRows(expectRows, msg, code);
    }
}
