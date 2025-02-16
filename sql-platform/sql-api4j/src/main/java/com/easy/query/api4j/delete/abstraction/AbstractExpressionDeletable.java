package com.easy.query.api4j.delete.abstraction;

import com.easy.query.api4j.delete.ExpressionDeletable;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 */
public abstract class AbstractExpressionDeletable<T> implements ExpressionDeletable<T> {
    private final ClientExpressionDeletable<T> expressionObjectDeletable;

    public AbstractExpressionDeletable(ClientExpressionDeletable<T> expressionObjectDeletable) {
        this.expressionObjectDeletable = expressionObjectDeletable;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionObjectDeletable.getExpressionContext();
    }

    @Override
    public EntityDeleteExpressionBuilder getDeleteExpressionBuilder() {
        return expressionObjectDeletable.getDeleteExpressionBuilder();
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
    public ExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLWherePredicate<T>> whereExpression) {
        if (condition) {
            expressionObjectDeletable.where(where -> {
                whereExpression.apply(new SQLWherePredicateImpl<>(where));
            });
        }
        return this;
    }

    @Override
    public ExpressionDeletable<T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            expressionObjectDeletable.withVersion(versionValue);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<T> whereById(boolean condition, Object id) {

        if (condition) {
            expressionObjectDeletable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> ExpressionDeletable<T> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            expressionObjectDeletable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<T> useLogicDelete(boolean enable) {
        expressionObjectDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public ExpressionDeletable<T> allowDeleteStatement(boolean allow) {
        expressionObjectDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asTable(Function<String, String> tableNameAs) {
        expressionObjectDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asSchema(Function<String, String> schemaAs) {
        expressionObjectDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asAlias(String alias) {
        expressionObjectDeletable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return expressionObjectDeletable.toSQL(toSQLContext);
    }

    @Override
    public ExpressionDeletable<T> noInterceptor() {
        expressionObjectDeletable.noInterceptor();
        return this;
    }

    @Override
    public ExpressionDeletable<T> useInterceptor(String name) {
        expressionObjectDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public ExpressionDeletable<T> noInterceptor(String name) {
        expressionObjectDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public ExpressionDeletable<T> useInterceptor() {
        expressionObjectDeletable.useInterceptor();
        return this;
    }
    @Override
    public ExpressionDeletable<T> ignoreVersion(boolean ignored) {
        expressionObjectDeletable.ignoreVersion(ignored);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        expressionObjectDeletable.executeRows(expectRows, msg, code);
    }

    @Override
    public ExpressionDeletable<T> asTableLink(Function<String, String> linkAs) {
        expressionObjectDeletable.asTableLink(linkAs);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        expressionObjectDeletable.asTableSegment(segmentAs);
        return this;
    }

    @Override
    public ExpressionDeletable<T> configure(SQLExpression1<ContextConfigurer> configurer) {
        expressionObjectDeletable.configure(configurer);
        return this;
    }
}
