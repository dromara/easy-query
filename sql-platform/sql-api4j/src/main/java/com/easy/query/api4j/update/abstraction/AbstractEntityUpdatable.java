package com.easy.query.api4j.update.abstraction;

import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractEntityUpdatable<T> implements EntityUpdatable<T> {

    protected final ClientEntityUpdatable<T> clientEntityUpdatable;

    public AbstractEntityUpdatable(ClientEntityUpdatable<T> entityObjectUpdatable) {
        this.clientEntityUpdatable = entityObjectUpdatable;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return clientEntityUpdatable.getUpdateExpressionBuilder();
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
    public EntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        clientEntityUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        clientEntityUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityUpdatable<T> asAlias(String alias) {
        clientEntityUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public EntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            clientEntityUpdatable.setSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> noInterceptor() {
        clientEntityUpdatable.noInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<T> useInterceptor(String name) {
        clientEntityUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<T> noInterceptor(String name) {
        clientEntityUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<T> useInterceptor() {
        clientEntityUpdatable.useInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<T> useLogicDelete(boolean enable) {
        clientEntityUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityUpdatable<T> noVersionError() {
        clientEntityUpdatable.noVersionError();
        return this;
    }

    @Override
    public EntityUpdatable<T> noVersionIgnore() {
        clientEntityUpdatable.noVersionIgnore();
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientEntityUpdatable.executeRows(expectRows, msg, code);
    }

    @Override
    public EntityUpdatable<T> batch(boolean use) {
        clientEntityUpdatable.batch(use);
        return this;
    }

    @Override
    public EntityUpdatable<T> asTableLink(Function<String, String> linkAs) {
        clientEntityUpdatable.asTableLink(linkAs);
        return this;
    }
}
