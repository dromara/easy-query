package com.easy.query.core.proxy;

import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableStringChainExpressionImpl;

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
public interface SQLParameterConstValueAvailable extends EntitySQLContextAvailable {

    default ColumnFunctionComparableDateTimeChainExpression<LocalDateTime> valueOf(LocalDateTime val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),LocalDateTime.class);
    }
    default  ColumnFunctionComparableDateTimeChainExpression<LocalDate> valueOf(LocalDate val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),LocalDate.class);
    }
    default  ColumnFunctionComparableDateTimeChainExpression<LocalTime> valueOf(LocalTime val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),LocalTime.class);
    }
    default  ColumnFunctionComparableDateTimeChainExpression<Date> valueOf(Date val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),Date.class);
    }
    default  ColumnFunctionComparableDateTimeChainExpression<java.sql.Date> valueOf(java.sql.Date val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),java.sql.Date.class);
    }
    default  ColumnFunctionComparableDateTimeChainExpression<Time> valueOf(Time val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),Time.class);
    }
    default  ColumnFunctionComparableDateTimeChainExpression<Timestamp> valueOf(Timestamp val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),Timestamp.class);
    }
    default ColumnFunctionComparableStringChainExpression<String> valueOf(String val) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),String.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> valueOf(BigDecimal val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Double> valueOf(Double val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Double.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Float> valueOf(Float val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Float.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Long> valueOf(Long val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Long.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Integer> valueOf(Integer val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Integer.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Short> valueOf(Short val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Short.class);
    }
    default ColumnFunctionComparableNumberChainExpression<Byte> valueOf(Byte val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Byte.class);
    }
    default ColumnFunctionComparableBooleanChainExpression<Boolean> valueOf(Boolean val) {
        return new ColumnFunctionComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Boolean.class);
    }
    default <TProperty> ColumnFunctionComparableAnyChainExpression<TProperty> valueOf(TProperty val, Class<TProperty> clazz) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),clazz);
    }
}
