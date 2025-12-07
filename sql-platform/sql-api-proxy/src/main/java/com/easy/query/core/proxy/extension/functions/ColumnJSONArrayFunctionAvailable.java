package com.easy.query.core.proxy.extension.functions;

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
import com.easy.query.core.proxy.extension.functions.type.JSONArrayTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.JSONObjectTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnJSONArrayFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, AnyTypeExpression<TProperty>>, SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty> {

    @Override
    default AnyTypeExpression<TProperty> createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    default AnyTypeExpression<Object> getElement(int index) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.jsonArrayByIndex(s -> {
                PropTypeColumn.columnFuncSelector(s, this);
                s.format(index);
            });
        }, Objects.class);
    }

    default BooleanTypeExpression<Boolean> getBoolean(int index) {
        return getElement(index).toBoolean();
    }

    default NumberTypeExpression<Integer> getInteger(int index) {
        return getElement(index).toNumber(Integer.class);
    }

    default NumberTypeExpression<Long> getLong(int index) {
        return getElement(index).toNumber(Long.class);
    }
    default NumberTypeExpression<BigDecimal> getBigDecimal(int index) {
        return getElement(index).toNumber(BigDecimal.class);
    }

    default AnyTypeExpression<TProperty> getJSONElement(int index) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.jsonArrayExtractByIndex(s -> {
                PropTypeColumn.columnFuncSelector(s, this);
                s.format(index);
            });
        }, Objects.class);
    }
    default JSONObjectTypeExpression<Object> getJSONObject(int index) {
        return getJSONElement(index).asJSONObject();
    }
    default JSONArrayTypeExpression<Object> getJSONOArray(int index) {
        return getJSONElement(index).asJSONArray();
    }


}
