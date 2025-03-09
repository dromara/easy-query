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
public interface SQLConstantExpression extends EntitySQLContextAvailable {

    default ColumnFunctionCompareComparableDateTimeChainExpression<LocalDateTime> valueOf(LocalDateTime val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), LocalDateTime.class);
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<LocalDate> valueOf(LocalDate val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), LocalDate.class);
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<LocalTime> valueOf(LocalTime val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), LocalTime.class);
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<Date> valueOf(Date val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Date.class);
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<java.sql.Date> valueOf(java.sql.Date val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), java.sql.Date.class);
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<Time> valueOf(Time val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Time.class);
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<Timestamp> valueOf(Timestamp val) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Timestamp.class);
    }

    default ColumnFunctionCompareComparableStringChainExpression<String> valueOf(String val) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), String.class);
    }

    default <TNumber extends Number> ColumnFunctionCompareComparableNumberChainExpression<TNumber> valueOf(Number val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), BigDecimal.class);
    }
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> valueOf(BigDecimal val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), BigDecimal.class);
    }

    default ColumnFunctionCompareComparableNumberChainExpression<Double> valueOf(Double val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Double.class);
    }

    default ColumnFunctionCompareComparableNumberChainExpression<Float> valueOf(Float val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Float.class);
    }

    default ColumnFunctionCompareComparableNumberChainExpression<Long> valueOf(Long val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Long.class);
    }

    default ColumnFunctionCompareComparableNumberChainExpression<Integer> valueOf(Integer val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Integer.class);
    }

    default ColumnFunctionCompareComparableNumberChainExpression<Short> valueOf(Short val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Short.class);
    }

    default ColumnFunctionCompareComparableNumberChainExpression<Byte> valueOf(Byte val) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Byte.class);
    }

    default ColumnFunctionCompareComparableBooleanChainExpression<Boolean> valueOf(Boolean val) {
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), Boolean.class);
    }

    default <TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> valueOf(TProperty val, Class<TProperty> clazz) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(val), clazz);
    }

    default <TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> valueOfNull() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), null, null, f -> f.constValue(0), Object.class);
    }
}
