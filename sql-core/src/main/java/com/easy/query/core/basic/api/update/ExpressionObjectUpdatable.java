package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 */
public interface ExpressionObjectUpdatable<T> extends Updatable<T, ExpressionObjectUpdatable<T>>, Versionable<ExpressionObjectUpdatable<T>> {
    default ExpressionObjectUpdatable<T> set(String property, Object val) {
        return set(true, property, val);
    }

    ExpressionObjectUpdatable<T> set(boolean condition, String property, Object val);

    default ExpressionObjectUpdatable<T> setSelfColumn(String property1, String property2) {
        return setSelfColumn(true, property1, property2);
    }

    ExpressionObjectUpdatable<T> setSelfColumn(boolean condition, String property1, String property2);
    // region 列++ --


    default ExpressionObjectUpdatable<T> setIncrement(String property) {
        return setIncrement(true, property);
    }

    default ExpressionObjectUpdatable<T> setIncrement(boolean condition, String property) {
        return setIncrementNumber(condition, property, 1);
    }

    default ExpressionObjectUpdatable<T> setIncrement(String property, int val) {
        return setIncrementNumber(true, property, val);
    }

    default ExpressionObjectUpdatable<T> setIncrement(boolean condition, String property, int val) {
        return setIncrementNumber(condition, property, val);
    }

    default ExpressionObjectUpdatable<T> setIncrement(String property, long val) {
        return setIncrementNumber(true, property, val);
    }

    default ExpressionObjectUpdatable<T> setIncrement(boolean condition, String property, long val) {
        return setIncrementNumber(condition, property, val);
    }


    default ExpressionObjectUpdatable<T> setIncrement(String property, Number val) {
        return setIncrementNumber(true, property, val);
    }

    ExpressionObjectUpdatable<T> setIncrementNumber(boolean condition, String property, Number val);

    default ExpressionObjectUpdatable<T> setDecrement(String property) {
        return setDecrement(true, property);
    }

    default ExpressionObjectUpdatable<T> setDecrement(boolean condition, String property) {
        return setDecrementNumber(condition, property, 1);
    }

    default ExpressionObjectUpdatable<T> setDecrement(String property, int val) {
        return setDecrementNumber(true, property, val);
    }

    default ExpressionObjectUpdatable<T> setDecrement(boolean condition, String property, int val) {
        return setDecrementNumber(condition, property, val);
    }

    default ExpressionObjectUpdatable<T> setDecrement(String property, long val) {
        return setDecrementNumber(true, property, val);
    }

    default ExpressionObjectUpdatable<T> setDecrement(boolean condition, String property, long val) {
        return setDecrementNumber(condition, property, val);
    }


    default ExpressionObjectUpdatable<T> setDecrement(String property, Number val) {
        return setDecrementNumber(true, property, val);
    }

    ExpressionObjectUpdatable<T> setDecrementNumber(boolean condition, String property, Number val);
    // endregion

    default ExpressionObjectUpdatable<T> where(SQLExpression1<WherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    ExpressionObjectUpdatable<T> where(boolean condition, SQLExpression1<WherePredicate<T>> whereExpression);

    default ExpressionObjectUpdatable<T> whereById(Object id) {
        return whereById(true, id);
    }

    ExpressionObjectUpdatable<T> whereById(boolean condition, Object id);


    default ExpressionObjectUpdatable<T> whereByIds(Object... ids) {
        return whereByIds(true, ids);
    }

    ExpressionObjectUpdatable<T> whereByIds(boolean condition, Object... ids);

    default <TProperty> ExpressionObjectUpdatable<T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> ExpressionObjectUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids);

    ExpressionContext getExpressionContext();

    default String toSQL() {
        TableContext tableContext = getExpressionContext().getTableContext();
        return toSQL(DefaultToSQLContext.defaultToSQLContext(tableContext));
    }

    String toSQL(ToSQLContext toSQLContext);
}

