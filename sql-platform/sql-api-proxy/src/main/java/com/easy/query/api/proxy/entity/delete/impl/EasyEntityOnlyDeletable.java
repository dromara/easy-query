package com.easy.query.api.proxy.entity.delete.impl;

import com.easy.query.api.proxy.entity.delete.EntityOnlyDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2024/5/19 09:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityOnlyDeletable<T> implements EntityOnlyDeletable<T> {
    private final ClientEntityDeletable<T> clientEntityDeletable;

    public EasyEntityOnlyDeletable(ClientEntityDeletable<T> clientEntityDeletable) {

        this.clientEntityDeletable = clientEntityDeletable;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return clientEntityDeletable.getExpressionContext();
    }

    @Override
    public EntityDeleteExpressionBuilder getDeleteExpressionBuilder() {
        return clientEntityDeletable.getDeleteExpressionBuilder();
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return clientEntityDeletable.toSQL(toSQLContext);
    }

    @Override
    public EntityOnlyDeletable<T> allowDeleteStatement(boolean allow) {
        clientEntityDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> ignoreVersion(boolean ignored) {
        clientEntityDeletable.ignoreVersion(ignored);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> noInterceptor() {
        clientEntityDeletable.noInterceptor();
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> useInterceptor(String name) {
        clientEntityDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> noInterceptor(String name) {
        clientEntityDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> useInterceptor() {
        clientEntityDeletable.useInterceptor();
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> useLogicDelete(boolean enable) {
        clientEntityDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> batch(boolean use) {
        clientEntityDeletable.batch(use);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientEntityDeletable.executeRows(expectRows, msg, code);
    }

    @Override
    public long executeRows() {
        return clientEntityDeletable.executeRows();
    }

    @Override
    public EntityOnlyDeletable<T> asTable(Function<String, String> tableNameAs) {
        clientEntityDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> asSchema(Function<String, String> schemaAs) {
        clientEntityDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> asAlias(String alias) {
        clientEntityDeletable.asAlias(alias);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> asTableLink(Function<String, String> linkAs) {
        clientEntityDeletable.asTableLink(linkAs);
        return this;
    }
    @Override
    public EntityOnlyDeletable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        clientEntityDeletable.asTableSegment(segmentAs);
        return this;
    }

    @Override
    public EntityOnlyDeletable<T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        clientEntityDeletable.configure(configurer);
        return this;
    }
}
