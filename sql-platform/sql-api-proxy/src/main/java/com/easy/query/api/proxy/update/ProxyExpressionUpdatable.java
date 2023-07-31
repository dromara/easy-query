package com.easy.query.api.proxy.update;

import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 */
public interface ProxyExpressionUpdatable<TProxy extends ProxyEntity<TProxy, T>, T>  extends ProxyUpdatable<TProxy,T, ProxyExpressionUpdatable<TProxy,T>>, WithVersionable<ProxyExpressionUpdatable<TProxy,T>>, ConfigureVersionable<ProxyExpressionUpdatable<TProxy,T>> {
    ClientExpressionUpdatable<T> getClientUpdate();

    default ProxyExpressionUpdatable<TProxy,T> set(SQLFuncExpression1<TProxy, SQLColumn<?>> columnFunction, Object val) {
        SQLColumn<?> sqlColumn = columnFunction.apply(getProxy());
        getClientUpdate().set(sqlColumn.value(), val);
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> set(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<?>> columnFunction, Object val) {
        if(condition){
            SQLColumn<?> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().set(sqlColumn.value(), val); 
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setWithColumn(SQLFuncExpression1<TProxy, SQLColumn<?>> columnFunction1, SQLFuncExpression1<TProxy, SQLColumn<?>> columnFunction2) {
        return setWithColumn(true,columnFunction1,columnFunction2);
    }

    default ProxyExpressionUpdatable<TProxy,T> setWithColumn(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<?>> columnFunction1, SQLFuncExpression1<TProxy, SQLColumn<?>> columnFunction2) {
        if(condition){
            SQLColumn<?> sqlColumn1 = columnFunction1.apply(getProxy());
            SQLColumn<?> sqlColumn2 = columnFunction2.apply(getProxy());
            getClientUpdate().setWithColumn(sqlColumn1.value(), sqlColumn2.value());
        }
        return this;
    }
    // region 列++ --


    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction) {
        return setIncrement(true,columnFunction);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction) {
        if(condition){
            SQLColumn<Integer> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setIncrement(sqlColumn.value());
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction, int val) {
        return setIncrement(true,columnFunction,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction, int val) {
        if(condition){
            SQLColumn<Integer> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setIncrement(sqlColumn.value(), val);
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<Long>> columnFunction, long val) {
        return setIncrement(true,columnFunction,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<Long>> columnFunction, long val) {
        if(condition){
            SQLColumn<Long> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setIncrement(sqlColumn.value(),val);
        }
        return this;
    }


    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLFuncExpression1<TProxy, SQLColumn<? extends Number>> columnFunction, Number val) {
        return setIncrementNumber(true,columnFunction,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrementNumber(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<? extends Number>> columnFunction, Number val) {
        if(condition){
            SQLColumn<? extends Number> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setIncrementNumber(true,sqlColumn.value(), val);
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction) {
        return setDecrement(true,columnFunction);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction) {
        if(condition){
            SQLColumn<Integer> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setDecrement(sqlColumn.value());
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction, int val) {
        return setDecrement(true,columnFunction,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<Integer>> columnFunction, int val) {
        if(condition){
            SQLColumn<Integer> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setDecrement(sqlColumn.value(), val);
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<Long>> columnFunction, long val) {
        return setDecrement(true,columnFunction,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<Long>> columnFunction, long val) {
        if(condition){
            SQLColumn<Long> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setDecrement(sqlColumn.value(), val);
        }
        return this;
    }


    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLFuncExpression1<TProxy, SQLColumn<? extends Number>> columnFunction, Number val) {
        return setDecrementNumber(true,columnFunction,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrementNumber(boolean condition, SQLFuncExpression1<TProxy, SQLColumn<? extends Number>> columnFunction, Number val) {
        if(condition){
            SQLColumn<? extends Number> sqlColumn = columnFunction.apply(getProxy());
            getClientUpdate().setDecrementNumber(true,sqlColumn.value(), val);
        }
        return this;
    }
    // endregion

    default ProxyExpressionUpdatable<TProxy,T> where(SQLExpression2<ProxyFilter,TProxy> whereExpression) {
        return where(true,whereExpression);
    }

    default ProxyExpressionUpdatable<TProxy,T> where(boolean condition, SQLExpression2<ProxyFilter,TProxy> whereExpression) {
        if(condition){

            getClientUpdate().where(where -> {
                whereExpression.apply(new ProxyFilterImpl(where.getFilter()),getProxy());
            });
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> whereById(Object id) {
        getClientUpdate().whereById(id);
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> whereById(boolean condition, Object id) {
        getClientUpdate().whereById(condition, id);
        return this;
    }


    default <TProperty> ProxyExpressionUpdatable<TProxy,T> whereByIds(Collection<TProperty> ids) {
        getClientUpdate().whereByIds(ids);
        return this;
    }

    default <TProperty> ProxyExpressionUpdatable<TProxy,T> whereByIds(boolean condition, Collection<TProperty> ids) {
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

