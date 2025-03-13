package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ManyPropColumn;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
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
public interface SQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntitySQLContextAvailable, ManyPropColumn<T1Proxy, T1> {//,ProxyEntity<T1Proxy,T1>

    SubQueryContext<T1Proxy, T1> getSubQueryContext();


//    SQLQueryable<T1Proxy, T1> elements(int begin,int end);
//    SQLQueryable<T1Proxy, T1> subList(int begin,int end);

    default SQLQueryable<T1Proxy, T1> distinct() {
        return distinct(true);
    }

    SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct);

    default SQLQueryable<T1Proxy, T1> orderBy(SQLExpression1<T1Proxy> orderExpression) {
        return orderBy(true, orderExpression);
    }

    SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> orderExpression);

//    SQLQueryable<T1Proxy, T1> elements(int begin,int end);

    SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression);

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

    /**
     * 不存在任意一个满足条件
     *
     * @param whereExpression
     */
    void none(SQLExpression1<T1Proxy> whereExpression);

    /**
     * 不存在任意一个满足条件
     */
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

    /**
     * 暂开集合元素
     * 用户返回集合元素
     *
     * @return
     */
    default T1Proxy flatElement() {
        return flatElement(null);
    }

    T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression);


    /**
     * 请使用{@link #configureToSubQuery(SQLExpression1)}
     *
     * @param configureExpression
     * @return
     */
    @Deprecated
    default SQLQueryable<T1Proxy, T1> configure(SQLExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression) {
        return configureToSubQuery(configureExpression);
    }

    /**
     * 仅子查询配置生效
     * manyJoin下使用则会转成独立SubQuery
     *
     * @param configureExpression
     * @return
     */
    SQLQueryable<T1Proxy, T1> configureToSubQuery(SQLExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression);
}
