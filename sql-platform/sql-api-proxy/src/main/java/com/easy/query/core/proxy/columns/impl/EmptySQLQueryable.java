package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyFlatElementEntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;

import java.math.BigDecimal;

/**
 * create time 2024/2/12 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptySQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {
    private final EntitySQLContext entitySQLContext;
    private final T1Proxy t1Proxy;

    public EmptySQLQueryable(EntitySQLContext entitySQLContext,T1Proxy t1Proxy){

        this.entitySQLContext = entitySQLContext;
        this.t1Proxy = t1Proxy;
    }
    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public TableAvailable getOriginalTable() {
        return null;
    }

//    @Override
//    public T1Proxy element(int index) {
//        return null;
//    }

    @Override
    public String getNavValue() {
        return t1Proxy.getNavValue();
    }

    @Override
    public T1Proxy getProxy() {
        return t1Proxy;
    }

    @Override
    public SubQueryContext<T1Proxy, T1> getSubQueryContext() {
        return null;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean distinct) {
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> orderExpression) {
        return null;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> where(SQLActionExpression1<T1Proxy> whereExpression) {
        return this;
    }
    @Override
    public void any(SQLActionExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void any() {

    }

    @Override
    public void none(SQLActionExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void none() {

    }

    @Override
    public BooleanTypeExpression<Boolean> anyValue() {
        return null;
    }

    @Override
    public BooleanTypeExpression<Boolean> noneValue() {
        return null;
    }

    @Override
    public <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return null;
    }

    @Override
    public NumberTypeExpression<Long> count() {
        return null;
    }

    @Override
    public <TMember> NumberTypeExpression<Integer> intCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return null;
    }

    @Override
    public NumberTypeExpression<Integer> intCount() {
        return null;
    }


    @Override
    public <TMember extends Number> NumberTypeExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StringTypeExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {
        QueryRuntimeContext runtimeContext = this.getEntitySQLContext().getRuntimeContext();
        T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), new ProxyFlatElementEntitySQLContext(this,null, runtimeContext, flatAdapterExpression));
        tPropertyProxy.setNavValue(getNavValue());
        return tPropertyProxy;
    }


    @Override
    public String getValue() {
        return "";
    }
}
