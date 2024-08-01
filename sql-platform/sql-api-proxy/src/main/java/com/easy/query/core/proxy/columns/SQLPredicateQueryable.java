package com.easy.query.core.proxy.columns;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableBooleanChainExpressionImpl;

import java.math.BigDecimal;

/**
 * create time 2024/2/11 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLPredicateQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntitySQLContextAvailable {

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
    ColumnFunctionComparableBooleanChainExpression<Boolean> anyValue();

    /**
     * 返回boolean表示是否没有任意一项被匹配到
     *
     * @return
     */
    ColumnFunctionComparableBooleanChainExpression<Boolean> noneValue();

    ColumnFunctionComparableNumberChainExpression<Long> count(SQLExpression1<T1Proxy> whereExpression);

    ColumnFunctionComparableNumberChainExpression<Long> count();

    ColumnFunctionComparableNumberChainExpression<Integer> intCount(SQLExpression1<T1Proxy> whereExpression);

    ColumnFunctionComparableNumberChainExpression<Integer> intCount();

    <TMember extends Number> ColumnFunctionComparableNumberChainExpression<TMember> sum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);

    <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);

    <TMember extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);

    <TMember> ColumnFunctionComparableAnyChainExpression<TMember> max(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);

    <TMember> ColumnFunctionComparableAnyChainExpression<TMember> min(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);
}
