package com.easy.query.api.proxy.select.extension.queryable;

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
     * @param sqlColumn
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLColumn<T1Proxy,TMember> sqlColumn) {
        return sumBigDecimalOrDefault(sqlColumn, null);
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLColumn<T1Proxy,TMember> sqlColumn, BigDecimal def) {
        return getClientQueryable().sumBigDecimalOrDefault(sqlColumn.getValue(), def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLColumn<T1Proxy,TMember> sqlColumn) {
        return getClientQueryable().sumOrNull(sqlColumn.getValue());
    }

    default <TMember extends Number> TMember sumOrDefault(SQLColumn<T1Proxy,TMember> sqlColumn, TMember def) {
        return getClientQueryable().sumOrDefault(sqlColumn.getValue(), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(SQLColumn<T1Proxy,TMember> sqlColumn) {
        return getClientQueryable().maxOrNull(sqlColumn.getValue());
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(SQLColumn<T1Proxy,TMember> sqlColumn, TMember def) {
        return getClientQueryable().maxOrDefault(sqlColumn.getValue(), def);
    }

    default <TMember> TMember minOrNull(SQLColumn<T1Proxy,TMember> sqlColumn) {
        return getClientQueryable().minOrNull(sqlColumn.getValue());
    }

    default <TMember> TMember minOrDefault(SQLColumn<T1Proxy,TMember> sqlColumn, TMember def) {
        return getClientQueryable().minOrDefault(sqlColumn.getValue(), def);
    }

    default <TMember extends Number> Double avgOrNull(SQLColumn<T1Proxy,TMember> sqlColumn) {
        return getClientQueryable().avgOrNull(sqlColumn.getValue());
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLColumn<T1Proxy,TMember> sqlColumn) {
        return getClientQueryable().avgBigDecimalOrNull(sqlColumn.getValue());
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLColumn<T1Proxy,TMember> sqlColumn) {
        return getClientQueryable().avgFloatOrNull(sqlColumn.getValue());
    }

    default <TMember extends Number> Double avgOrDefault(SQLColumn<T1Proxy,TMember> sqlColumn, Double def) {
        return getClientQueryable().avgOrDefault(sqlColumn.getValue(), def);
    }

    default BigDecimal avgOrDefault(SQLColumn<T1Proxy,BigDecimal> sqlColumn, BigDecimal def) {
        return getClientQueryable().avgOrDefault(sqlColumn.getValue(), def);
    }

    default Float avgOrDefault(SQLColumn<T1Proxy,Float> sqlColumn, Float def) {
        return getClientQueryable().avgOrDefault(sqlColumn.getValue(), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLColumn<T1Proxy,TMember> sqlColumn, TResult def, Class<TResult> resultClass) {
        return getClientQueryable().avgOrDefault(sqlColumn.getValue(), def, resultClass);
    }

}
