package com.easy.query.core.proxy;

import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * create time 2024/1/10 13:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public interface SQLConstantExpression extends EntitySQLContextAvailable {

    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableDateTimeChainExpression<LocalDateTime> valueOf(LocalDateTime val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), LocalDateTime.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableDateTimeChainExpression<LocalDate> valueOf(LocalDate val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), LocalDate.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableDateTimeChainExpression<LocalTime> valueOf(LocalTime val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), LocalTime.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableDateTimeChainExpression<Date> valueOf(Date val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Date.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableDateTimeChainExpression<java.sql.Date> valueOf(java.sql.Date val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), java.sql.Date.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableDateTimeChainExpression<Time> valueOf(Time val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Time.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableDateTimeChainExpression<Timestamp> valueOf(Timestamp val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Timestamp.class);
    }

    default ColumnFunctionCompareComparableStringChainExpression<String> valueOf(String val) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), String.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default <TNumber extends Number> ColumnFunctionCompareComparableNumberChainExpression<TNumber> valueOf(Number val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), BigDecimal.class);
    }

    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> valueOf(BigDecimal val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), BigDecimal.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<Double> valueOf(Double val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Double.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<Float> valueOf(Float val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Float.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<Long> valueOf(Long val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Long.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> valueOf(Integer val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Integer.class);
    }

    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<Short> valueOf(Short val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Short.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<Byte> valueOf(Byte val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Byte.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableBooleanChainExpression<Boolean> valueOf(Boolean val) {
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Boolean.class);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default <TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> valueOf(TProperty val, Class<TProperty> clazz) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), clazz);
    }


    /**
     * 请使用o.expression().constant(val)
     * @param val
     * @return
     */
    @Deprecated
    default <TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> valueOfNull() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(0), Object.class);
    }
}
