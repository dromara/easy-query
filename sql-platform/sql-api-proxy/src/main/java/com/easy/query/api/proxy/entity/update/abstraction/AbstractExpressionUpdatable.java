package com.easy.query.api.proxy.entity.update.abstraction;

import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 */
public abstract class AbstractExpressionUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> implements ExpressionUpdatable<TProxy,T> {
    private final TProxy tProxy;
    protected final ClientExpressionUpdatable<T> clientExpressionUpdatable;

    public AbstractExpressionUpdatable(TProxy tProxy,ClientExpressionUpdatable<T> clientExpressionUpdatable) {
        this.clientExpressionUpdatable = clientExpressionUpdatable;
       this.tProxy = tProxy.create(clientExpressionUpdatable.getUpdateExpressionBuilder().getTable(0).getEntityTable(),clientExpressionUpdatable.getUpdateExpressionBuilder(), getExpressionContext().getRuntimeContext());
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return clientExpressionUpdatable.getUpdateExpressionBuilder();
    }

    @Override
    public TProxy getProxy() {
        return tProxy;
    }

    @Override
    public ClientExpressionUpdatable<T> getClientUpdate() {
        return clientExpressionUpdatable;
    }

    @Override
    public long executeRows() {
        return clientExpressionUpdatable.executeRows();
    }

    @Override
    public ExpressionUpdatable<TProxy,T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            clientExpressionUpdatable.withVersion(versionValue);
        }
        return this;
    }
    @Override
    public ExpressionUpdatable<TProxy,T> asTable(Function<String, String> tableNameAs) {
        clientExpressionUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> asSchema(Function<String, String> schemaAs) {
        clientExpressionUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> asAlias(String alias) {
        clientExpressionUpdatable.asAlias(alias);
        return this;
    }
    @Override
    public ExpressionUpdatable<TProxy,T> noInterceptor() {
        clientExpressionUpdatable.noInterceptor();
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> useInterceptor(String name) {
        clientExpressionUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> noInterceptor(String name) {
        clientExpressionUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> useInterceptor() {
        clientExpressionUpdatable.useInterceptor();
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> useLogicDelete(boolean enable) {
        clientExpressionUpdatable.useLogicDelete(enable);
        return this;
    }
    @Override
    public ExpressionUpdatable<TProxy,T> configure(SQLExpression1<ContextConfigurer> configurer) {
        clientExpressionUpdatable.configure(configurer);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientExpressionUpdatable.executeRows(expectRows, msg, code);
    }

    @Override
    public ExpressionUpdatable<TProxy,T> ignoreVersion(boolean ignored) {
        clientExpressionUpdatable.ignoreVersion(ignored);
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> batch(boolean use) {
        clientExpressionUpdatable.batch(use);
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy,T> asTableLink(Function<String, String> linkAs) {
        clientExpressionUpdatable.asTableLink(linkAs);
        return this;
    }

    @Override
    public ExpressionUpdatable<TProxy, T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        clientExpressionUpdatable.asTableSegment(segmentAs);
        return this;
    }
}
