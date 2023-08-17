package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Aggregatable1<T1> {

    /**
     * 防止溢出
     *
     * @param property
     * @return
     */
    default BigDecimal sumBigDecimalOrNull(String property) {
        return sumBigDecimalOrDefault(property, null);
    }

    <TMember extends Number> BigDecimal sumBigDecimalOrDefault(String property, BigDecimal def);

    default <TMember extends Number> TMember sumOrNull(String property) {
        return sumOrDefault(property, null);
    }

    <TMember extends Number> TMember sumOrDefault(String property, TMember def);

    default <TMember extends Comparable<?>> TMember maxOrNull(String property) {
        return maxOrDefault(property, null);
    }

    <TMember extends Comparable<?>> TMember maxOrDefault(String property, TMember def);

    default <TMember> TMember minOrNull(String property) {
        return minOrDefault(property, null);
    }

    <TMember> TMember minOrDefault(String property, TMember def);

    default <TMember extends Number> Double avgOrNull(String property) {
        return avgOrDefault(property, null, Double.class);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(String property) {
        return avgOrDefault(property, null, BigDecimal.class);
    }

    default <TMember extends Number> Float avgFloatOrNull(String property) {
        return avgOrDefault(property, null, Float.class);
    }

    default <TMember extends Number> Double avgOrDefault(String property, Double def) {
        return avgOrDefault(property, def, Double.class);
    }

    default <TMember extends Number> BigDecimal avgOrDefault(String property, BigDecimal def) {
        return avgOrDefault(property, def, BigDecimal.class);
    }

    default <TMember extends Number> Float avgOrDefault(String property, Float def) {
        return avgOrDefault(property, def, Float.class);
    }

    <TMember extends Number, TResult extends Number> TResult avgOrDefault(String property, TResult def, Class<TResult> resultClass);

    <TMember> List<TMember> selectAggregateList(TableAvailable table, ColumnFunction columnFunction, String property, Class<TMember> resultClass);

}
