package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.DateTimeTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.DateTimeFilterTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.impl.DateTimeFilterTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.DateTimeTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.StringTypeExpressionImpl;
import com.easy.query.core.proxy.impl.duration.DurationExpression;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnDateTimeFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, DateTimeTypeExpression<TProperty>>,
        ColumnAggregateFilterFunctionAvailable<TProperty, DateTimeFilterTypeExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty> {

    @Override
    default DateTimeFilterTypeExpression<TProperty> max() {
        return createFilterChainExpression((self, fx) -> {
            return fx.max(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }

    @Override
    default DateTimeFilterTypeExpression<TProperty> min() {
        return createFilterChainExpression((self, fx) -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }

    default StringTypeExpression<String> format(String javaFormat) {
        return new StringTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.dateTimeFormat(x -> x.sqlFunc(sqlFunction), javaFormat);
            } else {
                return fx.dateTimeFormat(this.getValue(), javaFormat);
            }
        }, String.class);
    }

    default DateTimeTypeExpression<TProperty> plus(long duration, TimeUnitEnum timeUnit) {
        return new DateTimeTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.plusDateTime2(selector -> {
                PropTypeColumn.columnFuncSelector(selector, this);
                selector.value(duration);
            }, timeUnit);
        }, getPropertyType());
    }

    default <T extends Number> DateTimeTypeExpression<TProperty> plus(PropTypeColumn<T> durationColumn, TimeUnitEnum timeUnit) {
        return new DateTimeTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.plusDateTime2(selector -> {
                PropTypeColumn.columnFuncSelector(selector, this);
                PropTypeColumn.columnFuncSelector(selector, durationColumn);
            }, timeUnit);
        }, getPropertyType());
    }

    /**
     * 增加天
     *
     * @param day
     * @return
     */
    default DateTimeTypeExpression<TProperty> plusDays(long day) {
        return plus(day, TimeUnitEnum.DAYS);
    }

    /**
     * 增加天
     *
     * @param day
     * @return
     */
    default <T extends Number> DateTimeTypeExpression<TProperty> plusDays(PropTypeColumn<T> day) {
        return plus(day, TimeUnitEnum.DAYS);
    }

    /**
     * 增加月
     *
     * @param month
     * @return
     */
    default DateTimeTypeExpression<TProperty> plusMonths(long month) {
        return plus(month, TimeUnitEnum.MONTHS);
    }

    /**
     * 增加月
     *
     * @param month
     * @return
     */
    default <T extends Number> DateTimeTypeExpression<TProperty> plusMonths(PropTypeColumn<T> month) {
        return plus(month, TimeUnitEnum.MONTHS);
    }

    /**
     * 增加年
     *
     * @param year
     * @return
     */
    default DateTimeTypeExpression<TProperty> plusYears(long year) {
        return plus(year, TimeUnitEnum.YEARS);
    }

    /**
     * 增加年
     *
     * @param year
     * @return
     */
    default <T extends Number> DateTimeTypeExpression<TProperty> plusYears(PropTypeColumn<T> year) {
        return plus(year, TimeUnitEnum.YEARS);
    }

    /**
     * 增加秒
     *
     * @param seconds
     * @return
     */
    default DateTimeTypeExpression<TProperty> plusSeconds(long seconds) {
        return plus(seconds, TimeUnitEnum.SECONDS);
    }

    /**
     * 增加秒
     *
     * @param seconds
     * @return
     */
    default <T extends Number> DateTimeTypeExpression<TProperty> plusSeconds(PropTypeColumn<T> seconds) {
        return plus(seconds, TimeUnitEnum.SECONDS);
    }

    /**
     * 增加分钟
     *
     * @param minutes
     * @return
     */
    default DateTimeTypeExpression<TProperty> plusMinutes(long minutes) {
        return plus(minutes, TimeUnitEnum.MINUTES);
    }

    /**
     * 增加分钟
     *
     * @param minutes
     * @return
     */
    default <T extends Number> DateTimeTypeExpression<TProperty> plusMinutes(PropTypeColumn<T> minutes) {
        return plus(minutes, TimeUnitEnum.MINUTES);
    }

    /**
     * 增加小时
     *
     * @param hours
     * @return
     */
    default DateTimeTypeExpression<TProperty> plusHours(long hours) {
        return plus(hours, TimeUnitEnum.HOURS);
    }

    /**
     * 增加小时
     *
     * @param hours
     * @return
     */
    default <T extends Number> DateTimeTypeExpression<TProperty> plusHours(PropTypeColumn<T> hours) {
        return plus(hours, TimeUnitEnum.HOURS);
    }

    /**
     * 天在一年中的时间
     *
     * @return
     */
    default NumberTypeExpression<Integer> dayOfYear() {
        return dateTimeProp(DateTimeUnitEnum.DayOfYear);
    }

    /**
     * 星期0-6为0-6星期日为0
     *
     * @return 返回指定日期
     */
    default NumberTypeExpression<Integer> dayOfWeek() {
        return dateTimeProp(DateTimeUnitEnum.DayOfWeek);
    }

    /**
     * 星期1-7为1-7星期日为7
     *
     * @return 返回指定日期
     */
    default NumberTypeExpression<Integer> dayOfWeekSunDayIsLastDay() {
        return dateTimeProp(DateTimeUnitEnum.DayOfWeekSunDayLastDay);
    }

    /**
     * yyyy 指定时间的年份
     *
     * @return 返回当前年份
     */
    default NumberTypeExpression<Integer> year() {
        return dateTimeProp(DateTimeUnitEnum.Year);
    }

    /**
     * MM 1-12
     *
     * @return 返回1到12表示月份
     */
    default NumberTypeExpression<Integer> month() {
        return dateTimeProp(DateTimeUnitEnum.Month);
    }

    /**
     * dd 1-31
     *
     * @return 返回1-31表示在月份中的天数
     */
    default NumberTypeExpression<Integer> day() {
        return dateTimeProp(DateTimeUnitEnum.Day);
    }

    /**
     * HH 24小时制0-23
     *
     * @return 返回0-23表示小时
     */
    default NumberTypeExpression<Integer> hour() {
        return dateTimeProp(DateTimeUnitEnum.Hour);
    }

    /**
     * mm 0-59
     *
     * @return
     */
    default NumberTypeExpression<Integer> minute() {
        return dateTimeProp(DateTimeUnitEnum.Minute);
    }

    /**
     * ss 0-59
     *
     * @return
     */
    default NumberTypeExpression<Integer> second() {
        return dateTimeProp(DateTimeUnitEnum.Second);
    }
    /**
     * 通用返回时间属性的方法
     *
     * @param dateTimeUnitEnum 时间枚举
     * @return 返回时间属性的表达式
     */
    default NumberTypeExpression<Integer> dateTimeProp(DateTimeUnitEnum dateTimeUnitEnum) {
        PropColumn propColumn = this;
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (propColumn instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) propColumn).func().apply(fx);
                return fx.dateTimeProperty(sqlFunction, dateTimeUnitEnum);
            } else {
                return fx.dateTimeProperty(propColumn.getValue(), dateTimeUnitEnum);
            }
        }, Integer.class);
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
        return new DurationExpression(this, after);
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
        return new DurationExpression(this, Expression.of(this.getEntitySQLContext()).constant(after));
    }

    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationExpression duration(LocalDate after) {
        return new DurationExpression(this, Expression.of(this.getEntitySQLContext()).constant(after));
    }

    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationExpression duration(Date after) {
        return new DurationExpression(this, Expression.of(this.getEntitySQLContext()).constant(after));
    }



    default <T extends Temporal> void isBefore(T time) {
        ValueFilter valueFilter = this.getCurrentEntitySQLContext().getExpressionContext().getValueFilter();
        if(valueFilter.accept(this.getTable(),this.getValue(),time)){
            BooleanTypeExpression<Boolean> isAfterValue = dateTimeCompareExpression(time, this, this.getCurrentEntitySQLContext(), false);
            Expression.of(this.getCurrentEntitySQLContext()).rawSQLCommand("{0}",isAfterValue);
        }
    }

    default <T> void isBefore(ColumnDateTimeFunctionAvailable<T> time) {
        BooleanTypeExpression<Boolean> isAfterValue = dateTimeCompareExpression(time, this, this.getCurrentEntitySQLContext(), false);
        Expression.of(this.getCurrentEntitySQLContext()).rawSQLCommand("{0}",isAfterValue);
    }
    default void isBefore(PropTypeColumn<Date> time) {
        BooleanTypeExpression<Boolean> isAfterValue = dateTimeCompareExpression(time, this, this.getCurrentEntitySQLContext(), false);
        Expression.of(this.getCurrentEntitySQLContext()).rawSQLCommand("{0}",isAfterValue);
    }

    default <T extends Temporal> void isAfter(T time) {
        ValueFilter valueFilter = this.getCurrentEntitySQLContext().getExpressionContext().getValueFilter();
        if(valueFilter.accept(this.getTable(),this.getValue(),time)){
            BooleanTypeExpression<Boolean> isAfterValue = dateTimeCompareExpression(time, this, this.getCurrentEntitySQLContext(), true);
            Expression.of(this.getCurrentEntitySQLContext()).rawSQLCommand("{0}",isAfterValue);
        }
    }
    default <T> void isAfter(ColumnDateTimeFunctionAvailable<T> time) {
        BooleanTypeExpression<Boolean> isAfterValue = dateTimeCompareExpression(time, this, this.getCurrentEntitySQLContext(), true);
        Expression.of(this.getCurrentEntitySQLContext()).rawSQLCommand("{0}",isAfterValue);
    }

    default void isAfter(Date time) {
        BooleanTypeExpression<Boolean> isAfterValue = dateTimeCompareExpression(time, this, this.getCurrentEntitySQLContext(), true);
        Expression.of(this.getCurrentEntitySQLContext()).rawSQLCommand("{0}",isAfterValue);
    }

     static <TProp> BooleanTypeExpression<Boolean> dateTimeCompareExpression(TProp time, PropTypeColumn<?> propTypeColumn, EntitySQLContext entitySQLContext, boolean isAfter) {
         String operate = isAfter ? ">" : "<";
         return new BooleanTypeExpressionImpl<>(entitySQLContext, propTypeColumn.getTable(), propTypeColumn.getValue(), fx -> {
             if (propTypeColumn instanceof DSLSQLFunctionAvailable) {
                 SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) propTypeColumn).func().apply(fx);
                 return fx.anySQLFunction("{0} "+operate+" {1}", s -> {
                     s.sqlFunc(sqlFunction);
                     PropTypeColumn.acceptAnyValue(s,time);
                 });
             } else {
                 return fx.anySQLFunction("{0} "+operate+" {1}", s -> {
                     s.column(propTypeColumn.getValue());
                     PropTypeColumn.acceptAnyValue(s,time);
                 });
             }
         }, Boolean.class);
    }


    @Override
    default DateTimeTypeExpression<TProperty> createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new DateTimeTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    @Override
    default DateTimeFilterTypeExpression<TProperty> createFilterChainExpression(SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        return new DateTimeFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), func, propType);
    }
}
