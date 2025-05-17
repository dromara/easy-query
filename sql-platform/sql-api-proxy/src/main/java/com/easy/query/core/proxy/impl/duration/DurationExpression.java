package com.easy.query.core.proxy.impl.duration;

import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.NumberTypeExpressionImpl;


/**
 * create time 2025/2/21 19:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DurationExpression {
    private final ColumnDateTimeFunctionAvailable<?> before;
    private ColumnDateTimeFunctionAvailable<?> afterColumn;

    public DurationExpression(ColumnDateTimeFunctionAvailable<?> before, ColumnDateTimeFunctionAvailable<?> afterColumn) {
        this.before = before;
        this.afterColumn = afterColumn;
    }

    private NumberTypeExpression<Long> duration(DateTimeDurationEnum durationEnum) {

        return new NumberTypeExpressionImpl<>(before.getEntitySQLContext(), before.getTable(), before.getValue(), fx -> {
            return fx.duration2(s -> {
                PropTypeColumn.columnFuncSelector(s,before);
                PropTypeColumn.columnFuncSelector(s,afterColumn);
            }, durationEnum);
        }, Long.class);
    }

    public NumberTypeExpression<Long> toDays() {
        return duration(DateTimeDurationEnum.Days);
    }

    public NumberTypeExpression<Long> toHours() {
        return duration(DateTimeDurationEnum.Hours);
    }

    public NumberTypeExpression<Long> toMinutes() {
        return duration(DateTimeDurationEnum.Minutes);
    }

    public NumberTypeExpression<Long> toSeconds() {
        return duration(DateTimeDurationEnum.Seconds);
    }

    public NumberTypeExpression<Long> toValues(DateTimeDurationEnum durationEnum) {
        return duration(durationEnum);
    }
}
