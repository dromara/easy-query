package com.easy.query.core.proxy.extension.functions;

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
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnNumberFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionComparableNumberChainExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty> {
    /**
     * 计算平均值返回 BigDecimal
     *
     * @return 计算平均值返回 AVG(age)
     */
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> avg() {
        return avg(false);
    }

    /**
     * 计算去重后的平均值返回 BigDecimal
     *
     * @param distinct 是否去重
     * @return 计算平均值返回 AVG(DISTINCT age)
     */
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.avg(sqlFunction).distinct(distinct);
            } else {
                return fx.avg(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param <T> 任意数字类型
     * @return 计算求和 SUM(age)
     */
    default <T extends Number> ColumnFunctionComparableNumberChainExpression<T> sum() {
        return sum(false);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param <T> 任意数字类型
     * @return 计算求和 SUM(age)
     */
    default <T extends Number> ColumnFunctionComparableNumberChainExpression<T> sum(Class<T> resultClass) {
        return sum(false).asAnyType(resultClass);
    }

    /**
     * 计算去重求和 SUM(DISTINCT age)
     *
     * @param distinct 是否去重
     * @param <T>      任意数字类型
     * @return 计算去重求和 SUM(DISTINCT age)
     */
    default <T extends Number> ColumnFunctionComparableNumberChainExpression<T> sum(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, this);
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
    default <T extends Number> ColumnFunctionComparableNumberChainExpression<T> sum(boolean distinct, Class<T> resultClass) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, this);
            }).distinct(distinct);
        }, getPropertyType()).asAnyType(resultClass);
    }

    default ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal() {
        return sum(false);
    }

    default ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
//            if (this instanceof DSLSQLFunctionAvailable) {
//                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
//                return fx.sum(sqlFunction).distinct(distinct);
//            } else {
//                return fx.sum(this.getValue()).distinct(distinct);
//            }
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, this);
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
    default ColumnFunctionComparableNumberChainExpression<TProperty> abs() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<Integer> sign() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> floor() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> ceiling() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> round() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> round(int decimals) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> exp() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> log() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> log(BigDecimal newBase) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> log10() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> pow() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> pow(BigDecimal exponent) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> sqrt() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> cos() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> sin() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> tan() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> acos() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> asin() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> atan() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> atan2() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> truncate() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> add(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_ADD);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> add(TOtherProperty constant) {
        return add(Expression.of(getEntitySQLContext()).constant().valueOf(constant));
    }

    /**
     * 减法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> subtract(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_SUBTRACT);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> subtract(TOtherProperty constant) {
        return subtract(Expression.of(getEntitySQLContext()).constant().valueOf(constant));
    }

    /**
     * 乘法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> multiply(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_MULTIPLY);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> multiply(TOtherProperty constant) {
        return multiply(Expression.of(getEntitySQLContext()).constant().valueOf(constant));
    }

    /**
     * 除法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> devide(PropTypeColumn<TOtherProperty> other) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                PropTypeColumn.columnFuncSelector(o, other);
            }, NumberCalcEnum.NUMBER_DEVIDE);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> ColumnFunctionComparableNumberChainExpression<BigDecimal> devide(TOtherProperty constant) {
        return devide(Expression.of(getEntitySQLContext()).constant().valueOf(constant));
    }

    @Override
    default ColumnFunctionComparableNumberChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
