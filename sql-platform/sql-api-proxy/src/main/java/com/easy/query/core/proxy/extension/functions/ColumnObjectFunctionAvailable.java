package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.NumberFilterTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.filter.impl.NumberFilterTypeExpressionImpl;
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

    default NumberFilterTypeExpression<Long> count() {
        return count(false);
    }

    default NumberFilterTypeExpression<Long> count(boolean distinct) {
        return new NumberFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, Long.class);
    }

    default NumberFilterTypeExpression<Integer> intCount() {
        return intCount(false);
    }

    default NumberFilterTypeExpression<Integer> intCount(boolean distinct) {
        return count(distinct).asAnyType(Integer.class);
    }

    default TChain max() {
        return createChainExpression(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.max(sqlFunction);
            } else {
                return fx.max(this.getValue());
            }
        }, getPropertyType());
    }

    default TChain min() {
        return createChainExpression(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.min(sqlFunction);
            } else {
                return fx.min(this.getValue());
            }
        }, getPropertyType());
    }

    default TChain nullOrDefault(TProperty value) {
        return nullOrDefault(o -> o.value(_toFunctionSerializeValue(value)));
    }

    default TChain nullOrDefault(PropTypeColumn<TProperty> propTypeColumn) {
        return nullOrDefault(x -> {
            PropTypeColumn.columnFuncSelector(x.getColumnFuncSelector(), propTypeColumn);
        });
    }

    default TChain nullOrDefault(SQLActionExpression1<ProxyColumnFuncSelector> selector) {
        return createChainExpression(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.nullOrDefault(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                selector.apply(new ProxyColumnFuncSelectorImpl(o));
            });
        }, getPropertyType());
    }


    default BooleanTypeExpression<Boolean> equalsWith(TProperty value) {
        return equalsWith(x -> x.value(_toFunctionSerializeValue(value)));
    }

    default BooleanTypeExpression<Boolean> equalsWith(PropTypeColumn<TProperty> propTypeColumn) {
        return equalsWith(x -> {
            PropTypeColumn.columnFuncSelector(x.getColumnFuncSelector(), propTypeColumn);
        });
    }

    default BooleanTypeExpression<Boolean> equalsWith(SQLActionExpression1<ProxyColumnFuncSelector> selector) {
        return new BooleanTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.equalsWith(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                selector.apply(new ProxyColumnFuncSelectorImpl(o));
            });
        }, Boolean.class);
    }

}
