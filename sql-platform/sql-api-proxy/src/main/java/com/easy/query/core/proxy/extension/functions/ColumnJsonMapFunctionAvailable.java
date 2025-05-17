package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.executor.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.JsonMapTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.JsonMapTypeExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnJsonMapFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, AnyTypeExpression<TProperty>>, SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty> {

    @Override
    default AnyTypeExpression<TProperty> createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, getPropertyType());
    }

    default JsonMapTypeExpression<String> getField(String jsonKey) {
        return getField(jsonKey,String.class);
    }
    default <TP> JsonMapTypeExpression<TP> getField(String jsonKey, Class<TP> valueType) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new JsonMapTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.jsonField(x->x.sqlFunc(sqlFunction).format(key));
            } else {
                return fx.jsonField(x->x.column(this.getValue()).format(key));
            }
        }, valueType);
    }
    default BooleanTypeExpression<TProperty> containsField(String jsonKey) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new BooleanTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
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
