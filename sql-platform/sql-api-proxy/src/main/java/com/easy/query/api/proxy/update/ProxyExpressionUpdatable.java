package com.easy.query.api.proxy.update;

import com.easy.query.api.proxy.sql.expression.MultiProxyFilter;
import com.easy.query.api.proxy.sql.expression.impl.MultiProxyFilterImpl;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
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

    default <TProperty> ProxyExpressionUpdatable<TProxy,T> set(SQLColumn<TProxy,TProperty> setColumn, TProperty val) {
        getClientUpdate().set(setColumn.value(), val);
        return this;
    }

    default <TProperty> ProxyExpressionUpdatable<TProxy,T> set(boolean condition, SQLColumn<TProxy,TProperty> setColumn, TProperty val) {
        if(condition){
            getClientUpdate().set(setColumn.value(), val);
        }
        return this;
    }

    default <TProperty> ProxyExpressionUpdatable<TProxy,T> setWithColumn(SQLColumn<TProxy,TProperty> setColumn1, SQLColumn<TProxy,TProperty> setColumn2) {
        return setWithColumn(true,setColumn1,setColumn2);
    }

    default <TProperty> ProxyExpressionUpdatable<TProxy,T> setWithColumn(boolean condition, SQLColumn<TProxy,TProperty> setColumn1, SQLColumn<TProxy,TProperty> setColumn2) {
        if(condition){
            getClientUpdate().setWithColumn(setColumn1.value(), setColumn2.value());
        }
        return this;
    }
    // region 列++ --


    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLColumn<TProxy,Integer> setColumn) {
        return setIncrement(true,setColumn);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLColumn<TProxy,Integer> setColumn) {
        if(condition){
            getClientUpdate().setIncrement(setColumn.value());
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLColumn<TProxy,Integer> setColumn, int val) {
        return setIncrement(true,setColumn,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLColumn<TProxy,Integer> setColumn, int val) {
        if(condition){
            getClientUpdate().setIncrement(setColumn.value(), val);
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLColumn<TProxy,Long> setColumn, long val) {
        return setIncrement(true,setColumn,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrement(boolean condition, SQLColumn<TProxy,Long> setColumn, long val) {
        if(condition){
            getClientUpdate().setIncrement(setColumn.value(),val);
        }
        return this;
    }


    default ProxyExpressionUpdatable<TProxy,T> setIncrement(SQLColumn<TProxy,? extends Number> setColumn, Number val) {
        return setIncrementNumber(true,setColumn,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setIncrementNumber(boolean condition, SQLColumn<TProxy,? extends Number> setColumn, Number val) {
        if(condition){
            getClientUpdate().setIncrementNumber(true,setColumn.value(), val);
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLColumn<TProxy,Integer> setColumn) {
        return setDecrement(true,setColumn);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLColumn<TProxy,Integer> setColumn) {
        if(condition){
            getClientUpdate().setDecrement(setColumn.value());
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLColumn<TProxy,Integer> setColumn, int val) {
        return setDecrement(true,setColumn,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLColumn<TProxy,Integer> setColumn, int val) {
        if(condition){
            getClientUpdate().setDecrement(setColumn.value(), val);
        }
        return this;
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLColumn<TProxy,Long> setColumn, long val) {
        return setDecrement(true,setColumn,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrement(boolean condition, SQLColumn<TProxy,Long> setColumn, long val) {
        if(condition){
            getClientUpdate().setDecrement(setColumn.value(), val);
        }
        return this;
    }


    default ProxyExpressionUpdatable<TProxy,T> setDecrement(SQLColumn<TProxy,? extends Number> setColumn, Number val) {
        return setDecrementNumber(true,setColumn,val);
    }

    default ProxyExpressionUpdatable<TProxy,T> setDecrementNumber(boolean condition, SQLColumn<TProxy,? extends Number> setColumn, Number val) {
        if(condition){
            getClientUpdate().setDecrementNumber(true,setColumn.value(), val);
        }
        return this;
    }
    // endregion

    default ProxyExpressionUpdatable<TProxy,T> where(SQLExpression1<MultiProxyFilter<TProxy>> whereExpression) {
        return where(true,whereExpression);
    }

    default ProxyExpressionUpdatable<TProxy,T> where(boolean condition, SQLExpression1<MultiProxyFilter<TProxy>> whereExpression) {
        if(condition){

            getClientUpdate().where(where -> {
                whereExpression.apply(new MultiProxyFilterImpl<>(getProxy(),where.getFilter()));
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

