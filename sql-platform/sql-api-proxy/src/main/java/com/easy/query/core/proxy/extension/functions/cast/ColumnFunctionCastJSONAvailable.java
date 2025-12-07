package com.easy.query.core.proxy.extension.functions.cast;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.functions.type.JSONArrayTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.JSONObjectTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.JSONArrayTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.JSONObjectTypeExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/25 09:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCastJSONAvailable<TProperty> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
    default JSONObjectTypeExpression<Object> asJSONObject(){
        return new JSONObjectTypeExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                return ((DSLSQLFunctionAvailable) this).func().apply(fx);
            } else {
                return fx.anySQLFunction("{0}",c->c.column(this.getTable(),this.getValue()));
            }
        }, String.class);
    }
    default JSONArrayTypeExpression<Object> asJSONArray(){
        return new JSONArrayTypeExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                return ((DSLSQLFunctionAvailable) this).func().apply(fx);
            } else {
                return fx.anySQLFunction("{0}",c->c.column(this.getTable(),this.getValue()));
            }
        }, String.class);
    }
}
