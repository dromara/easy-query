package com.easy.query.api.proxy.delete.abstraction;

import com.easy.query.api.proxy.delete.ProxyExpressionDeletable;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 */
public abstract class AbstractProxyExpressionDeletable<TProxy extends ProxyEntity<TProxy, T>, T> implements ProxyExpressionDeletable<TProxy, T> {
    private final TProxy proxy;
    private final ClientExpressionDeletable<T> expressionObjectDeletable;

    public AbstractProxyExpressionDeletable(TProxy proxy, ClientExpressionDeletable<T> expressionObjectDeletable) {
        this.proxy = proxy;
        this.expressionObjectDeletable = expressionObjectDeletable;
    }

    @Override
    public TProxy getProxy() {
        return proxy;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionObjectDeletable.getExpressionContext();
    }

    @Override
    public long executeRows() {
        return expressionObjectDeletable.executeRows();
    }

//    @Override
//    public void executeRows(long expectRows, String msg, String code) {
//        long rows = executeRows();
//        if(rows!=expectRows){
//            throw new EasyQueryConcurrentException(msg,code);
//        }
//    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> where(boolean condition, SQLExpression2<ProxyFilter,TProxy> whereExpression) {
        if (condition) {
            expressionObjectDeletable.where(where -> {
                whereExpression.apply(new ProxyFilterImpl(where.getFilter()),getProxy());
            });
        }
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            expressionObjectDeletable.withVersion(versionValue);
        }
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> whereById(boolean condition, Object id) {

        if (condition) {
            expressionObjectDeletable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> ProxyExpressionDeletable<TProxy, T> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            expressionObjectDeletable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> useLogicDelete(boolean enable) {
        expressionObjectDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> allowDeleteStatement(boolean allow) {
        expressionObjectDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        expressionObjectDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        expressionObjectDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> asAlias(String alias) {
        expressionObjectDeletable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return expressionObjectDeletable.toSQL(toSQLContext);
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> noInterceptor() {
        expressionObjectDeletable.noInterceptor();
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> useInterceptor(String name) {
        expressionObjectDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> noInterceptor(String name) {
        expressionObjectDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public ProxyExpressionDeletable<TProxy, T> useInterceptor() {
        expressionObjectDeletable.useInterceptor();
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        expressionObjectDeletable.executeRows(expectRows, msg, code);
    }
}
