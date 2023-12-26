package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnObjectFunctionAvailable<TProperty, TChain> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
    TChain createChainExpression(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType);

    default <T extends Long> ColumnFuncComparableExpression<T> count() {
        return count(false);
    }

    default <T extends Long> ColumnFuncComparableExpression<T> count(boolean distinct) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.count(sqlFunction).distinct(distinct);
            } else {
                return fx.count(this.getValue()).distinct(distinct);
            }
        }, Long.class);
    }

    default TChain max() {
        return createChainExpression(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.max(sqlFunction);
            } else {
                return fx.max(this.getValue());
            }
        }, getPropertyType());
    }

    default TChain min() {
        return createChainExpression(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.min(sqlFunction);
            } else {
                return fx.min(this.getValue());
            }
        }, getPropertyType());
    }


    default <T> TChain nullDefault(T value) {
        return nullDefault(o -> o.value(value));
    }

    default <T> TChain nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return createChainExpression(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.valueOrDefault(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            } else {
                return fx.valueOrDefault(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            }
        }, getPropertyType());
    }

}
