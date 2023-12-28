package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnObjectFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/28 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractGrouping1Proxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity,TSourceProxy> extends AbstractProxyEntity<TProxy, TEntity> implements SQLGroupByExpression {

    private final TSourceProxy tSourceProxy;

    public AbstractGrouping1Proxy(TSourceProxy tSourceProxy) {
        this.tSourceProxy = tSourceProxy;
    }

    public <TProperty> ColumnFuncComparableExpression<Long> count(SQLFuncExpression1<TSourceProxy, ColumnObjectFunctionAvailable<TProperty, ?>> column) {
        return column.apply(tSourceProxy).count();
    }
    public ColumnFuncComparableExpression<Long> count() {
        return new SQLColumnFunctionComparableExpressionImpl<>(getEntitySQLContext(),null,null,f->{
            return f.count(c->{});
        }, Long.class);
    }
    public ColumnFuncComparableExpression<Integer> intCount() {
        return new SQLColumnFunctionComparableExpressionImpl<>(getEntitySQLContext(),null,null,f->{
            return f.count(c->{});
        }, Integer.class);
    }

    public <TProperty> ColumnFuncComparableExpression<Integer> intCount(SQLFuncExpression1<TSourceProxy, ColumnObjectFunctionAvailable<TProperty, ?>> column) {
        return column.apply(tSourceProxy).intCount();
    }

    public <TProperty, TChain extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> TChain max(SQLFuncExpression1<TSourceProxy, ColumnObjectFunctionAvailable<TProperty, TChain>> column) {
        return column.apply(tSourceProxy).max();
    }

    public <TProperty, TChain extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> TChain min(SQLFuncExpression1<TSourceProxy, ColumnObjectFunctionAvailable<TProperty, TChain>> column) {
        return column.apply(tSourceProxy).min();
    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<BigDecimal> sum(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TProperty>> column) {
        return column.apply(tSourceProxy).sum();
    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TProperty>> column) {
        return column.apply(tSourceProxy).avg();
    }
    public <TProperty> ColumnFunctionComparableStringChainExpression<String> join(SQLFuncExpression1<TSourceProxy, ColumnStringFunctionAvailable<TProperty>> column, String delimiter) {
        return column.apply(tSourceProxy).join(delimiter);
    }

    protected <TKey extends PropTypeColumn<TKey1>, TKey1> void acceptGroupSelector(TKey key, GroupSelector s){

        if(key instanceof DSLSQLFunctionAvailable){
            Function<SQLFunc, SQLFunction> funcCreate = ((DSLSQLFunctionAvailable) key).func();
            SQLFunc fx = s.getRuntimeContext().fx();
            SQLFunction sqlFunction = funcCreate.apply(fx);
            s.columnFunc(key.getTable(),sqlFunction);
        }else{
            s.column(key.getTable(), key.getValue());
        }
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }
}