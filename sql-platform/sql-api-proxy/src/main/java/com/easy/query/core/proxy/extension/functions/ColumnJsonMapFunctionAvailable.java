package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.JsonMapTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.JsonMapTypeExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnJsonMapFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, AnyTypeExpression<TProperty>>, SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty> {

    @Override
    default AnyTypeExpression<TProperty> createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    default JsonMapTypeExpression<Object> getField(String jsonKey) {
        return getField(jsonKey, Object.class);
    }

    default BooleanTypeExpression<Boolean> getBooleanField(String jsonKey) {
        return getField(jsonKey).toBoolean();
    }
    default NumberTypeExpression<Integer> getIntegerField(String jsonKey) {
        return getField(jsonKey).toNumber(Integer.class);
    }
    default NumberTypeExpression<Long> getLongField(String jsonKey) {
        return getField(jsonKey).toNumber(Long.class);
    }

    default <TP> JsonMapTypeExpression<TP> getField(String jsonKey, Class<TP> valueType) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new JsonMapTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.jsonField(x -> x.sqlFunc(sqlFunction).format(key));
            } else {
                return fx.jsonField(x -> x.column(this.getValue()).format(key));
            }
        }, valueType);
    }

    default BooleanTypeExpression<TProperty> containsField(String jsonKey) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new BooleanTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.containsField(x -> x.sqlFunc(sqlFunction).format(key));
            } else {
                return fx.containsField(x -> x.column(this.getValue()).format(key));
            }
        });
    }


    static String getJsonKey(QueryRuntimeContext runtimeContext, String jsonKey) {
        return runtimeContext.getMapColumnNameChecker().checkColumnName(jsonKey);
    }

}
