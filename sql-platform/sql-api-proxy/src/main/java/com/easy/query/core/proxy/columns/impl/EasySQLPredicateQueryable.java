package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLPredicateQueryable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;

import java.math.BigDecimal;

/**
 * create time 2024/2/24 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySQLPredicateQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLPredicateQueryable<T1Proxy, T1> {
    private final SQLQueryable<T1Proxy, T1> sqlQueryable;

    public EasySQLPredicateQueryable(SQLQueryable<T1Proxy, T1> sqlQueryable) {

        this.sqlQueryable = sqlQueryable;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return sqlQueryable.getEntitySQLContext();
    }

    @Override
    public SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        sqlQueryable.getQueryable().where(whereExpression);
        return this;
    }

    @Override
    public void any(SQLExpression1<T1Proxy> whereExpression) {
        sqlQueryable.getQueryable().where(whereExpression);
        sqlQueryable.any();
    }

    @Override
    public void any() {
        sqlQueryable.any();
    }

    @Override
    public void none(SQLExpression1<T1Proxy> whereExpression) {
        sqlQueryable.getQueryable().where(whereExpression);
        sqlQueryable.none();
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue() {
        return sqlQueryable.anyValue();
    }

    @Override
    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue() {
        return sqlQueryable.noneValue();
    }

    @Override
    public void none() {
        sqlQueryable.none();
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLExpression1<T1Proxy> whereExpression) {
        sqlQueryable.getQueryable().where(whereExpression);
        return sqlQueryable.count();
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        return sqlQueryable.count();
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(SQLExpression1<T1Proxy> whereExpression) {
        sqlQueryable.getQueryable().where(whereExpression);
        return sqlQueryable.intCount();
    }

    @Override
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount() {
        return sqlQueryable.intCount();
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector, boolean distinct) {
        return sqlQueryable.sum(columnSelector, distinct);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector, boolean distinct) {
        return sqlQueryable.sumBigDecimal(columnSelector, distinct);
    }

    @Override
    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector, boolean distinct) {
        return sqlQueryable.avg(columnSelector, distinct);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return sqlQueryable.max(columnSelector);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return sqlQueryable.min(columnSelector);
    }

    @Override
    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> select(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        return sqlQueryable.select(columnSelector);
    }
//    @Override
//    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
//        return sqlQueryable.sum(columnSelector);
//    }
//
//    @Override
//    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.sumBigDecimal(columnSelector);
//    }
//
//    @Override
//    public <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.avg(columnSelector);
//    }
//
//    @Override
//    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.max(columnSelector);
//    }
//
//    @Override
//    public <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector) {
//        return sqlQueryable.max(columnSelector);
//    }
}
