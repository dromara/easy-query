package com.easy.query.api4j.update;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.api4j.util.EasyLambdaUtil;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 */
public interface ExpressionUpdatable<T> extends Updatable<T, ExpressionUpdatable<T>>, Versionable<ExpressionUpdatable<T>> {
    ClientExpressionUpdatable<T> getClientUpdate();

    default ExpressionUpdatable<T> set(Property<T, ?> column, Object val) {
        getClientUpdate().set(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> set(boolean condition, Property<T, ?> column, Object val) {
        getClientUpdate().set(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setWithColumn(Property<T, ?> column1, Property<T, ?> column2) {
        getClientUpdate().setWithColumn(EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return this;
    }

    default ExpressionUpdatable<T> setWithColumn(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        getClientUpdate().setWithColumn(condition, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return this;
    }
    // region 列++ --


    default ExpressionUpdatable<T> setIncrement(Property<T, Integer> column) {
        getClientUpdate().setIncrement(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ExpressionUpdatable<T> setIncrement(boolean condition, Property<T, Integer> column) {
        getClientUpdate().setIncrement(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ExpressionUpdatable<T> setIncrement(Property<T, Integer> column, int val) {
        getClientUpdate().setIncrement(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setIncrement(boolean condition, Property<T, Integer> column, int val) {
        getClientUpdate().setIncrement(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setIncrement(Property<T, Long> column, long val) {
        getClientUpdate().setIncrement(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setIncrement(boolean condition, Property<T, Long> column, long val) {
        getClientUpdate().setIncrement(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }


    default ExpressionUpdatable<T> setIncrement(Property<T, ? extends Number> column, Number val) {
        getClientUpdate().setIncrement(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setIncrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {
        getClientUpdate().setIncrementNumber(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setDecrement(Property<T, Integer> column) {
        getClientUpdate().setDecrement(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ExpressionUpdatable<T> setDecrement(boolean condition, Property<T, Integer> column) {
        getClientUpdate().setDecrement(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default ExpressionUpdatable<T> setDecrement(Property<T, Integer> column, int val) {
        getClientUpdate().setDecrement(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setDecrement(boolean condition, Property<T, Integer> column, int val) {
        getClientUpdate().setDecrement(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setDecrement(Property<T, Long> column, long val) {
        getClientUpdate().setDecrement(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setDecrement(boolean condition, Property<T, Long> column, long val) {
        getClientUpdate().setDecrement(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }


    default ExpressionUpdatable<T> setDecrement(Property<T, ? extends Number> column, Number val) {
        getClientUpdate().setDecrement(EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default ExpressionUpdatable<T> setDecrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {
        getClientUpdate().setDecrementNumber(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }
    // endregion

    default ExpressionUpdatable<T> where(SQLExpression1<SQLWherePredicate<T>> whereExpression) {
        getClientUpdate().where(where -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(where));
        });
        return this;
    }

    default ExpressionUpdatable<T> where(boolean condition, SQLExpression1<SQLWherePredicate<T>> whereExpression) {
        getClientUpdate().where(condition, where -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(where));
        });
        return this;
    }

    default ExpressionUpdatable<T> whereById(Object id) {
        getClientUpdate().whereById(id);
        return this;
    }

    default ExpressionUpdatable<T> whereById(boolean condition, Object id) {
        getClientUpdate().whereById(condition, id);
        return this;
    }


    default <TProperty> ExpressionUpdatable<T> whereByIds(Collection<TProperty> ids) {
        getClientUpdate().whereByIds(ids);
        return this;
    }

    default <TProperty> ExpressionUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids) {
        getClientUpdate().whereByIds(condition, ids);
        return this;
    }

    default ExpressionContext getExpressionContext() {
        return getClientUpdate().getExpressionContext();
    }

    default String toSQL() {
        return getClientUpdate().toSQL();
    }

    default String toSQL(ToSQLContext toSQLContext) {
        return getClientUpdate().toSQL(toSQLContext);
    }
}

