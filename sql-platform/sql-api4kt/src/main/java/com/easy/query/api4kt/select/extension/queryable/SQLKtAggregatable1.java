package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import kotlin.reflect.KProperty1;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAggregatable1<T1> extends ClientKtQueryableAvailable<T1> {
    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(KProperty1<? super T1, TMember> column) {
        return getClientQueryable().sumBigDecimalOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(KProperty1<? super T1, TMember> column, BigDecimal def) {
        return getClientQueryable().sumBigDecimalOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> TMember sumOrNull(KProperty1<? super T1, TMember> column) {
        return getClientQueryable().sumOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> TMember sumOrDefault(KProperty1<? super T1, TMember> column, TMember def) {
        return getClientQueryable().sumOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(KProperty1<? super T1, TMember> column) {
        return getClientQueryable().maxOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(KProperty1<? super T1, TMember> column, TMember def) {
        return getClientQueryable().maxOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember> TMember minOrNull(KProperty1<? super T1, TMember> column) {
        return getClientQueryable().minOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember> TMember minOrDefault(KProperty1<? super T1, TMember> column, TMember def) {
        return getClientQueryable().minOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> Double avgOrNull(KProperty1<? super T1, TMember> column) {
        return getClientQueryable().avgOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(KProperty1<? super T1, TMember> column) {
        return getClientQueryable().avgBigDecimalOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Float avgFloatOrNull(KProperty1<? super T1, TMember> column) {
        return getClientQueryable().avgFloatOrNull(EasyKtLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Double avgOrDefault(KProperty1<? super T1, TMember> column, Double def) {
        return getClientQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default BigDecimal avgOrDefault(KProperty1<? super T1, BigDecimal> column, BigDecimal def) {
        return getClientQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default Float avgOrDefault(KProperty1<? super T1, Float> column, Float def) {
        return getClientQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(KProperty1<? super T1, TMember> column, TResult def, Class<TResult> resultClass) {
        return getClientQueryable().avgOrDefault(EasyKtLambdaUtil.getPropertyName(column), def, resultClass);
    }
}
