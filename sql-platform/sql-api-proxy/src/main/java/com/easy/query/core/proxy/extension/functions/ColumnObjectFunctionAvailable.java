package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
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

    default <T extends Long> ColumnFunctionComparableNumberChainExpression<T> count() {
        return count(false);
    }

    default <T extends Long> ColumnFunctionComparableNumberChainExpression<T> count(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.count(sqlFunction).distinct(distinct);
            } else {
                return fx.count(this.getValue()).distinct(distinct);
            }
        }, Long.class);
    }
    default <T extends Integer> ColumnFunctionComparableNumberChainExpression<T> intCount() {
        return intCount(false);
    }

    default <T extends Integer> ColumnFunctionComparableNumberChainExpression<T> intCount(boolean distinct) {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.count(sqlFunction).distinct(distinct);
            } else {
                return fx.count(this.getValue()).distinct(distinct);
            }
        }, Integer.class);
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


    /**
     * 请使用 nullOrDefault
     * @param value
     * @return
     * @param <T>
     */
    @Deprecated
    default TChain nullDefault(TProperty value) {
        return nullOrDefault(value);
    }

    default TChain nullOrDefault(TProperty value) {
        return nullOrDefault(o -> o.value(_toFunctionSerializeValue(value)));
    }
    default TChain nullOrDefault(PropTypeColumn<TProperty> propTypeColumn) {
        return nullOrDefault(x->{
            processPropTypeColumn(x,propTypeColumn);
        });
    }

    default TChain nullOrDefault(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return createChainExpression(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.nullOrDefault(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            } else {
                return fx.nullOrDefault(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            }
        }, getPropertyType());
    }

    /**
     * 请使用 nullOrDefault
     * @param selector
     * @return
     * @param <T>
     */
    @Deprecated
    default <T> TChain nullDefault(SQLExpression1<ProxyColumnFuncSelector> selector) {
        return nullOrDefault(selector);
    }


    default ColumnFunctionComparableBooleanChainExpression<Boolean> equalsWith(TProperty value){
        return equalsWith(x->x.value(_toFunctionSerializeValue(value)));
    }
    default ColumnFunctionComparableBooleanChainExpression<Boolean> equalsWith(PropTypeColumn<TProperty> propTypeColumn){
        return equalsWith(x->{
            processPropTypeColumn(x,propTypeColumn);
        });
    }

    static <T> void processPropTypeColumn(ProxyColumnFuncSelector proxyColumnFuncSelector,PropTypeColumn<T> propTypeColumn){
        if(propTypeColumn instanceof SQLColumn){
            SQLColumn<?, ?> sqlColumn = (SQLColumn<?, ?>) propTypeColumn;
            proxyColumnFuncSelector.column(sqlColumn);
        }else if(propTypeColumn instanceof DSLSQLFunctionAvailable){
            DSLSQLFunctionAvailable typeColumn = (DSLSQLFunctionAvailable) propTypeColumn;
            SQLFunc fx = propTypeColumn.getEntitySQLContext().getRuntimeContext().fx();
            SQLFunction sqlFunction = typeColumn.func().apply(fx);
            proxyColumnFuncSelector.sqlFunc(sqlFunction);
        }else{
            throw new UnsupportedOperationException();
        }
    }
    default ColumnFunctionComparableBooleanChainExpression<Boolean> equalsWith(SQLExpression1<ProxyColumnFuncSelector> selector){
        return new ColumnFunctionComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.equalsWith(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            } else {
                return fx.equalsWith(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            }
        }, Boolean.class);
    }

}
