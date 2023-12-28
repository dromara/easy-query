package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.proxy.PropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnDateTimeFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionComparableDateTimeChainExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty> {


    default ColumnFunctionComparableStringChainExpression<String> format(String javaFormat) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.dateTimeFormat(this.getValue(), javaFormat);
        }, String.class);
    }

    /**
     * 最小精度为秒部分数据库支持秒以下精度
     * @param duration
     * @param timeUnit
     * @return
     */
    default ColumnFunctionComparableDateTimeChainExpression<TProperty> plus(long duration, TimeUnit timeUnit) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTime(sqlFunction,duration,timeUnit);
            } else {
                return fx.plusDateTime(this.getValue(),duration,timeUnit);
            }
        }, getPropertyType());
    }
    default ColumnFunctionComparableDateTimeChainExpression<TProperty> plusMonths(int month) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTimeMonths(sqlFunction,month);
            } else {
                return fx.plusDateTimeMonths(this.getValue(),month);
            }
        }, getPropertyType());
    }
    default ColumnFunctionComparableDateTimeChainExpression<TProperty> plusYears(int year) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTimeYears(sqlFunction,year);
            } else {
                return fx.plusDateTimeYears(this.getValue(),year);
            }
        }, getPropertyType());
    }
    default ColumnFunctionComparableNumberChainExpression<Integer> dayOfYear() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.DayOfYear);
    }

    /**
     * 星期1-6为1-6星期日为0
     * @return
     */
    default ColumnFunctionComparableNumberChainExpression<Integer> dayOfWeek() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.DayOfWeek);
    }
    default ColumnFunctionComparableNumberChainExpression<Integer> year() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.Year);
    }

    /**
     * MM 1-12
     * @return
     */
    default ColumnFunctionComparableNumberChainExpression<Integer> month() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.Month);
    }

    /**
     * 1-31
     * @return
     */
    default ColumnFunctionComparableNumberChainExpression<Integer> day() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.Day);
    }

    /**
     * HH 24小时制0-23
     * @return
     */
    default ColumnFunctionComparableNumberChainExpression<Integer> hour() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.Hour);
    }

    /**
     * mm 0-60
     * @return
     */
    default ColumnFunctionComparableNumberChainExpression<Integer> minute() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.Minute);
    }

    /**
     * ss 0-60
     * @return
     */
    default ColumnFunctionComparableNumberChainExpression<Integer> second() {
        return dateTimeProp(this,this.getEntitySQLContext(),this.getTable(),this.getValue(),DateTimeUnitEnum.Second);
    }
    /**
     * a.duration(b,DateTimeDurationEnum.Days)
     * a比b大多少天,如果a小于b则返回负数
     * 两个日期a,b之间相隔多少天
     * @param otherDateTime 被比较的时间
     * @param durationEnum 返回相差枚举比如天数
     * @return 如果为负数表示
     */
    default ColumnFunctionComparableNumberChainExpression<Long> duration(ColumnDateTimeFunctionAvailable<TProperty> otherDateTime, DateTimeDurationEnum durationEnum) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                if(otherDateTime instanceof DSLSQLFunctionAvailable){
                    DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) otherDateTime;
                    SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                    return fx.duration(sqlFunction,otherDateTimeFunction,durationEnum);
                }else{
                    return fx.duration(sqlFunction,otherDateTime,otherDateTime.getValue(),durationEnum);
                }
            } else {
                if(otherDateTime instanceof DSLSQLFunctionAvailable){
                    DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) otherDateTime;
                    SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                    return fx.duration(this.getValue(),otherDateTimeFunction,durationEnum);
                }else{
                    return fx.duration(this.getValue(),otherDateTime,otherDateTime.getValue(),durationEnum);
                }
            }
        }, Long.class);
    }
    /**
     * a.duration(b,DateTimeDurationEnum.Days)
     * a比b大多少天,如果a小于b则返回负数
     * 两个日期a,b之间相隔多少天
     * @param otherDateTime 被比较的时间
     * @param durationEnum 返回相差枚举比如天数
     * @return 如果为负数表示
     */
    default ColumnFunctionComparableNumberChainExpression<Long> duration(LocalDateTime otherDateTime, DateTimeDurationEnum durationEnum) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.duration(sqlFunction,otherDateTime,durationEnum);
            } else {
                return fx.duration(this.getValue(),otherDateTime,durationEnum);
            }
        }, Long.class);
    }
    static ColumnFunctionComparableNumberChainExpression<Integer> dateTimeProp(PropColumn propColumn, EntitySQLContext entitySQLContext, TableAvailable table, String property, DateTimeUnitEnum dateTimeUnitEnum){
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(entitySQLContext, table, property, fx -> {
            if (propColumn instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) propColumn).func().apply(fx);
                return fx.dateTimeProperty(sqlFunction,dateTimeUnitEnum);
            } else {
                return fx.dateTimeProperty(propColumn.getValue(),dateTimeUnitEnum);
            }
        }, Integer.class);
    }

    @Override
    default ColumnFunctionComparableDateTimeChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
