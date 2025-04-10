package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.func.def.enums.NumberCalcEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.ColumnFunctionCompareComparableNumberFilterChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.impl.ColumnFunctionCompareComparableNumberSumFilterChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.filter.impl.ColumnFunctionCompareComparableNumberFilterChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnNumberFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionCompareComparableNumberChainExpression<TProperty>>,
        ColumnAggregateFilterFunctionAvailable<TProperty, ColumnFunctionCompareComparableNumberFilterChainExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty> {

    @Override
    default ColumnFunctionCompareComparableNumberFilterChainExpression<TProperty> max() {
        return createFilterChainExpression(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.max(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }

    @Override
    default ColumnFunctionCompareComparableNumberFilterChainExpression<TProperty> min() {
        return createFilterChainExpression(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }


    /**
     * 计算平均值返回 BigDecimal
     *
     * @return 计算平均值返回 AVG(age)
     */
    default ColumnFunctionCompareComparableNumberFilterChainExpression<BigDecimal> avg() {
        return avg(false);
    }

    /**
     * 计算去重后的平均值返回 BigDecimal
     *
     * @param distinct 是否去重
     * @return 计算平均值返回 AVG(DISTINCT age)
     */
    default ColumnFunctionCompareComparableNumberFilterChainExpression<BigDecimal> avg(boolean distinct) {
        return new ColumnFunctionCompareComparableNumberFilterChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.avg(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, BigDecimal.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param <T> 任意数字类型
     * @return 计算求和 SUM(age)
     */
    default <T extends Number> ColumnFunctionCompareComparableNumberFilterChainExpression<T> sum() {
        return sum(false);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param <T> 任意数字类型
     * @return 计算求和 SUM(age)
     */
    default <T extends Number> ColumnFunctionCompareComparableNumberFilterChainExpression<T> sum(Class<T> resultClass) {
        return sum(false).asAnyType(resultClass);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @return 计算求和 SUM(age)
     */
    default ColumnFunctionCompareComparableNumberFilterChainExpression<Integer> sumInt() {
        return sum(false).asAnyType(Integer.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param distinct 是否去重
     * @return 计算求和 SUM(age)
     */
    default ColumnFunctionCompareComparableNumberFilterChainExpression<Integer> sumInt(boolean distinct) {
        return sum(distinct).asAnyType(Integer.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @return 计算求和 SUM(age)
     */
    default ColumnFunctionCompareComparableNumberFilterChainExpression<Long> sumLong() {
        return sum(false).asAnyType(Long.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param distinct 是否去重
     * @return 计算求和 SUM(age)
     */
    default ColumnFunctionCompareComparableNumberFilterChainExpression<Long> sumLong(boolean distinct) {
        return sum(distinct).asAnyType(Long.class);
    }

    /**
     * 计算去重求和 SUM(DISTINCT age)
     *
     * @param distinct 是否去重
     * @param <T>      任意数字类型
     * @return 计算去重求和 SUM(DISTINCT age)
     */
    default <T extends Number> ColumnFunctionCompareComparableNumberFilterChainExpression<T> sum(boolean distinct) {
        return new ColumnFunctionCompareComparableNumberSumFilterChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, getPropertyType());
    }

    /**
     * 计算去重求和 SUM(DISTINCT age)
     *
     * @param distinct 是否去重
     * @param <T>      任意数字类型
     * @return 计算去重求和 SUM(DISTINCT age)
     */
    default <T extends Number> ColumnFunctionCompareComparableNumberFilterChainExpression<T> sum(boolean distinct, Class<T> resultClass) {
        return new ColumnFunctionCompareComparableNumberSumFilterChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, getPropertyType()).asAnyType(resultClass);
    }

    default ColumnFunctionCompareComparableNumberFilterChainExpression<BigDecimal> sumBigDecimal() {
        return sum(false);
    }

    default ColumnFunctionCompareComparableNumberFilterChainExpression<BigDecimal> sumBigDecimal(boolean distinct) {
        return new ColumnFunctionCompareComparableNumberSumFilterChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, BigDecimal.class);
    }

    /**
     * 计算绝对值
     * 请使用{@link SQLMathExpression}
     *
     * @return 计算绝对值
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<TProperty> abs() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
//            if (this instanceof DSLSQLFunctionAvailable) {
//                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
//                return fx.math(o -> {
//                    o.sqlFunc(sqlFunction);
//                }, MathMethodEnum.Abs);
//            } else {
//                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Abs);
//            }
            return fx.math(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
            }, MathMethodEnum.Abs);
        }, getPropertyType());
    }

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<Integer> sign() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> floor() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> ceiling() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> round() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> round(int decimals) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> exp() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> log() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> log(BigDecimal newBase) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(newBase);
                }, MathMethodEnum.Log);
            } else {
                return fx.math(o -> o.column(this.getValue()).value(newBase), MathMethodEnum.Log);
            }
        }, BigDecimal.class);
    }

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> log10() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> pow() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> pow(BigDecimal exponent) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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


    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sqrt() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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


    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> cos() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sin() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> tan() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> acos() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> asin() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> atan() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> atan2() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 请使用{@link SQLMathExpression}
     *
     * @return
     */
    @Deprecated
    default ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> truncate() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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

    /**
     * 加法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> add(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_ADD);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> add(TOtherProperty constant) {
        return add(Expression.of(getEntitySQLContext()).constant(constant));
    }

    /**
     * 减法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> subtract(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_SUBTRACT);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> subtract(TOtherProperty constant) {
        return subtract(Expression.of(getEntitySQLContext()).constant(constant));
    }

    /**
     * 乘法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> multiply(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_MULTIPLY);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> multiply(TOtherProperty constant) {
        return multiply(Expression.of(getEntitySQLContext()).constant(constant));
    }

    /**
     * 除法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> divide(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_DIVIDE);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> divide(TOtherProperty constant) {
        return divide(Expression.of(getEntitySQLContext()).constant(constant));
    }

    @Override
    default ColumnFunctionCompareComparableNumberChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }

    @Override
    default ColumnFunctionCompareComparableNumberFilterChainExpression<TProperty> createFilterChainExpression(EntitySQLContext entitySQLContext, PropTypeColumn<?> self, TableAvailable table, String property, SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableNumberFilterChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), func, getPropertyType());
    }
}
