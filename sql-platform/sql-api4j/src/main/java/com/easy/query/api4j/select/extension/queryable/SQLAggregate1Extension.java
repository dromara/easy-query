package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregate1Extension<T1> extends ClientQueryableAvailable<T1>{
    /**
     * 防止溢出
     *
     * @param column
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(Property<T1, TMember> column) {
        return getClientQueryable().sumBigDecimalOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Property<T1, TMember> column, BigDecimal def) {
        return getClientQueryable().sumBigDecimalOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> TMember sumOrNull(Property<T1, TMember> column) {
        return getClientQueryable().sumOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> TMember sumOrDefault(Property<T1, TMember> column, TMember def) {
        return getClientQueryable().sumOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(Property<T1, TMember> column) {
        return getClientQueryable().maxOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(Property<T1, TMember> column, TMember def) {
        return getClientQueryable().maxOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember> TMember minOrNull(Property<T1, TMember> column) {
        return getClientQueryable().minOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember> TMember minOrDefault(Property<T1, TMember> column, TMember def) {
        return getClientQueryable().minOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number> Double avgOrNull(Property<T1, TMember> column) {
        return getClientQueryable().avgOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(Property<T1, TMember> column) {
        return getClientQueryable().avgBigDecimalOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Float avgFloatOrNull(Property<T1, TMember> column) {
        return getClientQueryable().avgFloatOrNull(EasyLambdaUtil.getPropertyName(column));
    }

    default <TMember extends Number> Double avgOrDefault(Property<T1, TMember> column, Double def) {
        return getClientQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default BigDecimal avgOrDefault(Property<T1, BigDecimal> column, BigDecimal def) {
        return getClientQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default Float avgOrDefault(Property<T1, Float> column, Float def) {
        return getClientQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(Property<T1, TMember> column, TResult def, Class<TResult> resultClass) {
        return getClientQueryable().avgOrDefault(EasyLambdaUtil.getPropertyName(column), def, resultClass);
    }
}
