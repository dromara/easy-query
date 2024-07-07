package com.easy.query.api.proxy.entity.delete.abstraction;

import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 */
public abstract class AbstractExpressionDeletable<TProxy extends ProxyEntity<TProxy, T>, T> implements ExpressionDeletable<TProxy, T> {
    private final TProxy tProxy;
    private final ClientExpressionDeletable<T> expressionObjectDeletable;

    public AbstractExpressionDeletable(TProxy tProxy, ClientExpressionDeletable<T> expressionObjectDeletable) {
        this.expressionObjectDeletable = expressionObjectDeletable;
        this.tProxy = tProxy.create(expressionObjectDeletable.getDeleteExpressionBuilder().getTable(0).getEntityTable(), expressionObjectDeletable.getDeleteExpressionBuilder(), getExpressionContext().getRuntimeContext());
    }

    @Override
    public EntityDeleteExpressionBuilder getDeleteExpressionBuilder() {
        return expressionObjectDeletable.getDeleteExpressionBuilder();
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
    public ExpressionDeletable<TProxy, T> where(boolean condition, SQLExpression1<TProxy> whereExpression) {
        if (condition) {
            expressionObjectDeletable.where(where -> {
                tProxy.getEntitySQLContext()._where(where.getFilter(), () -> {
                    whereExpression.apply(tProxy);
                });
            });
        }
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            expressionObjectDeletable.withVersion(versionValue);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> whereById(boolean condition, Object id) {

        if (condition) {
            expressionObjectDeletable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> ExpressionDeletable<TProxy, T> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            expressionObjectDeletable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> useLogicDelete(boolean enable) {
        expressionObjectDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> allowDeleteStatement(boolean allow) {
        expressionObjectDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        expressionObjectDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        expressionObjectDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> asAlias(String alias) {
        expressionObjectDeletable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return expressionObjectDeletable.toSQL(toSQLContext);
    }

    @Override
    public ExpressionDeletable<TProxy, T> noInterceptor() {
        expressionObjectDeletable.noInterceptor();
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> useInterceptor(String name) {
        expressionObjectDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> noInterceptor(String name) {
        expressionObjectDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> useInterceptor() {
        expressionObjectDeletable.useInterceptor();
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> ignoreVersion(boolean ignored) {
        expressionObjectDeletable.ignoreVersion(ignored);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        expressionObjectDeletable.executeRows(expectRows, msg, code);
    }

    @Override
    public ExpressionDeletable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        expressionObjectDeletable.asTableLink(linkAs);
        return this;
    }

    @Override
    public ExpressionDeletable<TProxy, T> configure(SQLExpression1<ContextConfigurer> configurer) {
        expressionObjectDeletable.configure(configurer);
        return this;
    }
}
