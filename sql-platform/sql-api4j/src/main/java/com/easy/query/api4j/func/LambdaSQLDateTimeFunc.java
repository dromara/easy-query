package com.easy.query.api4j.func;

import com.easy.query.api4j.func.column.SQLColumnFuncSelector;
import com.easy.query.api4j.func.column.SQLColumnFuncSelectorImpl;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFuncAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * create time 2024/5/6 16:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LambdaSQLDateTimeFunc<T1> extends SQLFuncAvailable {
    /**
     * 时间格式添加函数
     *
     * @param property
     * @param duration
     * @param timeUnit
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTime(Property<T1, ?> property, long duration, TimeUnit timeUnit) {
        return plusDateTime(s -> {
            s.column(property);
        }, duration, timeUnit);
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlFunction
     * @param duration
     * @param timeUnit
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTime(SQLFunction sqlFunction, long duration, TimeUnit timeUnit) {
        return plusDateTime(s -> {
            s.sqlFunc(sqlFunction);
        }, duration, timeUnit);
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlExpression
     * @param duration
     * @param timeUnit
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTime(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression, long duration, TimeUnit timeUnit) {
        return getSQLFunc().plusDateTime(x -> {
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        }, duration, timeUnit);
    }

    /**
     * 时间格式添加函数
     *
     * @param property
     * @param month
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTimeMonths(Property<T1, ?> property, int month) {
        return plusDateTimeMonths(s -> {
            s.column(property).value(month);
        });
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlFunction
     * @param month
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTimeMonths(SQLFunction sqlFunction, int month) {
        return plusDateTimeMonths(s -> {
            s.sqlFunc(sqlFunction).value(month);
        });
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlExpression
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTimeMonths(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        return getSQLFunc().plusDateTimeMonths(x -> {
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }

    /**
     * 时间格式添加函数
     *
     * @param property
     * @param year
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTimeYears(Property<T1, ?> property, int year) {
        return plusDateTimeYears(s -> {
            s.column(property).value(year);
        });
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlFunction
     * @param year
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTimeYears(SQLFunction sqlFunction, int year) {
        return plusDateTimeMonths(s -> {
            s.sqlFunc(sqlFunction).value(year);
        });
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlExpression
     * @return 时间格式添加函数
     */
    default SQLFunction plusDateTimeYears(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        return getSQLFunc().plusDateTimeYears(x -> {
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }


    /**
     * 时间格式添加函数
     *
     * @param property
     * @param dateTimeUnitEnum
     * @return 时间格式添加函数
     */
    default SQLFunction dateTimeProperty(Property<T1, ?> property, DateTimeUnitEnum dateTimeUnitEnum) {
        return dateTimeProperty(s -> {
            s.column(property);
        }, dateTimeUnitEnum);
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlFunction
     * @param dateTimeUnitEnum
     * @return 时间格式添加函数
     */
    default SQLFunction dateTimeProperty(SQLFunction sqlFunction, DateTimeUnitEnum dateTimeUnitEnum) {
        return dateTimeProperty(s -> {
            s.sqlFunc(sqlFunction);
        }, dateTimeUnitEnum);
    }

    /**
     * 时间格式添加函数
     *
     * @param sqlExpression
     * @return 时间格式添加函数
     */
    default SQLFunction dateTimeProperty(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return getSQLFunc().dateTimeProperty(x -> {
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        }, dateTimeUnitEnum);
    }

    /**
     * 相差时间函数
     * 参数顺序大时间然后小时间则计算出来结果为正数
     *
     * @param property
     * @param otherDateTime
     * @param durationEnum
     * @return 相差时间函数
     */
    default SQLFunction duration(Property<T1, ?> property, LocalDateTime otherDateTime, DateTimeDurationEnum durationEnum) {
        return duration(s -> {
            s.column(property)
                    .value(otherDateTime);
        }, durationEnum);
    }

    /**
     * 相差时间函数
     * 参数顺序大时间然后小时间则计算出来结果为正数
     *
     * @param property
     * @param otherTable
     * @param otherProperty
     * @param durationEnum
     * @return 相差时间函数
     */
    default <T2> SQLFunction duration(Property<T1, ?> property, SQLTableOwner otherTable, Property<T2, ?> otherProperty, DateTimeDurationEnum durationEnum) {
        return duration(s -> {
            s.column(property)
                    .column(otherTable, otherProperty);
        }, durationEnum);
    }

    /**
     * 相差时间函数
     * 参数顺序大时间然后小时间则计算出来结果为正数
     *
     * @param property
     * @param sqlFunction
     * @param durationEnum
     * @return 相差时间函数
     */
    default SQLFunction duration(Property<T1, ?> property, SQLFunction sqlFunction, DateTimeDurationEnum durationEnum) {
        return duration(s -> {
            s.column(property).sqlFunc(sqlFunction);
        }, durationEnum);
    }

    /**
     * 相差时间函数
     * 参数顺序大时间然后小时间则计算出来结果为正数
     *
     * @param sqlFunction
     * @param otherDateTime
     * @param durationEnum
     * @return 相差时间函数
     */
    default SQLFunction duration(SQLFunction sqlFunction, LocalDateTime otherDateTime, DateTimeDurationEnum durationEnum) {
        return duration(s -> {
            s.sqlFunc(sqlFunction)
                    .value(otherDateTime);
        }, durationEnum);
    }

    /**
     * 相差时间函数
     * 参数顺序大时间然后小时间则计算出来结果为正数
     *
     * @param sqlFunction
     * @param otherTable
     * @param otherProperty
     * @param durationEnum
     * @return 相差时间函数
     */
    default <T2> SQLFunction duration(SQLFunction sqlFunction, SQLTableOwner otherTable, Property<T2, ?> otherProperty, DateTimeDurationEnum durationEnum) {
        return duration(s -> {
            s.sqlFunc(sqlFunction)
                    .column(otherTable, otherProperty);
        }, durationEnum);
    }

    /**
     * 相差时间函数
     * 参数顺序大时间然后小时间则计算出来结果为正数
     *
     * @param sqlFunction1
     * @param sqlFunction2
     * @param durationEnum
     * @return 相差时间函数
     */
    default SQLFunction duration(SQLFunction sqlFunction1, SQLFunction sqlFunction2, DateTimeDurationEnum durationEnum) {
        return duration(s -> {
            s.sqlFunc(sqlFunction1).sqlFunc(sqlFunction2);
        }, durationEnum);
    }

    /**
     * 相差时间函数
     * 参数顺序大时间然后小时间则计算出来结果为正数
     *
     * @param sqlExpression
     * @param durationEnum
     * @return 相差时间函数
     */
   default SQLFunction duration(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression, DateTimeDurationEnum durationEnum){
       return getSQLFunc().duration(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       },durationEnum);
   }
}
