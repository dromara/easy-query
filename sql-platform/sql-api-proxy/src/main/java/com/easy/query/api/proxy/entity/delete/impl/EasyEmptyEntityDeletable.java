package com.easy.query.api.proxy.entity.delete.impl;

import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2025/9/8 09:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyEntityDeletable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntityDeletable<TProxy, T> {
    @Override
    public List<T> getEntities() {
        return Collections.emptyList();
    }

    @Override
    public TProxy getTProxy() {
        return null;
    }

    @Override
    public ClientEntityDeletable<T> getClientDelete() {
        return null;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return null;
    }

    @Override
    public EntityDeleteExpressionBuilder getDeleteExpressionBuilder() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public EntityDeletable<TProxy, T> allowDeleteStatement(boolean allow) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> ignoreVersion(boolean ignored) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> noInterceptor() {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> useInterceptor(String name) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> noInterceptor(String name) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> useInterceptor() {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> batch(boolean use) {
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
    public EntityDeletable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> asAlias(String alias) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        return this;
    }
}
