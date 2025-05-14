package com.easy.query.api.proxy.entity.update.abstraction;

import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/12/7 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class AbstractEntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntityUpdatable<TProxy, T> {
    private final TProxy tProxy;
    private final ClientEntityUpdatable<T> clientEntityUpdatable;

    public AbstractEntityUpdatable(TProxy tProxy, ClientEntityUpdatable<T> clientEntityUpdatable) {

        this.clientEntityUpdatable = clientEntityUpdatable;
        this.tProxy = tProxy.create(clientEntityUpdatable.getUpdateExpressionBuilder().getTable(0).getEntityTable(),clientEntityUpdatable.getUpdateExpressionBuilder(), getUpdateExpressionBuilder().getRuntimeContext());
    }

    @Override
    public EntityUpdatable<TProxy, T> ignoreVersion(boolean ignored) {
        clientEntityUpdatable.ignoreVersion(ignored);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> noInterceptor() {
        clientEntityUpdatable.noInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> useInterceptor(String name) {
        clientEntityUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> noInterceptor(String name) {
        clientEntityUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> useInterceptor() {
        clientEntityUpdatable.useInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> useLogicDelete(boolean enable) {
        clientEntityUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> batch(boolean use) {
        clientEntityUpdatable.batch(use);
        return this;
    }
    @Override
    public EntityUpdatable<TProxy, T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        clientEntityUpdatable.configure(configurer);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientEntityUpdatable.executeRows(expectRows, msg, code);
    }

    @Override
    public long executeRows() {
       return clientEntityUpdatable.executeRows();
    }

    @Override
    public EntityUpdatable<TProxy, T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        clientEntityUpdatable.setSQLStrategy(condition,sqlStrategy);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        clientEntityUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        clientEntityUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asAlias(String alias) {
        clientEntityUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        clientEntityUpdatable.asTableLink(linkAs);
        return this;
    }

    @Override
    public EntityUpdatable<TProxy, T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        clientEntityUpdatable.asTableSegment(segmentAs);
        return this;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return clientEntityUpdatable.getUpdateExpressionBuilder();
    }

    @Override
    public TProxy getTProxy() {
        return tProxy;
    }

    @Override
    public List<T> getEntities() {
        return clientEntityUpdatable.getEntities();
    }

    @Override
    public ClientEntityUpdatable<T> getClientUpdate() {
        return clientEntityUpdatable;
    }
}
