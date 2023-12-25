package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableBooleanChainExpressionImpl;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnBooleanFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionComparableBooleanChainExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty> {

    @Override
    default ColumnFunctionComparableBooleanChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
