package com.easy.query.core.proxy.grouping;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

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

    <T extends Long> ColumnFunctionCompareComparableNumberChainExpression<T> count();

    <T extends Integer> ColumnFunctionCompareComparableNumberChainExpression<T> intCount();

    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    <TMember> ColumnFunctionCompareComparableStringChainExpression<String> join(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector, String delimiter);
}
