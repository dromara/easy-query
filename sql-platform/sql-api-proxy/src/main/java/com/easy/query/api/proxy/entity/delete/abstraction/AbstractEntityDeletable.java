package com.easy.query.api.proxy.entity.delete.abstraction;

import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractEntityDelete.java
 * @Description: 文件说明
 * create time 2023/2/28 12:33
 */
public abstract class AbstractEntityDeletable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntityDeletable<TProxy,T> {
    private final ClientEntityDeletable<T> entityObjectDeletable;

    public AbstractEntityDeletable(ClientEntityDeletable<T> entityObjectDeletable) {
        this.entityObjectDeletable = entityObjectDeletable;
    }

    @Override
    public List<T> getEntities() {
        return entityObjectDeletable.getEntities();
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return entityObjectDeletable.getExpressionContext();
    }

    @Override
    public EntityDeleteExpressionBuilder getDeleteExpressionBuilder() {
        return entityObjectDeletable.getDeleteExpressionBuilder();
    }

    @Override
    public String toSQL() {
        return entityObjectDeletable.toSQL();
    }

    @Override
    public long executeRows() {
        return entityObjectDeletable.executeRows();
    }


    @Override
    public EntityDeletable<TProxy,T> useLogicDelete(boolean enable) {
        entityObjectDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> allowDeleteStatement(boolean allow) {
        entityObjectDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> asTable(Function<String, String> tableNameAs) {
        entityObjectDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> asSchema(Function<String, String> schemaAs) {
        entityObjectDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> asAlias(String alias) {
        entityObjectDeletable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return entityObjectDeletable.toSQL(toSQLContext);
    }

    @Override
    public EntityDeletable<TProxy,T> noInterceptor() {
        entityObjectDeletable.noInterceptor();
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> useInterceptor(String name) {
        entityObjectDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> noInterceptor(String name) {
        entityObjectDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> useInterceptor() {
        entityObjectDeletable.useInterceptor();
        return this;
    }

    @Override
    public EntityDeletable<TProxy,T> ignoreVersion(boolean ignored) {
        entityObjectDeletable.ignoreVersion(ignored);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        entityObjectDeletable.executeRows(expectRows, msg, code);
    }

    @Override
    public EntityDeletable<TProxy,T> asTableLink(Function<String, String> linkAs) {
        entityObjectDeletable.asTableLink(linkAs);
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityObjectDeletable.asTableSegment(segmentAs);
        return this;
    }

    @Override
    public EntityDeletable<TProxy, T> batch(boolean use) {
        entityObjectDeletable.batch(use);
        return this;
    }
    @Override
    public EntityDeletable<TProxy, T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        entityObjectDeletable.configure(configurer);
        return this;
    }
}
