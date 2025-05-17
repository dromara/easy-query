package com.easy.query.core.proxy.grouping;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.StringTypeExpression;

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

    NumberTypeExpression<Long> count();

    <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    NumberTypeExpression<Integer> intCount();

    <TMember> NumberTypeExpression<Integer> intCount(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    <TMember extends Number> NumberTypeExpression<TMember> sum(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember extends Number> NumberTypeExpression<Integer> sumInt(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);

    <TMember extends Number> NumberTypeExpression<Long> sumLong(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector);


    <TMember> AnyTypeExpression<TMember> max(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    <TMember> AnyTypeExpression<TMember> min(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector);

    default <TMember> StringTypeExpression<String> joining(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector){
        return joining(columnSelector,",");
    }
    <TMember> StringTypeExpression<String> joining(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TMember>> columnSelector, String delimiter);

//    <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy element(int index, SQLFuncExpression1<TSourceProxy,T1Proxy> elementSelector);
}
