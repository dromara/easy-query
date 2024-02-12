package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;

import java.math.BigDecimal;

/**
 * create time 2024/2/12 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptySQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable originalTable;
    private final String navValue;

    public EmptySQLQueryable(EntitySQLContext entitySQLContext,TableAvailable originalTable,String navValue){

        this.entitySQLContext = entitySQLContext;
        this.originalTable = originalTable;
        this.navValue = navValue;
    }
    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public EasyEntityQueryable<T1Proxy, T1> getQueryable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getOriginalTable() {
        return originalTable;
    }

    @Override
    public String getNavValue() {
        return navValue;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void exists(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notExists(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ColumnFunctionComparableNumberChainExpression<Long> count(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ColumnFunctionComparableNumberChainExpression<Integer> intCount(SQLExpression1<T1Proxy> whereExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> ColumnFunctionComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> ColumnFunctionComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TMember> ColumnFunctionComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
        throw new UnsupportedOperationException();
    }
}
