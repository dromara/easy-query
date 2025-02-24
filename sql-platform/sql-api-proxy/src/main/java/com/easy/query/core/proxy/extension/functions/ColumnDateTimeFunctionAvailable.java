package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.proxy.PropColumn;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.impl.duration.DurationExpression;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnDateTimeFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionCompareComparableDateTimeChainExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty> {


    default ColumnFunctionCompareComparableStringChainExpression<String> format(String javaFormat) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.dateTimeFormat(x->x.sqlFunc(sqlFunction),javaFormat);
            } else {
                return fx.dateTimeFormat(this.getValue(),javaFormat);
            }
        }, String.class);
    }
    default ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> plusTime(long duration, TimeUnitEnum timeUnit) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.plusDateTime2(selector->{
                PropTypeColumn.columnFuncSelector(selector,this);
                selector.value(duration);
            },timeUnit);
        }, getPropertyType());
    }
    default <T extends Number> ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> plusTime(PropTypeColumn<T> propTypeColumn, TimeUnitEnum timeUnit) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.plusDateTime2(selector->{
                PropTypeColumn.columnFuncSelector(selector,this);
                PropTypeColumn.columnFuncSelector(selector,propTypeColumn);
            },timeUnit);
        }, getPropertyType());
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> plusMonths(long month) {
        return plusTime(month,TimeUnitEnum.MONTHS);
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> plusYears(long year) {
        return plusTime(year,TimeUnitEnum.YEARS);
    }

    default ColumnFunctionCompareComparableNumberChainExpression<Integer> dayOfYear() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.DayOfYear);
    }

    /**
     * 星期0-6为0-6星期日为0
     *
     * @return 返回指定日期
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> dayOfWeek() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.DayOfWeek);
    }

    /**
     * yyyy 指定时间的年份
     *
     * @return 返回当前年份
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> year() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.Year);
    }

    /**
     * MM 1-12
     *
     * @return 返回1到12表示月份
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> month() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.Month);
    }

    /**
     * dd 1-31
     *
     * @return 返回1-31表示在月份中的天数
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> day() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.Day);
    }

    /**
     * HH 24小时制0-23
     *
     * @return 返回0-23表示小时
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> hour() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.Hour);
    }

    /**
     * mm 0-59
     *
     * @return
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> minute() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.Minute);
    }

    /**
     * ss 0-59
     *
     * @return
     */
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> second() {
        return dateTimeProp(this, this.getEntitySQLContext(), this.getTable(), this.getValue(),  DateTimeUnitEnum.Second);
    }

    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationExpression duration(ColumnDateTimeFunctionAvailable<TProperty> after) {
        return new DurationExpression(this,after);
    }

    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationExpression duration(LocalDateTime after) {
        return new DurationExpression(this,after);
    }

    /**
     * 通用返回时间属性的方法
     *
     * @param propColumn       属性列
     * @param entitySQLContext 当前上下文
     * @param table            当前表
     * @param property         当前属性
     * @param dateTimeUnitEnum 时间枚举
     * @return 返回时间属性的表达式
     */
    static ColumnFunctionCompareComparableNumberChainExpression<Integer> dateTimeProp(PropColumn propColumn, EntitySQLContext entitySQLContext, TableAvailable table, String property, DateTimeUnitEnum dateTimeUnitEnum) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(entitySQLContext, table, property, fx -> {
            if (propColumn instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) propColumn).func().apply(fx);
                return fx.dateTimeProperty(sqlFunction, dateTimeUnitEnum);
            } else {
                return fx.dateTimeProperty(propColumn.getValue(), dateTimeUnitEnum);
            }
        }, Integer.class);
    }

    @Override
    default ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
