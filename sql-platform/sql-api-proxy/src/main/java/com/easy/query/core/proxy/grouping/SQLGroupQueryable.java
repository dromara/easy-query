package com.easy.query.core.proxy.grouping;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;

import java.math.BigDecimal;

/**
 * create time 2025/3/4 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupQueryable<TSourceProxy> extends EntitySQLContextAvailable {

    default SQLGroupQueryable<TSourceProxy> distinct() {
        return distinct(true);
    }

    SQLGroupQueryable<TSourceProxy> distinct(boolean useDistinct);

    ColumnFunctionCompareComparableNumberChainExpression<Long> count();

    <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount();

    <TMember> ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<Integer> sumInt(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<Long> sumLong(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    <TMember> ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector, String delimiter);

//    <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy element(int index, SQLFuncExpression1<TSourceProxy,T1Proxy> elementSelector);
}
