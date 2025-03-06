package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2025/3/6 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAggregateFilterFunctionAvailable<TProperty, TChain> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
    TChain createFilterChainExpression(EntitySQLContext entitySQLContext, PropTypeColumn<?> self, TableAvailable table, String property, SQLFuncExpression2<PropTypeColumn<?>, SQLFunc, SQLFunction> func, Class<?> propType);

    default TChain max() {
        return createFilterChainExpression(this.getEntitySQLContext(),this, this.getTable(), this.getValue(), (self,fx) -> {
            return fx.max(x->{
                PropTypeColumn.columnFuncSelector(x,self);
            });
        }, getPropertyType());
    }

    default TChain min() {
        return createFilterChainExpression(this.getEntitySQLContext(),this, this.getTable(), this.getValue(), (self,fx)  -> {
            return fx.min(x->{
                PropTypeColumn.columnFuncSelector(x,self);
            });
        }, getPropertyType());
    }
}
