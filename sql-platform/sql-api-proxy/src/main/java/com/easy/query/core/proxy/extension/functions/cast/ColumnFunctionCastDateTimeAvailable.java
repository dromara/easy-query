package com.easy.query.core.proxy.extension.functions.cast;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/25 09:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCastDateTimeAvailable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {

    default <T> ColumnFunctionComparableDateTimeChainExpression<T> toDateTime(Class<T> clazz){
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.cast(sqlFunction, clazz);
            } else {
                return fx.cast(this.getValue(), clazz);
            }
        }, clazz);
    }
}
