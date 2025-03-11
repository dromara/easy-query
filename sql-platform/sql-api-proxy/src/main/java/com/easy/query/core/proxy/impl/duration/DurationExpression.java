package com.easy.query.core.proxy.impl.duration;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.PropTypeColumn;
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
                return fx.duration2(s -> {
                    PropTypeColumn.columnFuncSelector(s,before);
                    s.value(afterConstant);
                }, durationEnum);
            }, Long.class);
        } else if (afterColumn != null) {

            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(before.getEntitySQLContext(), before.getTable(), before.getValue(), fx -> {
                return fx.duration2(s -> {
                    PropTypeColumn.columnFuncSelector(s,before);
                    PropTypeColumn.columnFuncSelector(s,afterColumn);
                }, durationEnum);
            }, Long.class);
        } else {
            throw new EasyQueryInvalidOperationException("duration error,after constant and after column all null");
        }
    }

    public ColumnFunctionCompareComparableNumberChainExpression<Long> toDays() {
        return duration(DateTimeDurationEnum.Days);
    }

    public ColumnFunctionCompareComparableNumberChainExpression<Long> toHours() {
        return duration(DateTimeDurationEnum.Hours);
    }

    public ColumnFunctionCompareComparableNumberChainExpression<Long> toMinutes() {
        return duration(DateTimeDurationEnum.Minutes);
    }

    public ColumnFunctionCompareComparableNumberChainExpression<Long> toSeconds() {
        return duration(DateTimeDurationEnum.Seconds);
    }

    public ColumnFunctionCompareComparableNumberChainExpression<Long> toValues(DateTimeDurationEnum durationEnum) {
        return duration(durationEnum);
    }
}
