package com.easy.query.api4kt.update;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import kotlin.reflect.KProperty1;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 */
public interface KtExpressionUpdatable<T> extends Updatable<T, KtExpressionUpdatable<T>>, Versionable<KtExpressionUpdatable<T>> {
    ClientExpressionUpdatable<T> getClientUpdate();

    default KtExpressionUpdatable<T> set(KProperty1<T, ?> column, Object val) {
        getClientUpdate().set(EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> set(boolean condition, KProperty1<T, ?> column, Object val) {
        getClientUpdate().set(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setWithColumn(KProperty1<T, ?> column1, KProperty1<T, ?> column2) {
        getClientUpdate().setWithColumn(EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default KtExpressionUpdatable<T> setWithColumn(boolean condition, KProperty1<T, ?> column1, KProperty1<T, ?> column2) {
        getClientUpdate().setWithColumn(condition, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }
    // region 列++ --


    default KtExpressionUpdatable<T> setIncrement(KProperty1<T, Integer> column) {
        getClientUpdate().setIncrement(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default KtExpressionUpdatable<T> setIncrement(boolean condition, KProperty1<T, Integer> column) {
        getClientUpdate().setIncrement(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default KtExpressionUpdatable<T> setIncrement(KProperty1<T, Integer> column, int val) {
        getClientUpdate().setIncrement(EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setIncrement(boolean condition, KProperty1<T, Integer> column, int val) {
        getClientUpdate().setIncrement(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setIncrement(KProperty1<T, Long> column, long val) {
        getClientUpdate().setIncrement(EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setIncrement(boolean condition, KProperty1<T, Long> column, long val) {
        getClientUpdate().setIncrement(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }


    default KtExpressionUpdatable<T> setIncrement(KProperty1<T, ? extends Number> column, Number val) {
        getClientUpdate().setIncrement(EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setIncrementNumber(boolean condition, KProperty1<T, ? extends Number> column, Number val) {
        getClientUpdate().setDecrementNumber(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setDecrement(KProperty1<T, Integer> column) {
        getClientUpdate().setDecrement(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default KtExpressionUpdatable<T> setDecrement(boolean condition, KProperty1<T, Integer> column) {
        getClientUpdate().setDecrement(condition, EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default KtExpressionUpdatable<T> setDecrement(KProperty1<T, Integer> column, int val) {
        getClientUpdate().setDecrement(EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setDecrement(boolean condition, KProperty1<T, Integer> column, int val) {
        getClientUpdate().setDecrement(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setDecrement(KProperty1<T, Long> column, long val) {
        getClientUpdate().setDecrement(EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setDecrement(boolean condition, KProperty1<T, Long> column, long val) {
        getClientUpdate().setDecrement(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }


    default KtExpressionUpdatable<T> setDecrement(KProperty1<T, ? extends Number> column, Number val) {
        getClientUpdate().setDecrement(EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default KtExpressionUpdatable<T> setDecrementNumber(boolean condition, KProperty1<T, ? extends Number> column, Number val) {
        getClientUpdate().setDecrementNumber(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }
    // endregion

    default KtExpressionUpdatable<T> where(SQLExpression1<SQLKtWherePredicate<T>> whereExpression) {
        getClientUpdate().where(where -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(where));
        });
        return this;
    }

    default KtExpressionUpdatable<T> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T>> whereExpression) {

        getClientUpdate().where(condition, where -> {
            whereExpression.apply(new SQLKtWherePredicateImpl<>(where));
        });
        return this;
    }

    default KtExpressionUpdatable<T> whereById(Object id) {
        getClientUpdate().whereById(id);
        return this;
    }

    default KtExpressionUpdatable<T> whereById(boolean condition, Object id) {
        getClientUpdate().whereById(condition, id);
        return this;
    }

    default <TProperty> KtExpressionUpdatable<T> whereByIds(Collection<TProperty> ids) {
        getClientUpdate().whereByIds(ids);
        return this;
    }

    default <TProperty> KtExpressionUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids) {
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

