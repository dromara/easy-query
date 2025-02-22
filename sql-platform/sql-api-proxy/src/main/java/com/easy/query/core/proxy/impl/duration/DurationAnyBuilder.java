package com.easy.query.core.proxy.impl.duration;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDateTime;


/**
 * create time 2025/2/21 19:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DurationAnyBuilder {
    private LocalDateTime afterConstant;
    private ColumnDateTimeFunctionAvailable<?> afterColumn;
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;

    public DurationAnyBuilder(LocalDateTime afterConstant, EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.afterConstant = afterConstant;
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
    }

    public DurationAnyBuilder(ColumnDateTimeFunctionAvailable<?> afterColumn, EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.afterColumn = afterColumn;
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
    }

    private ColumnFunctionCompareComparableAnyChainExpression<Long> duration(DateTimeDurationEnum durationEnum) {
        if (afterConstant != null) {
            return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.entitySQLContext, this.table, this.property, fx -> {
                return fx.duration(s -> {
                    s.value(afterConstant)
                            .column(this.table, property);
                }, durationEnum);
            }, Long.class);
        } else if (afterColumn != null) {

            return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.entitySQLContext, this.table, this.property, fx -> {
                if (afterColumn instanceof DSLSQLFunctionAvailable) {
                    DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) afterColumn;
                    SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                    return fx.duration(s -> {
                        s.sqlFunc(otherDateTimeFunction).column(this.table, property);
                    }, durationEnum);
                } else {
                    return fx.duration(s -> {
                        s.column(afterColumn, afterColumn.getValue())
                                .column(this.table, property);
                    }, durationEnum);
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

    public ColumnFunctionCompareComparableAnyChainExpression<Long> toDays() {
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
    public ColumnFunctionCompareComparableAnyChainExpression<Long> toHours() {
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
    public ColumnFunctionCompareComparableAnyChainExpression<Long> toMinutes() {
        return duration(DateTimeDurationEnum.Minutes);
    }

    /**
     * 返回两者相差秒数
     * a.duration(b)
     * a小于b则返回正数
     * a大于b则返回负数
     *
     * @return
     */
    public ColumnFunctionCompareComparableAnyChainExpression<Long> toSeconds() {
        return duration(DateTimeDurationEnum.Seconds);
    }

    /**
     * 返回两者相差值
     * a.duration(b)
     * a小于b则返回正数
     * a大于b则返回负数
     *
     * @param durationEnum 返回什么值
     * @return
     */
    public ColumnFunctionCompareComparableAnyChainExpression<Long> toValues(DateTimeDurationEnum durationEnum) {
        return duration(durationEnum);
    }
}
