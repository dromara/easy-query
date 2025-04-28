package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.duration.DurationAnyExpression;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyStringUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAnyFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionCompareComparableAnyChainExpression<TProperty>>, SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty>,
        ColumnJsonMapFunctionAvailable<TProperty> {

    @Override
    default ColumnFunctionCompareComparableAnyChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }

    default ColumnFunctionCompareComparableAnyChainExpression<TProperty> concat(TablePropColumn... propColumns) {
        return concat(o -> {
            for (TablePropColumn propColumn : propColumns) {
                o.getColumnFuncSelector().column(propColumn.getTable(), propColumn.getValue());
            }
        });
    }

    default ColumnFunctionCompareComparableAnyChainExpression<TProperty> concat(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.concat(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            } else {
                return fx.concat(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<TProperty> nullEmpty() {
        return nullOrDefault(o -> o.value(EasyStringUtil.EMPTY));
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> toLower() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toLower(sqlFunction);
            } else {
                return fx.toLower(this.getValue());
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> toUpper() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toUpper(sqlFunction);
            } else {
                return fx.toUpper(this.getValue());
            }
        }, String.class);
    }

    /**
     * 字符串截取
     *
     * @param begin  开始索引默认0
     * @param length 截取长度
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<String> subString(int begin, int length) {
        if (begin < 0) {
            throw new IllegalArgumentException("begin must be greater than 0");
        }
        if (length < 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.subString(sqlFunction, begin, length);
            } else {
                return fx.subString(this.getValue(), begin, length);
            }
        }, String.class);
    }

    /**
     * 去空格
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<String> trim() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trim(sqlFunction);
            } else {
                return fx.trim(this.getValue());
            }
        }, String.class);
    }

    /**
     * 去头部空格请使用{@link #ltrim()}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableAnyChainExpression<String> trimStart() {
        return ltrim();
    }
    default ColumnFunctionCompareComparableAnyChainExpression<String> ltrim() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimStart(sqlFunction);
            } else {
                return fx.trimStart(this.getValue());
            }
        }, String.class);
    }

    /**
     * 去尾部空格
     * 请使用{@link #rtrim()}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableAnyChainExpression<String> trimEnd() {
        return rtrim();
    }
    /**
     * 去尾部空格
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<String> rtrim() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimEnd(sqlFunction);
            } else {
                return fx.trimEnd(this.getValue());
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> replace(String oldValue, String newValue) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.replace(sqlFunction, oldValue, newValue);
            } else {
                return fx.replace(this.getValue(), oldValue, newValue);
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> leftPad(int totalWidth) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction, totalWidth);
            } else {
                return fx.leftPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> leftPad(int totalWidth, char paddingChar) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction, totalWidth, paddingChar);
            } else {
                return fx.leftPad(this.getValue(), totalWidth, paddingChar);
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> rightPad(int totalWidth) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth);
            } else {
                return fx.rightPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> rightPad(int totalWidth, char paddingChar) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth, paddingChar);
            } else {
                return fx.rightPad(this.getValue(), totalWidth, paddingChar);
            }
        }, String.class);
    }

    /**
     * 请使用{@link #joining(String)} 或{@link #joining(String, boolean)}
     * @param delimiter
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableAnyChainExpression<String> join(String delimiter) {
        return joining(delimiter);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> joining(String delimiter) {
        return joining(delimiter,false);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<String> joining(String delimiter, boolean distinct) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.joining(x -> {
                x.value(delimiter);
                PropTypeColumn.columnFuncSelector(x, this);
            }, distinct);
        }, String.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<Integer> length() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.length(sqlFunction);
            } else {
                return fx.length(this.getValue());
            }
        }, Integer.class);
    }

    default <T extends BigDecimal> ColumnFunctionCompareComparableAnyChainExpression<T> avg() {
        return avg(false);
    }

    default <T extends BigDecimal> ColumnFunctionCompareComparableAnyChainExpression<T> avg(boolean distinct) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.avg(sqlFunction).distinct(distinct);
            } else {
                return fx.avg(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }

    default <T extends Number> ColumnFunctionCompareComparableAnyChainExpression<T> sum() {
        return sum(false);
    }

    default <T extends Number> ColumnFunctionCompareComparableAnyChainExpression<T> sum(boolean distinct) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.sum(sqlFunction).distinct(distinct);
            } else {
                return fx.sum(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<TProperty> abs() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Abs);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Abs);
            }
        }, getPropertyType());
    }

    default ColumnFunctionCompareComparableAnyChainExpression<Integer> sign() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sin);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Sin);
            }
        }, Integer.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> floor() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Floor);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Floor);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> ceiling() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Ceiling);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Ceiling);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> round() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Round);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Round);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> round(int decimals) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(decimals);
                }, MathMethodEnum.Ceiling);
            } else {
                return fx.math(o -> o.column(this.getValue()).value(decimals), MathMethodEnum.Ceiling);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> exp() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Exp);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Exp);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> log() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Log);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Log);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> log10() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Log10);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Log10);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> pow() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Pow);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Pow);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> pow(BigDecimal exponent) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(exponent);
                }, MathMethodEnum.Pow);
            } else {
                return fx.math(o -> o.column(this.getValue()).value(exponent), MathMethodEnum.Pow);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> sqrt() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sqrt);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Sqrt);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> cos() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Cos);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Cos);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> sin() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sin);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Sin);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> tan() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Tan);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Tan);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> acos() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Acos);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Acos);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> asin() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Asin);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Asin);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> atan() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Atan);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Atan);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> atan2() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Atan2);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Atan2);
            }
        }, BigDecimal.class);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<BigDecimal> truncate() {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Truncate);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Truncate);
            }
        }, BigDecimal.class);
    }


    default ColumnFunctionCompareComparableAnyChainExpression<String> dateTimeFormat(String javaFormat) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.dateTimeFormat(this.getValue(), javaFormat);
        }, String.class);
    }

    /**
     * 最小精度为秒部分数据库支持秒以下精度
     *
     * @param duration
     * @param timeUnit
     * @return
     */
    default ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> plusTimeUnit(long duration, TimeUnit timeUnit) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTime(sqlFunction, duration, timeUnit);
            } else {
                return fx.plusDateTime(this.getValue(), duration, timeUnit);
            }
        }, getPropertyType());
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> plusMonths(int month) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTimeMonths(sqlFunction, month);
            } else {
                return fx.plusDateTimeMonths(this.getValue(), month);
            }
        }, getPropertyType());
    }

    default ColumnFunctionCompareComparableDateTimeChainExpression<TProperty> plusYears(int year) {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTimeYears(sqlFunction, year);
            } else {
                return fx.plusDateTimeYears(this.getValue(), year);
            }
        }, getPropertyType());
    }

    default ColumnFunctionCompareComparableAnyChainExpression<Integer> dayOfYear() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.DayOfYear);
    }

    /**
     * 星期1-6为1-6星期日为0
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<Integer> dayOfWeek() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.DayOfWeek);
    }

    default ColumnFunctionCompareComparableAnyChainExpression<Integer> year() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Year);
    }

    /**
     * MM 1-12
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<Integer> month() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Month);
    }

    /**
     * 1-31
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<Integer> day() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Day);
    }

    /**
     * HH 24小时制0-23
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<Integer> hour() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Hour);
    }

    /**
     * mm 0-60
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<Integer> minute() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Minute);
    }

    /**
     * ss 0-60
     *
     * @return
     */
    default ColumnFunctionCompareComparableAnyChainExpression<Integer> second() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Second);
    }

    /**
     * 新版本和老版本结果相反更符合java的{@link Duration}的类
     * 新版本是a大于b则返回负数,如果a小于b则返回正数
     * 新版本是a大于b则返回负数,如果a小于b则返回正数
     * 新版本是a大于b则返回负数,如果a小于b则返回正数
     * 请使用单入参函数{@link #duration(ColumnDateTimeFunctionAvailable)}
     * 当前方法是a.duration(b,DateTimeDurationEnum.DAYS)
     * 如果a大于b则返回正数,如果a小于b则返回负数
     *
     * @param otherDateTime 被比较的时间
     * @param durationEnum  返回相差枚举比如天数
     * @return 如果为负数表示
     */
    @Deprecated
    default ColumnFunctionCompareComparableAnyChainExpression<Long> duration(ColumnDateTimeFunctionAvailable<TProperty> otherDateTime, DateTimeDurationEnum durationEnum) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                if (otherDateTime instanceof DSLSQLFunctionAvailable) {
                    DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) otherDateTime;
                    SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                    return fx.duration(sqlFunction, otherDateTimeFunction, durationEnum);
                } else {
                    return fx.duration(sqlFunction, otherDateTime, otherDateTime.getValue(), durationEnum);
                }
            } else {
                if (otherDateTime instanceof DSLSQLFunctionAvailable) {
                    DSLSQLFunctionAvailable otherFunction = (DSLSQLFunctionAvailable) otherDateTime;
                    SQLFunction otherDateTimeFunction = otherFunction.func().apply(fx);
                    return fx.duration(this.getValue(), otherDateTimeFunction, durationEnum);
                } else {
                    return fx.duration(this.getValue(), otherDateTime, otherDateTime.getValue(), durationEnum);
                }
            }
        }, Long.class);
    }

    /**
     * 新版本和老版本结果相反更符合java的{@link Duration}的类
     * 新版本是a大于b则返回负数,如果a小于b则返回正数
     * 新版本是a大于b则返回负数,如果a小于b则返回正数
     * 新版本是a大于b则返回负数,如果a小于b则返回正数
     * 请使用单入参函数{@link #duration(LocalDateTime)}
     * 当前方法是a.duration(b,DateTimeDurationEnum.DAYS)
     * 如果a大于b则返回正数,如果a小于b则返回负数
     *
     * @param otherDateTime 被比较的时间
     * @param durationEnum  返回相差枚举比如天数
     * @return 如果为负数表示
     */
    @Deprecated
    default ColumnFunctionCompareComparableAnyChainExpression<Long> duration(LocalDateTime otherDateTime, DateTimeDurationEnum durationEnum) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.duration(sqlFunction, otherDateTime, durationEnum);
            } else {
                return fx.duration(this.getValue(), otherDateTime, durationEnum);
            }
        }, Long.class);
    }


    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationAnyExpression duration(ColumnDateTimeFunctionAvailable<TProperty> after) {
        return new DurationAnyExpression(this, after);
    }

    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationAnyExpression duration(LocalDateTime after) {
        return new DurationAnyExpression(this, after);
    }


    static ColumnFunctionCompareComparableAnyChainExpression<Integer> dateTimeProp(PropColumn propColumn, EntitySQLContext entitySQLContext, TableAvailable table, String property, DateTimeUnitEnum dateTimeUnitEnum) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(entitySQLContext, table, property, fx -> {
            if (propColumn instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) propColumn).func().apply(fx);
                return fx.dateTimeProperty(sqlFunction, dateTimeUnitEnum);
            } else {
                return fx.dateTimeProperty(propColumn.getValue(), dateTimeUnitEnum);
            }
        }, Integer.class);
    }
}
