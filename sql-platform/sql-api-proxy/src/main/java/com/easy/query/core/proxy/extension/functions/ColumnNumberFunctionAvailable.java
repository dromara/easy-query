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
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.NumberFilterTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.impl.NumberSumFilterTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.filter.impl.NumberFilterTypeExpressionImpl;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnNumberFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, NumberTypeExpression<TProperty>>,
        ColumnAggregateFilterFunctionAvailable<TProperty, NumberFilterTypeExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty> {


    default NumberFilterTypeExpression<TProperty> nullOrDefaultZero() {
        return createFilterChainExpression((self, fx) -> {
            return fx.nullOrDefault(x -> {
                PropTypeColumn.acceptAnyValue(x, self);
                x.format(0);
            });
        }, getPropertyType());
    }

    @Override
    default NumberFilterTypeExpression<TProperty> max() {
        return createFilterChainExpression((self, fx) -> {
            return fx.max(x -> {
                PropTypeColumn.acceptAnyValue(x, self);
            });
        }, getPropertyType());
    }

    @Override
    default NumberFilterTypeExpression<TProperty> min() {
        return createFilterChainExpression((self, fx) -> {
            return fx.min(x -> {
                PropTypeColumn.acceptAnyValue(x, self);
            });
        }, getPropertyType());
    }


    /**
     * 计算平均值返回 BigDecimal
     *
     * @return 计算平均值返回 AVG(age)
     */
    default NumberFilterTypeExpression<BigDecimal> avg() {
        return avg(false);
    }

    /**
     * 计算去重后的平均值返回 BigDecimal
     *
     * @param distinct 是否去重
     * @return 计算平均值返回 AVG(DISTINCT age)
     */
    default NumberFilterTypeExpression<BigDecimal> avg(boolean distinct) {
        return new NumberFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
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
    default <T extends Number> NumberFilterTypeExpression<T> sum() {
        return sum(false);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param <T> 任意数字类型
     * @return 计算求和 SUM(age)
     */
    default <T extends Number> NumberFilterTypeExpression<T> sum(Class<T> resultClass) {
        return sum(false).asAnyType(resultClass);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @return 计算求和 SUM(age)
     */
    default NumberFilterTypeExpression<Integer> sumInt() {
        return sum(false).asAnyType(Integer.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param distinct 是否去重
     * @return 计算求和 SUM(age)
     */
    default NumberFilterTypeExpression<Integer> sumInt(boolean distinct) {
        return sum(distinct).asAnyType(Integer.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @return 计算求和 SUM(age)
     */
    default NumberFilterTypeExpression<Long> sumLong() {
        return sum(false).asAnyType(Long.class);
    }

    /**
     * 计算求和 SUM(age)
     *
     * @param distinct 是否去重
     * @return 计算求和 SUM(age)
     */
    default NumberFilterTypeExpression<Long> sumLong(boolean distinct) {
        return sum(distinct).asAnyType(Long.class);
    }

    /**
     * 计算去重求和 SUM(DISTINCT age)
     *
     * @param distinct 是否去重
     * @param <T>      任意数字类型
     * @return 计算去重求和 SUM(DISTINCT age)
     */
    default <T extends Number> NumberFilterTypeExpression<T> sum(boolean distinct) {
        return new NumberSumFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
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
    default <T extends Number> NumberFilterTypeExpression<T> sum(boolean distinct, Class<T> resultClass) {
        return new NumberSumFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, getPropertyType()).asAnyType(resultClass);
    }

    default NumberFilterTypeExpression<BigDecimal> sumBigDecimal() {
        return sum(false);
    }

    default NumberFilterTypeExpression<BigDecimal> sumBigDecimal(boolean distinct) {
        return new NumberSumFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, BigDecimal.class);
    }

    /**
     * 计算绝对值
     * 或使用{@link SQLMathExpression}
     *
     * @return 计算绝对值
     */
    default NumberTypeExpression<TProperty> abs() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Abs);
        }, getPropertyType());
    }

    /**
     * 是一种数学类方法，该方法返回一个整数，该整数指定数字的符号
     * 它的作用就是返回 数值的符号：
     * •	1.0 → 如果参数大于 0
     * •	0.0 → 如果参数等于 0
     * •	-1.0 → 如果参数小于 0
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<Integer> signum() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Signum);

        }, Integer.class);
    }

    /**
     * 向下取整
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> floor() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Floor);
        }, BigDecimal.class);
    }

    /**
     * 向上取整
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> ceiling() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Ceiling);
        }, BigDecimal.class);
    }

    /**
     * 四舍五入
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> round() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Round);
        }, BigDecimal.class);
    }

    /**
     * 四舍五入
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> round(int decimals) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                o.value(decimals);
            }, MathMethodEnum.Round);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> exp() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Exp);
        }, BigDecimal.class);
    }

    /**
     * 要查找其对数的数字
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> log() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Log);
        }, BigDecimal.class);
    }

    /**
     * 要查找其对数的数字 以10为低
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> log10() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Log10);
        }, BigDecimal.class);
    }

//    /**
//     * 或使用{@link SQLMathExpression}
//     *
//     * @return
//     */
//    default NumberTypeExpression<BigDecimal> pow() {
//        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
//            return fx.math(o -> {
//                PropTypeColumn.acceptAnyValue(o, this);
//            }, MathMethodEnum.Pow);
//        }, BigDecimal.class);
//    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> pow(BigDecimal exponent) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                o.value(exponent);
            }, MathMethodEnum.Pow);
        }, BigDecimal.class);
    }


    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> sqrt() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Sqrt);
        }, BigDecimal.class);
    }


    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> cos() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Cos);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> sin() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Sin);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> tan() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Tan);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> acos() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Acos);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> asin() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Asin);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> atan() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Atan);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> atan2(BigDecimal other) {
        return atan2(Expression.of(this.getEntitySQLContext()).constant(other));
    }

    default NumberTypeExpression<BigDecimal> atan2(PropTypeColumn<BigDecimal> other) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                PropTypeColumn.acceptAnyValue(o, other);
            }, MathMethodEnum.Atan2);
        }, BigDecimal.class);
    }

    /**
     * 或使用{@link SQLMathExpression}
     *
     * @return
     */
    default NumberTypeExpression<BigDecimal> truncate() {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.math(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
            }, MathMethodEnum.Truncate);
        }, BigDecimal.class);
    }

    /**
     * 加法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> add(PropTypeColumn<TOtherProperty> other) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                PropTypeColumn.acceptAnyValue(o, other);
            }, NumberCalcEnum.NUMBER_ADD);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> add(TOtherProperty constant) {
        return add(Expression.of(getEntitySQLContext()).constant(constant));
    }

    /**
     * 减法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> subtract(PropTypeColumn<TOtherProperty> other) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                PropTypeColumn.acceptAnyValue(o, other);
            }, NumberCalcEnum.NUMBER_SUBTRACT);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> subtract(TOtherProperty constant) {
        return subtract(Expression.of(getEntitySQLContext()).constant(constant));
    }

    /**
     * 乘法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> multiply(PropTypeColumn<TOtherProperty> other) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                PropTypeColumn.acceptAnyValue(o, other);
            }, NumberCalcEnum.NUMBER_MULTIPLY);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> multiply(TOtherProperty constant) {
        return multiply(Expression.of(getEntitySQLContext()).constant(constant));
    }

    /**
     * 除法
     *
     * @param other
     * @param <TOtherProperty>
     * @return
     */
    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> divide(PropTypeColumn<TOtherProperty> other) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.numberCalc(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                PropTypeColumn.acceptAnyValue(o, other);
            }, NumberCalcEnum.NUMBER_DIVIDE);
        }, getPropertyType());
    }

    default <TOtherProperty extends Number> NumberTypeExpression<BigDecimal> divide(TOtherProperty constant) {
        return divide(Expression.of(getEntitySQLContext()).constant(constant));
    }

    @Override
    default NumberTypeExpression<TProperty> createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new NumberTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    @Override
    default NumberFilterTypeExpression<TProperty> createFilterChainExpression(SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        return new NumberFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), func, propType);
    }
}
