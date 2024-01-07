package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.proxy.core.EntitySQLContext;
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
    default <T extends BigDecimal> ColumnFunctionComparableNumberChainExpression<T> avg() {
        return avg(false);
    }
    default <T extends BigDecimal> ColumnFunctionComparableNumberChainExpression<T>  avg(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.avg(sqlFunction).distinct(distinct);
            } else {
                return fx.avg(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }

    default <T extends Number> ColumnFunctionComparableNumberChainExpression<T> sum() {
        return sum(false);
    }
    default <T extends Number> ColumnFunctionComparableNumberChainExpression<T> sum(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.sum(sqlFunction).distinct(distinct);
            } else {
                return fx.sum(this.getValue()).distinct(distinct);
            }
        }, getPropertyType());
    }
    default <T extends BigDecimal> ColumnFunctionComparableNumberChainExpression<T> sumBigDecimal() {
        return sum(false);
    }
    default <T extends BigDecimal> ColumnFunctionComparableNumberChainExpression<T> sumBigDecimal(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.sum(sqlFunction).distinct(distinct);
            } else {
                return fx.sum(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<TProperty> abs() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Abs);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Abs);
            }
        }, getPropertyType());
    }
    default ColumnFunctionComparableNumberChainExpression<Integer> sign() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sin);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Sin);
            }
        }, Integer.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> floor() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Floor);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Floor);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> ceiling() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Ceiling);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Ceiling);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> round() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Round);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Round);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> round(int decimals) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(decimals);
                }, MathMethodEnum.Ceiling);
            } else {
                return fx.math(o->o.column(this.getValue()).value(decimals),MathMethodEnum.Ceiling);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> exp() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Exp);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Exp);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> log() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Log);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Log);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> log(BigDecimal newBase) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(newBase);
                }, MathMethodEnum.Log);
            } else {
                return fx.math(o->o.column(this.getValue()).value(newBase),MathMethodEnum.Log);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> log10() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Log10);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Log10);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> pow() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Pow);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Pow);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> pow(BigDecimal exponent) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(exponent);
                }, MathMethodEnum.Pow);
            } else {
                return fx.math(o->o.column(this.getValue()).value(exponent),MathMethodEnum.Pow);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> sqrt() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sqrt);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Sqrt);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> cos() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Cos);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Cos);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> sin() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sin);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Sin);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> tan() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Tan);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Tan);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> acos() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Acos);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Acos);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> asin() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Asin);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Asin);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> atan() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Atan);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Atan);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> atan2() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Atan2);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Atan2);
            }
        }, BigDecimal.class);
    }
    default ColumnFunctionComparableNumberChainExpression<BigDecimal> truncate() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Truncate);
            } else {
                return fx.math(o->o.column(this.getValue()),MathMethodEnum.Truncate);
            }
        }, BigDecimal.class);
    }
    @Override
    default ColumnFunctionComparableNumberChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
