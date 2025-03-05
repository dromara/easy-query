package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableJsonMapChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableJsonMapChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnJsonMapFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, ColumnFunctionCompareComparableAnyChainExpression<TProperty>>, SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty> {

    @Override
    default ColumnFunctionCompareComparableAnyChainExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }

    default ColumnFunctionCompareComparableJsonMapChainExpression<String> getField(String jsonKey) {
        return getField(jsonKey,String.class);
    }
    default <TP> ColumnFunctionCompareComparableJsonMapChainExpression<TP> getField(String jsonKey,Class<TP> valueType) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new ColumnFunctionCompareComparableJsonMapChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.jsonField(x->x.sqlFunc(sqlFunction).format(key));
            } else {
                return fx.jsonField(x->x.column(this.getValue()).format(key));
            }
        }, valueType);
    }
    default ColumnFunctionCompareComparableBooleanChainExpression<TProperty> containsField(String jsonKey) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.containsField(x->x.sqlFunc(sqlFunction).format(key));
            } else {
                return fx.containsField(x->x.column(this.getValue()).format(key));
            }
        });
    }


    static String getJsonKey(QueryRuntimeContext runtimeContext,String jsonKey){
        return runtimeContext.getMapColumnNameChecker().checkColumnName(jsonKey);
    }

}
