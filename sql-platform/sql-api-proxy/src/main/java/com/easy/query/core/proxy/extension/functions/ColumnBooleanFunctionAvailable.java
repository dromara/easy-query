package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.ColumnFunctionCompareComparableBooleanFilterChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.ColumnFunctionCompareComparableDateTimeFilterChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.ColumnFunctionCompareComparableStringFilterChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.filter.impl.ColumnFunctionCompareComparableBooleanFilterChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.filter.impl.ColumnFunctionCompareComparableStringFilterChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnBooleanFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionCompareComparableBooleanChainExpression<TProperty>>,
        ColumnAggregateFilterFunctionAvailable<TProperty, ColumnFunctionCompareComparableBooleanFilterChainExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty> {

    @Override
    default ColumnFunctionCompareComparableBooleanFilterChainExpression<TProperty> max() {
        return createFilterChainExpression(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.max(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }

    @Override
    default ColumnFunctionCompareComparableBooleanFilterChainExpression<TProperty> min() {
        return createFilterChainExpression(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }
    @Override
    default ColumnFunctionCompareComparableBooleanChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }

    /**
     * 取反函数
     * @return NOT xxx
     */
    default ColumnFunctionCompareComparableBooleanChainExpression<TProperty> not() {
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.not(sqlFunction);
            } else {
                return fx.not(this.getValue());
            }
        }, getPropertyType());
    }
    @Override
    default ColumnFunctionCompareComparableBooleanFilterChainExpression<TProperty> createFilterChainExpression(EntitySQLContext entitySQLContext, PropTypeColumn<?> self, TableAvailable table, String property, SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableBooleanFilterChainExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), func, getPropertyType());
    }
}
