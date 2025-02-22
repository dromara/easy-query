package com.easy.query.core.proxy.impl.duration;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.extension.functions.ColumnAnyFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDateTime;


/**
 * create time 2025/2/21 19:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DurationAnyExpression {
    private final ColumnAnyFunctionAvailable<?> before;
    private LocalDateTime afterConstant;
    private ColumnDateTimeFunctionAvailable<?> afterColumn;

    public DurationAnyExpression(ColumnAnyFunctionAvailable<?> before, LocalDateTime afterConstant) {
        this.before = before;
        this.afterConstant = afterConstant;
    }

    public DurationAnyExpression(ColumnAnyFunctionAvailable<?> before, ColumnDateTimeFunctionAvailable<?> afterColumn) {
        this.before = before;
        this.afterColumn = afterColumn;
    }

    private ColumnFunctionCompareComparableAnyChainExpression<Long> duration(DateTimeDurationEnum durationEnum) {
        if (afterConstant != null) {
            return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(before.getEntitySQLContext(), before.getTable(), before.getValue(), fx -> {
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

            return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(before.getEntitySQLContext(), before.getTable(), before.getValue(), fx -> {
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
                            s.column(before,before.getValue()).sqlFunc(otherDateTimeFunction);
                        }, durationEnum);
                    } else {
                        return fx.duration2(s -> {
                            s.column(before,before.getValue()).column(afterColumn, afterColumn.getValue());
                        }, durationEnum);
                    }
                }
            }, Long.class);
        } else {
            throw new EasyQueryInvalidOperationException("duration error,after constant and after column all null");
        }
    }

    public ColumnFunctionCompareComparableAnyChainExpression<Long> toDays() {
        return duration(DateTimeDurationEnum.Days);
    }

    public ColumnFunctionCompareComparableAnyChainExpression<Long> toHours() {
        return duration(DateTimeDurationEnum.Hours);
    }

    public ColumnFunctionCompareComparableAnyChainExpression<Long> toMinutes() {
        return duration(DateTimeDurationEnum.Minutes);
    }

    public ColumnFunctionCompareComparableAnyChainExpression<Long> toSeconds() {
        return duration(DateTimeDurationEnum.Seconds);
    }

    public ColumnFunctionCompareComparableAnyChainExpression<Long> toValues(DateTimeDurationEnum durationEnum) {
        return duration(durationEnum);
    }
}
