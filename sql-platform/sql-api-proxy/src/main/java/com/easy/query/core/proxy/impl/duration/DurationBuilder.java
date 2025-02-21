package com.easy.query.core.proxy.impl.duration;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnDateTimeFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDateTime;
import java.util.function.Function;


/**
 * create time 2025/2/21 19:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DurationBuilder {
    private LocalDateTime afterConstant;
    private ColumnDateTimeFunctionAvailable<?> afterColumn;
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;

    public DurationBuilder(LocalDateTime afterConstant, EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.afterConstant = afterConstant;
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
    }

    public DurationBuilder(ColumnDateTimeFunctionAvailable<?> afterColumn, EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.afterColumn = afterColumn;
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
    }

    private ColumnFunctionCompareComparableNumberChainExpression<Long> duration(DateTimeDurationEnum durationEnum) {
        if (afterConstant != null) {
            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.entitySQLContext, this.table, this.property, fx -> {
                if (this instanceof DSLSQLFunctionAvailable) {
                    SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                    return fx.duration(s -> {
                        s.value(afterConstant).sqlFunc(sqlFunction);
                    }, durationEnum);
                } else {
                    return fx.duration(s -> {
                        s.value(afterConstant)
                                .column(property);
                    }, durationEnum);
                }
            }, Long.class);
        } else if (afterColumn != null) {

            return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.entitySQLContext, this.table, this.property, fx -> {
                if (this instanceof DSLSQLFunctionAvailable) {
                    SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                    if (afterColumn instanceof DSLSQLFunctionAvailable) {
                        DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) afterColumn;
                        SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                        return fx.duration(s -> {
                            s.sqlFunc(otherDateTimeFunction).sqlFunc(sqlFunction);
                        }, durationEnum);
                    } else {
                        return fx.duration(s -> {
                            s.column(afterColumn, afterColumn.getValue())
                                    .sqlFunc(sqlFunction);
                        }, durationEnum);
                    }
                } else {
                    if (afterColumn instanceof DSLSQLFunctionAvailable) {
                        DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) afterColumn;
                        SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                        return fx.duration(s -> {
                            s.sqlFunc(otherDateTimeFunction).column(property);
                        }, durationEnum);
                    } else {
                        return fx.duration(s -> {
                            s.column(afterColumn, afterColumn.getValue())
                                    .column(property);
                        }, durationEnum);
                    }
                }
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
