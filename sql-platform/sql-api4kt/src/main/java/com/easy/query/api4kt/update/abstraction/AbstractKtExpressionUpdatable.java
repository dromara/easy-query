package com.easy.query.api4kt.update.abstraction;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.api4kt.update.KtExpressionUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 */
public abstract class AbstractKtExpressionUpdatable<T> implements KtExpressionUpdatable<T> {
    protected final ClientExpressionUpdatable<T> expressionObjectUpdatable;

    public AbstractKtExpressionUpdatable(ClientExpressionUpdatable<T> expressionObjectUpdatable) {
        this.expressionObjectUpdatable = expressionObjectUpdatable;
    }

    @Override
    public long executeRows() {
        return expressionObjectUpdatable.executeRows();
    }

    @Override
    public KtExpressionUpdatable<T> set(boolean condition, Property<T, ?> column, Object val) {
        expressionObjectUpdatable.set(true, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> setWithColumn(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        expressionObjectUpdatable.setWithColumn(true, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            expressionObjectUpdatable.withVersion(versionValue);
        }
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> setIncrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {
        expressionObjectUpdatable.setIncrementNumber(true, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> setDecrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {
        expressionObjectUpdatable.setDecrementNumber(true, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }


    @Override
    public KtExpressionUpdatable<T> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T>> whereExpression) {
        if (condition) {
            expressionObjectUpdatable.where(where -> {
                whereExpression.apply(new SQLKtWherePredicateImpl<>(where));
            });
        }
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> whereById(boolean condition, Object id) {

        if (condition) {
            expressionObjectUpdatable.whereById(id);
        }
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> whereByIds(boolean condition, Object... ids) {
        if (condition) {
            expressionObjectUpdatable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public <TProperty> KtExpressionUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            expressionObjectUpdatable.whereByIds(ids);
        }
        return this;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionObjectUpdatable.getExpressionContext();
    }

    @Override
    public KtExpressionUpdatable<T> asTable(Function<String, String> tableNameAs) {
        expressionObjectUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> asSchema(Function<String, String> schemaAs) {
        expressionObjectUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> asAlias(String alias) {
        expressionObjectUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return expressionObjectUpdatable.toSQL(toSQLContext);
    }

    @Override
    public KtExpressionUpdatable<T> noInterceptor() {
        expressionObjectUpdatable.noInterceptor();
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> useInterceptor(String name) {
        expressionObjectUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> noInterceptor(String name) {
        expressionObjectUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> useInterceptor() {
        expressionObjectUpdatable.useInterceptor();
        return this;
    }

    @Override
    public KtExpressionUpdatable<T> useLogicDelete(boolean enable) {
        expressionObjectUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        expressionObjectUpdatable.executeRows(expectRows, msg, code);
    }
}
