package com.easy.query.core.proxy.extension.functions.cast;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/25 09:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCastStringAvailable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
    default ColumnFunctionComparableStringChainExpression<String> toStr(){
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.cast(sqlFunction, String.class);
            } else {
                return fx.cast(this.getValue(), String.class);
            }
        }, String.class);
    }
    default ColumnFunctionComparableStringChainExpression<String> toChar(){
        return new ColumnFunctionComparableStringChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.cast(sqlFunction, char.class);
            } else {
                return fx.cast(this.getValue(), char.class);
            }
        }, String.class);
    }
}
