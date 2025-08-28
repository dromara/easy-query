package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.BooleanFilterTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.impl.BooleanFilterTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnBooleanFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, BooleanTypeExpression<TProperty>>,
        ColumnAggregateFilterFunctionAvailable<TProperty, BooleanFilterTypeExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty> {

    @Override
    default BooleanFilterTypeExpression<TProperty> max() {
        return createFilterChainExpression((self, fx) -> {
            return fx.max(x -> {
                PropTypeColumn.acceptAnyValue(x, self);
            });
        }, getPropertyType());
    }

    @Override
    default BooleanFilterTypeExpression<TProperty> min() {
        return createFilterChainExpression((self, fx) -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            });
        }, getPropertyType());
    }
    @Override
    default BooleanTypeExpression<TProperty> createChainExpression( Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new BooleanTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    /**
     * 取反函数
     * @return NOT xxx
     */
    default BooleanTypeExpression<TProperty> not() {
        return new BooleanTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.not(sqlFunction);
            } else {
                return fx.not(this.getValue());
            }
        }, getPropertyType());
    }
    @Override
    default BooleanFilterTypeExpression<TProperty> createFilterChainExpression(SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType) {
        return new BooleanFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), func, propType);
    }
}
