package com.easy.query.api4kt.update;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 */
public interface KtExpressionUpdatable<T> extends Updatable<T, KtExpressionUpdatable<T>>, Versionable<KtExpressionUpdatable<T>> {
    default KtExpressionUpdatable<T> set(Property<T, ?> column, Object val) {
        return set(true, column, val);
    }

    KtExpressionUpdatable<T> set(boolean condition, Property<T, ?> column, Object val);

    default KtExpressionUpdatable<T> setWithColumn(Property<T, ?> column1, Property<T, ?> column2) {
        return setWithColumn(true, column1, column2);
    }

    KtExpressionUpdatable<T> setWithColumn(boolean condition, Property<T, ?> column1, Property<T, ?> column2);
    // region 列++ --


    default KtExpressionUpdatable<T> setIncrement(Property<T, Integer> column) {
        return setIncrement(true, column);
    }

    default KtExpressionUpdatable<T> setIncrement(boolean condition, Property<T, Integer> column) {
        return setIncrementNumber(condition, column, 1);
    }

    default KtExpressionUpdatable<T> setIncrement(Property<T, Integer> column, int val) {
        return setIncrementNumber(true, column, val);
    }

    default KtExpressionUpdatable<T> setIncrement(boolean condition, Property<T, Integer> column, int val) {
        return setIncrementNumber(condition, column, val);
    }

    default KtExpressionUpdatable<T> setIncrement(Property<T, Long> column, long val) {
        return setIncrementNumber(true, column, val);
    }

    default KtExpressionUpdatable<T> setIncrement(boolean condition, Property<T, Long> column, long val) {
        return setIncrementNumber(condition, column, val);
    }


    default KtExpressionUpdatable<T> setIncrement(Property<T, ? extends Number> column, Number val) {
        return setIncrementNumber(true, column, val);
    }

    KtExpressionUpdatable<T> setIncrementNumber(boolean condition, Property<T, ? extends Number> column, Number val);

    default KtExpressionUpdatable<T> setDecrement(Property<T, Integer> column) {
        return setDecrement(true, column);
    }

    default KtExpressionUpdatable<T> setDecrement(boolean condition, Property<T, Integer> column) {
        return setDecrementNumber(condition, column, 1);
    }

    default KtExpressionUpdatable<T> setDecrement(Property<T, Integer> column, int val) {
        return setDecrementNumber(true, column, val);
    }

    default KtExpressionUpdatable<T> setDecrement(boolean condition, Property<T, Integer> column, int val) {
        return setDecrementNumber(condition, column, val);
    }

    default KtExpressionUpdatable<T> setDecrement(Property<T, Long> column, long val) {
        return setDecrementNumber(true, column, val);
    }

    default KtExpressionUpdatable<T> setDecrement(boolean condition, Property<T, Long> column, long val) {
        return setDecrementNumber(condition, column, val);
    }


    default KtExpressionUpdatable<T> setDecrement(Property<T, ? extends Number> column, Number val) {
        return setDecrementNumber(true, column, val);
    }

    KtExpressionUpdatable<T> setDecrementNumber(boolean condition, Property<T, ? extends Number> column, Number val);
    // endregion

    default KtExpressionUpdatable<T> where(SQLExpression1<SQLKtWherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    KtExpressionUpdatable<T> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T>> whereExpression);

    default KtExpressionUpdatable<T> whereById(Object id) {
        return whereById(true, id);
    }

    KtExpressionUpdatable<T> whereById(boolean condition, Object id);


    default KtExpressionUpdatable<T> whereByIds(Object... ids) {
        return whereByIds(true, ids);
    }

    KtExpressionUpdatable<T> whereByIds(boolean condition, Object... ids);

    default <TProperty> KtExpressionUpdatable<T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> KtExpressionUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids);

    ExpressionContext getExpressionContext();

    default String toSQL() {
        TableContext tableContext = getExpressionContext().getTableContext();
        return toSQL(DefaultToSQLContext.defaultToSQLContext(tableContext));
    }

    String toSQL(ToSQLContext toSQLContext);
}

