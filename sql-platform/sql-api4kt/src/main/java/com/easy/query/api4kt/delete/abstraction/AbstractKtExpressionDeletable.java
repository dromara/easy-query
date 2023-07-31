package com.easy.query.api4kt.delete.abstraction;

import com.easy.query.api4kt.delete.KtExpressionDeletable;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 */
public abstract class AbstractKtExpressionDeletable<T> implements KtExpressionDeletable<T> {
    private final ClientExpressionDeletable<T> expressionObjectDeletable;

    public AbstractKtExpressionDeletable(ClientExpressionDeletable<T> expressionObjectDeletable) {
        this.expressionObjectDeletable = expressionObjectDeletable;
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
    public KtExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T>> whereExpression) {
        if (condition) {
            expressionObjectDeletable.where(where -> {
                whereExpression.apply(new SQLKtWherePredicateImpl<>(where));
            });
        }
        return this;
    }

    @Override
    public KtExpressionDeletable<T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            expressionObjectDeletable.withVersion(versionValue);
        }
        return this;
    }

    @Override
    public KtExpressionDeletable<T> whereById(boolean condition, Object id) {

        if (condition) {
            expressionObjectDeletable.whereById(id);
        }
        return this;
    }

    @Override
    public <TProperty> KtExpressionDeletable<T> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            expressionObjectDeletable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public KtExpressionDeletable<T> useLogicDelete(boolean enable) {
        expressionObjectDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtExpressionDeletable<T> allowDeleteStatement(boolean allow) {
        expressionObjectDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public KtExpressionDeletable<T> asTable(Function<String, String> tableNameAs) {
        expressionObjectDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtExpressionDeletable<T> asSchema(Function<String, String> schemaAs) {
        expressionObjectDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtExpressionDeletable<T> asAlias(String alias) {
        expressionObjectDeletable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return expressionObjectDeletable.toSQL(toSQLContext);
    }

    @Override
    public KtExpressionDeletable<T> noInterceptor() {
        expressionObjectDeletable.noInterceptor();
        return this;
    }

    @Override
    public KtExpressionDeletable<T> useInterceptor(String name) {
        expressionObjectDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public KtExpressionDeletable<T> noInterceptor(String name) {
        expressionObjectDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public KtExpressionDeletable<T> useInterceptor() {
        expressionObjectDeletable.useInterceptor();
        return this;
    }

    @Override
    public KtExpressionDeletable<T> noVersionError() {
        expressionObjectDeletable.noVersionError();
        return this;
    }

    @Override
    public KtExpressionDeletable<T> noVersionIgnore() {
        expressionObjectDeletable.noVersionIgnore();
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        expressionObjectDeletable.executeRows(expectRows, msg, code);
    }
}
