package com.easy.query.core.proxy.impl.duration;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDateTime;


/**
 * create time 2025/2/21 19:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DurationExpression {
    private final ColumnDateTimeFunctionAvailable<?> before;
    private LocalDateTime afterConstant;
    private ColumnDateTimeFunctionAvailable<?> afterColumn;

    public DurationExpression(ColumnDateTimeFunctionAvailable<?> before, LocalDateTime afterConstant) {
        this.before = before;
        this.afterConstant = afterConstant;
    }

    public DurationExpression(ColumnDateTimeFunctionAvailable<?> before, ColumnDateTimeFunctionAvailable<?> afterColumn) {
        this.before = before;
        this.afterColumn = afterColumn;
    }

    private ColumnFunctionCompareComparableNumberChainExpression<Long> duration(DateTimeDurationEnum durationEnum) {
        if (afterConstant != null) {
            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(before.getEntitySQLContext(), before.getTable(), before.getValue(), fx -> {
                if (before instanceof DSLSQLFunctionAvailable) {
                    SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) before).func().apply(fx);
                    return fx.duration2(s -> {
                        s.sqlFunc(sqlFunction).value(afterConstant);
                    }, durationEnum);
                } else {
                    return fx.duration2(s -> {
                        s.column(before,before.getValue()).value(afterConstant);
                    }, durationEnum);
                }
            }, Long.class);
        } else if (afterColumn != null) {

            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(before.getEntitySQLContext(), before.getTable(), before.getValue(), fx -> {
                if (before instanceof DSLSQLFunctionAvailable) {
                    SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) before).func().apply(fx);
                    if (afterColumn instanceof DSLSQLFunctionAvailable) {
                        DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) afterColumn;
                        SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                        return fx.duration2(s -> {
                            s.sqlFunc(sqlFunction).sqlFunc(otherDateTimeFunction);
                        }, durationEnum);
                    } else {
                        return fx.duration2(s -> {
                            s.sqlFunc(sqlFunction).column(afterColumn, afterColumn.getValue());
                        }, durationEnum);
                    }
                } else {
                    if (afterColumn instanceof DSLSQLFunctionAvailable) {
                        DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) afterColumn;
                        SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                        return fx.duration2(s -> {
                            s.column(before, before.getValue()).sqlFunc(otherDateTimeFunction);
                        }, durationEnum);
                    } else {
                        return fx.duration2(s -> {
                            s.column(before, before.getValue()).column(afterColumn, afterColumn.getValue());
                        }, durationEnum);
                    }
                }
            }, Long.class);
        } else {
            throw new EasyQueryInvalidOperationException("duration error,after constant and after column all null");
        }
    }

    /**
     * 返回两者相差天数
     * a.duration(b)
     * a小于b则返回正数
     * a大于b则返回负数
     *
     * @return
     */

    public ColumnFunctionCompareComparableNumberChainExpression<Long> toDays() {
        return duration(DateTimeDurationEnum.Days);
    }

    /**
     * 返回两者相差小时数
     * a.duration(b)
     * a小于b则返回正数
     * a大于b则返回负数
     *
     * @return
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Long> toHours() {
        return duration(DateTimeDurationEnum.Hours);
    }

    /**
     * 返回两者相差分钟数
     * a.duration(b)
     * a小于b则返回正数
     * a大于b则返回负数
     *
     * @return
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Long> toMinutes() {
        return duration(DateTimeDurationEnum.Minutes);
    }

    /**
     * 返回两者相差秒数
     * a.duration(b)
     * a小于b则返回正数
     * a大于b则返回负数
     * @return
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Long> toSeconds() {
        return duration(DateTimeDurationEnum.Seconds);
    }

    /**
     * 返回两者相差值
     * a.duration(b)
     * a小于b则返回正数
     * a大于b则返回负数
     * @param durationEnum 返回什么值
     * @return
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Long> toValues(DateTimeDurationEnum durationEnum) {
        return duration(durationEnum);
    }
}
