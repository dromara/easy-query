package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAnyFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionComparableAnyChainExpression<TProperty>>,SQLSelectAsExpression, PropTypeColumn<TProperty> {

    @Override
    default ColumnFunctionComparableAnyChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
