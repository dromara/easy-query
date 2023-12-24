package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.ColumnFunctionComparableChainExpression;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnObjectFunctionAvailable<TProperty,TChain> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
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
    default ColumnFunctionComparableChainExpression<TProperty> max() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.max(sqlFunction);
            } else {
                return fx.max(this.getValue());
            }
        }, getPropertyType());
    }

    default ColumnFunctionComparableChainExpression<TProperty> min() {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.min(sqlFunction);
            } else {
                return fx.min(this.getValue());
            }
        }, getPropertyType());
    }


    default <T> TChain nullDefault(T value) {
        return nullDefault(o->o.value(value));
    }

     <T> TChain nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector);
    default ColumnFuncComparableExpression<Integer> compareTo(String comparedValue) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.compareTo(sqlFunction,comparedValue);
            } else {
                return fx.compareTo(this.getValue(),comparedValue);
            }
        }, Integer.class);
    }
    default <TColumnProxy> ColumnFuncComparableExpression<Integer> compareTo(SQLColumn<TColumnProxy,String> sqlColumn) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.compareTo(sqlFunction,new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
            } else {
                return fx.compareTo(this.getValue(),new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
            }
        }, Integer.class);
    }
    default ColumnFuncComparableExpression<Integer> compareTo(DSLSQLFunctionAvailable otherSQLFunction) {
        return new SQLColumnFunctionComparableExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            SQLFunction comparedSQLFunction = otherSQLFunction.func().apply(fx);
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.compareTo(sqlFunction,comparedSQLFunction);
            } else {
                return fx.compareTo(this.getValue(),comparedSQLFunction);
            }
        }, Integer.class);
    }
}
