package com.easy.query.core.proxy.columns;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;

import java.math.BigDecimal;

/**
 * create time 2024/2/11 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLPredicateQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntitySQLContextAvailable {

    default SQLPredicateQueryable<T1Proxy, T1> distinct() {
        return distinct(true);
    }

    SQLPredicateQueryable<T1Proxy, T1> distinct(boolean useDistinct);

    default SQLPredicateQueryable<T1Proxy, T1> orderBy(SQLExpression1<T1Proxy> orderExpression) {
        return orderBy(true, orderExpression);
    }

    SQLPredicateQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> orderExpression);
    T1Proxy element(int index);
    SQLPredicateQueryable<T1Proxy, T1> elements(int begin,int end);

    SQLPredicateQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression);

    /**
     * 存在任意一个满足条件
     *
     * @param whereExpression
     */
    void any(SQLExpression1<T1Proxy> whereExpression);

    /**
     * 存在任意一个满足条件
     */
    void any();

    void none(SQLExpression1<T1Proxy> whereExpression);

    void none();

    /**
     * 返回boolean表示是否存在任意匹配项
     *
     * @return
     */
    ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue();

    /**
     * 返回boolean表示是否没有任意一项被匹配到
     *
     * @return
     */
    ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue();

    <TMember> ColumnFunctionCompareComparableNumberChainExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    ColumnFunctionCompareComparableNumberChainExpression<Long> count();

    <TMember> ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount();


    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    <TMember> ColumnFunctionCompareComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter);
}
