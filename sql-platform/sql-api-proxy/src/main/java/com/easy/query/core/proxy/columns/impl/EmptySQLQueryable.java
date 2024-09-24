package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.columns.SQLPredicateQueryable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;

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
    public EntityQueryable<T1Proxy, T1> getQueryable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getOriginalTable() {
        return null;
    }

    @Override
    public String getNavValue() {
        return t1Proxy.getNavValue();
    }

    @Override
    public T1Proxy getProxy() {
        return t1Proxy;
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLQueryable<T1Proxy, T1> useLogicDelete(boolean enable) {
        return this;
    }
}
