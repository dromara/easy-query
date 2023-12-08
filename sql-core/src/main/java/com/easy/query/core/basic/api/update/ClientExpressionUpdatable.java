package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 */
public interface ClientExpressionUpdatable<T> extends Updatable<T, ClientExpressionUpdatable<T>>,
        WithVersionable<ClientExpressionUpdatable<T>>,
        ConfigureVersionable<ClientExpressionUpdatable<T>>
{
    ColumnSetter<T> getColumnSetter();
    default ClientExpressionUpdatable<T> set(String property, Object val) {
        return set(true, property, val);
    }

    ClientExpressionUpdatable<T> set(boolean condition, String property, Object val);

    default ClientExpressionUpdatable<T> setWithColumn(String property1, String property2) {
        return setWithColumn(true, property1, property2);
    }

    ClientExpressionUpdatable<T> setWithColumn(boolean condition, String property1, String property2);
    // region 列++ --


    default ClientExpressionUpdatable<T> setIncrement(String property) {
        return setIncrement(true, property);
    }

    default ClientExpressionUpdatable<T> setIncrement(boolean condition, String property) {
        return setIncrementNumber(condition, property, 1);
    }

    default ClientExpressionUpdatable<T> setIncrement(String property, int val) {
        return setIncrementNumber(true, property, val);
    }

    default ClientExpressionUpdatable<T> setIncrement(boolean condition, String property, int val) {
        return setIncrementNumber(condition, property, val);
    }

    default ClientExpressionUpdatable<T> setIncrement(String property, long val) {
        return setIncrementNumber(true, property, val);
    }

    default ClientExpressionUpdatable<T> setIncrement(boolean condition, String property, long val) {
        return setIncrementNumber(condition, property, val);
    }


    default ClientExpressionUpdatable<T> setIncrement(String property, Number val) {
        return setIncrementNumber(true, property, val);
    }

    ClientExpressionUpdatable<T> setIncrementNumber(boolean condition, String property, Number val);

    default ClientExpressionUpdatable<T> setDecrement(String property) {
        return setDecrement(true, property);
    }

    default ClientExpressionUpdatable<T> setDecrement(boolean condition, String property) {
        return setDecrementNumber(condition, property, 1);
    }

    default ClientExpressionUpdatable<T> setDecrement(String property, int val) {
        return setDecrementNumber(true, property, val);
    }

    default ClientExpressionUpdatable<T> setDecrement(boolean condition, String property, int val) {
        return setDecrementNumber(condition, property, val);
    }

    default ClientExpressionUpdatable<T> setDecrement(String property, long val) {
        return setDecrementNumber(true, property, val);
    }

    default ClientExpressionUpdatable<T> setDecrement(boolean condition, String property, long val) {
        return setDecrementNumber(condition, property, val);
    }


    default ClientExpressionUpdatable<T> setDecrement(String property, Number val) {
        return setDecrementNumber(true, property, val);
    }

    ClientExpressionUpdatable<T> setDecrementNumber(boolean condition, String property, Number val);


    default ClientExpressionUpdatable<T> setSQLSegment(String property, String sqlSegment, SQLExpression1<SQLNativePropertyExpressionContext> contextConsume){
        return setSQLSegment(true,property,sqlSegment,contextConsume);
    }
    ClientExpressionUpdatable<T> setSQLSegment(boolean condition, String property, String sqlSegment, SQLExpression1<SQLNativePropertyExpressionContext> contextConsume);
    // endregion

    default ClientExpressionUpdatable<T> where(SQLExpression1<WherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientExpressionUpdatable<T> where(boolean condition, SQLExpression1<WherePredicate<T>> whereExpression);

    default ClientExpressionUpdatable<T> whereById(Object id) {
        return whereById(true, id);
    }

    ClientExpressionUpdatable<T> whereById(boolean condition, Object id);

    default <TProperty> ClientExpressionUpdatable<T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> ClientExpressionUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids);
    ExpressionContext getExpressionContext();

    default String toSQL() {
        TableContext tableContext = getExpressionContext().getTableContext();
        return toSQL(DefaultToSQLContext.defaultToSQLContext(tableContext));
    }

    String toSQL(ToSQLContext toSQLContext);
}

