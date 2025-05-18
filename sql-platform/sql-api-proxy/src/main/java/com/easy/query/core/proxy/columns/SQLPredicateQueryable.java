package com.easy.query.core.proxy.columns;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;

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

    default SQLPredicateQueryable<T1Proxy, T1> orderBy(SQLActionExpression1<T1Proxy> orderExpression) {
        return orderBy(true, orderExpression);
    }

    SQLPredicateQueryable<T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> orderExpression);
    T1Proxy element(int index);
    SQLPredicateQueryable<T1Proxy, T1> elements(int begin,int end);

    SQLPredicateQueryable<T1Proxy, T1> where(SQLActionExpression1<T1Proxy> whereExpression);

    /**
     * 存在任意一个满足条件
     *
     * @param whereExpression
     */
    void any(SQLActionExpression1<T1Proxy> whereExpression);

    /**
     * 存在任意一个满足条件
     */
    void any();

    void none(SQLActionExpression1<T1Proxy> whereExpression);

    void none();

    /**
     * 返回boolean表示是否存在任意匹配项
     *
     * @return
     */
    BooleanTypeExpression<Boolean> anyValue();

    /**
     * 返回boolean表示是否没有任意一项被匹配到
     *
     * @return
     */
    BooleanTypeExpression<Boolean> noneValue();

    <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    NumberTypeExpression<Long> count();

    <TMember> NumberTypeExpression<Integer> intCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    NumberTypeExpression<Integer> intCount();


    <TMember extends Number> NumberTypeExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember> AnyTypeExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    <TMember> AnyTypeExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector);

    StringTypeExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter);
}
