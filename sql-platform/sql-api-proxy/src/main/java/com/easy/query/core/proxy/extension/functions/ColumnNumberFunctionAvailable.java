package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.EntitySQLContext;
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
public interface ColumnNumberFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionComparableNumberChainExpression<TProperty>> {
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

    default <T extends BigDecimal> ColumnFunctionComparableNumberChainExpression<T> sum() {
        return sum(false);
    }
    default <T extends BigDecimal> ColumnFunctionComparableNumberChainExpression<T> sum(boolean distinct) {
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
                return fx.concat(o -> {
                    o.sqlFunc(sqlFunction);
                });
            } else {
                return fx.abs(new SimpleSQLTableOwner(this.getTable()),this.getValue());
            }
        }, getPropertyType());
    }

    @Override
    default ColumnFunctionComparableNumberChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
