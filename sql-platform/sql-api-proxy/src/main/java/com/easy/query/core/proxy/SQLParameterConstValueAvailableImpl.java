package com.easy.query.core.proxy;

import com.easy.query.core.proxy.core.EntitySQLContext;
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
 * create time 2024/1/10 13:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLParameterConstValueAvailableImpl implements SQLParameterConstValueAvailable {


    private final EntitySQLContext entitySQLContext;

    public SQLParameterConstValueAvailableImpl(EntitySQLContext entitySQLContext){
        this.entitySQLContext = entitySQLContext;
    }


    public ColumnFunctionComparableDateTimeChainExpression<LocalDateTime> valueOf(LocalDateTime val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),LocalDateTime.class);
    }
    public  ColumnFunctionComparableDateTimeChainExpression<LocalDate> valueOf(LocalDate val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),LocalDate.class);
    }
    public  ColumnFunctionComparableDateTimeChainExpression<LocalTime> valueOf(LocalTime val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),LocalTime.class);
    }
    public  ColumnFunctionComparableDateTimeChainExpression<Date> valueOf(Date val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),Date.class);
    }
    public  ColumnFunctionComparableDateTimeChainExpression<java.sql.Date> valueOf(java.sql.Date val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),java.sql.Date.class);
    }
    public  ColumnFunctionComparableDateTimeChainExpression<Time> valueOf(Time val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),Time.class);
    }
    public  ColumnFunctionComparableDateTimeChainExpression<Timestamp> valueOf(Timestamp val) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(),null, null,f->f.constValue(val),Timestamp.class);
    }
    public ColumnFunctionComparableStringChainExpression<String> valueOf(String val) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),String.class);
    }
    public ColumnFunctionComparableNumberChainExpression<BigDecimal> valueOf(BigDecimal val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),BigDecimal.class);
    }
    public ColumnFunctionComparableNumberChainExpression<Double> valueOf(Double val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Double.class);
    }
    public ColumnFunctionComparableNumberChainExpression<Float> valueOf(Float val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Float.class);
    }
    public ColumnFunctionComparableNumberChainExpression<Long> valueOf(Long val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Long.class);
    }
    public ColumnFunctionComparableNumberChainExpression<Integer> valueOf(Integer val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Integer.class);
    }
    public ColumnFunctionComparableNumberChainExpression<Short> valueOf(Short val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Short.class);
    }
    public ColumnFunctionComparableNumberChainExpression<Byte> valueOf(Byte val) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Byte.class);
    }
    public ColumnFunctionComparableBooleanChainExpression<Boolean> valueOf(Boolean val) {
        return new ColumnFunctionComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),Boolean.class);
    }
    public <TProperty> ColumnFunctionComparableAnyChainExpression<TProperty> valueOf(TProperty val, Class<TProperty> clazz) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(),null, null, f->f.constValue(val),clazz);
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
}
