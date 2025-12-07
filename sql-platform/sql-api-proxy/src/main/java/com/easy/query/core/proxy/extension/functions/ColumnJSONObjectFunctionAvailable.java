package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.DateTimeTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.JSONObjectTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.JSONObjectTypeExpressionImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnJSONObjectFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, AnyTypeExpression<TProperty>>, SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty> {

    @Override
    default AnyTypeExpression<TProperty> createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    default AnyTypeExpression<Object> getField(String jsonKey) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.jsonObjectField(s -> {
                PropTypeColumn.acceptAnyValue(s, this);
                s.format(key);
            });
        }, Object.class);
    }
    default JSONObjectTypeExpression<Object> getJsonObject(String jsonKey) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        return new JSONObjectTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.jsonObjectExtract(s -> {
                PropTypeColumn.acceptAnyValue(s, this);
                s.format(key);
            });
        }, Object.class);
    }
    default StringTypeExpression<String> getString(String jsonKey) {
        return getField(jsonKey).toStr();
    }
    default BooleanTypeExpression<Boolean> getBoolean(String jsonKey) {
        return getField(jsonKey).toBoolean();
    }
    default DateTimeTypeExpression<LocalDateTime> getLocalDateTime(String jsonKey) {
        return getField(jsonKey).toDateTime(LocalDateTime.class);
    }
    default DateTimeTypeExpression<LocalDate> getLocalDate(String jsonKey) {
        return getField(jsonKey).toDateTime(LocalDate.class);
    }
    default NumberTypeExpression<Integer> getInteger(String jsonKey) {
        return getField(jsonKey).toNumber(Integer.class);
    }
    default NumberTypeExpression<Long> getLong(String jsonKey) {
        return getField(jsonKey).toNumber(Long.class);
    }
    default NumberTypeExpression<BigDecimal> getBigDecimal(String jsonKey) {
        return getField(jsonKey).toNumber(BigDecimal.class);
    }

    default void containsKey(String jsonKey) {
        String key = getJsonKey(getEntitySQLContext().getRuntimeContext(), jsonKey);
        getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
            SQLFunc fx = f.getRuntimeContext().fx();
            SQLFunction sqlFunction = fx.jsonObjectContainsKey(s -> {
                PropTypeColumn.acceptAnyValue(s, this);
                s.format(key);
            });
            f.sqlFunctionExecute(getTable(), sqlFunction);
        }));
    }


    static String getJsonKey(QueryRuntimeContext runtimeContext, String jsonKey) {
        return runtimeContext.getMapColumnNameChecker().checkColumnName(jsonKey);
    }

}
