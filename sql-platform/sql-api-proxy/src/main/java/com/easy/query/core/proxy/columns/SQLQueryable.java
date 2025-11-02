package com.easy.query.core.proxy.columns;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ManyPropColumn;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
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
public interface SQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntitySQLContextAvailable, ManyPropColumn<T1Proxy, T1> {//,ProxyEntity<T1Proxy,T1>

    SubQueryContext<T1Proxy, T1> getSubQueryContext();


//    SQLQueryable<T1Proxy, T1> elements(int begin,int end);
//    SQLQueryable<T1Proxy, T1> subList(int begin,int end);

    default SQLQueryable<T1Proxy, T1> distinct() {
        return distinct(true);
    }

    SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct);

    default SQLQueryable<T1Proxy, T1> orderBy(SQLActionExpression1<T1Proxy> orderExpression) {
        return orderBy(true, orderExpression);
    }

    SQLQueryable<T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> orderExpression);

//    SQLQueryable<T1Proxy, T1> elements(int begin,int end);

    SQLQueryable<T1Proxy, T1> where(SQLActionExpression1<T1Proxy> whereExpression);

    /**
     * 存在任意一个满足条件
     *
     * @param whereExpression
     */
    void any(SQLActionExpression1<T1Proxy> whereExpression);

    /**
     * 之前的集合都满足条件
     * @param allExpression
     */
    void all(SQLActionExpression1<T1Proxy> allExpression);

    /**
     * 至少集合非空且全部满足where条件
     * @param whereExpression
     */
    default void notEmptyAll(SQLActionExpression1<T1Proxy> whereExpression){
        getEntitySQLContext()._whereAnd(()->{
            any();
            all(whereExpression);
        });
    }

    /**
     * 存在任意一个满足条件
     */
    void any();

    /**
     * 不存在任意一个满足条件
     *
     * @param whereExpression
     */
    void none(SQLActionExpression1<T1Proxy> whereExpression);

    /**
     * 不存在任意一个满足条件
     */
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

    default StringTypeExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector){
        return joining(columnSelector,",");
    }
    StringTypeExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter);

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




}
