package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAggregatable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ProxyQueryableAvailable<T1Proxy,T1>,ClientProxyQueryableAvailable<T1> {

    /**
     * 防止溢出
     *
     * @param columnSelector
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return sumBigDecimalOrDefault(columnSelector, null);
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, BigDecimal def) {
        return getClientQueryable().sumBigDecimalOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getClientQueryable().sumOrNull(columnSelector.apply(getQueryable().get1Proxy()).value());
    }

    default <TMember extends Number> TMember sumOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getClientQueryable().sumOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getClientQueryable().maxOrNull(columnSelector.apply(getQueryable().get1Proxy()).value());
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getClientQueryable().maxOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def);
    }

    default <TMember> TMember minOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getClientQueryable().minOrNull(columnSelector.apply(getQueryable().get1Proxy()).value());
    }

    default <TMember> TMember minOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getClientQueryable().minOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def);
    }

    default <TMember extends Number> Double avgOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getClientQueryable().avgOrNull(columnSelector.apply(getQueryable().get1Proxy()).value());
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getClientQueryable().avgBigDecimalOrNull(columnSelector.apply(getQueryable().get1Proxy()).value());
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getClientQueryable().avgFloatOrNull(columnSelector.apply(getQueryable().get1Proxy()).value());
    }

    default <TMember extends Number> Double avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, Double def) {
        return getClientQueryable().avgOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def);
    }

    default BigDecimal avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<BigDecimal>> columnSelector, BigDecimal def) {
        return getClientQueryable().avgOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def);
    }

    default Float avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<Float>> columnSelector, Float def) {
        return getClientQueryable().avgOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLFuncExpression1<T1Proxy, SQLColumn<TMember>> columnSelector, TResult def, Class<TResult> resultClass) {
        return getClientQueryable().avgOrDefault(columnSelector.apply(getQueryable().get1Proxy()).value(), def, resultClass);
    }

}
