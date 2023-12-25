package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableStringChainExpressionImpl;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnDateTimeFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionComparableDateTimeChainExpression<TProperty>>,
        ColumnFunctionCastStringAvailable<TProperty> {


    default ColumnFunctionComparableStringChainExpression<String> format(String javaFormat) {
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.dateTimeFormat(this.getValue(), javaFormat);
        }, String.class);
    }
    @Override
    default ColumnFunctionComparableDateTimeChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }
}
