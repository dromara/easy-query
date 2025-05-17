package com.easy.query.core.proxy.extension.functions.cast;

import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.functions.executor.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/25 09:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCastBooleanAvailable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {

    /**
     * 使用数据库函数将其转成boolean类型
     * @return
     */
    default BooleanTypeExpression<Boolean> toBoolean(){
        Class<Boolean> clazz = Boolean.class;
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.cast(sqlFunction, clazz);
            } else {
                return fx.cast(this.getValue(), clazz);
            }
        }, clazz);
    }

    /**
     * 编译层面欺骗编译器将其视作Boolean
     * @return
     */
    default BooleanTypeExpression<Boolean> asBoolean() {
        Class<Boolean> clazz = Boolean.class;
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                return ((DSLSQLFunctionAvailable) this).func().apply(fx);
            } else {
                return fx.anySQLFunction("{0}", c -> c.column(this.getTable(), this.getValue()));
            }
        }, clazz);
    }
}
