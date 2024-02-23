package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnObjectFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/28 14:57
 * 抽象的group代理对象
 *
 * @author xuejiaming
 */
public abstract class AbstractGrouping1Proxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity,TSourceProxy> extends AbstractProxyEntity<TProxy, TEntity> implements SQLGroupByExpression {

    private final TSourceProxy tSourceProxy;

    public AbstractGrouping1Proxy(TSourceProxy tSourceProxy) {
        this.tSourceProxy = tSourceProxy;
    }

    /**
     * 当仅单表是group就是当前表
     * 如果是多表下比如join下那么groups就是MergeTuple2-10最多10个如有需要可以提交issue或者自行扩展
     * @return
     */
    public TSourceProxy group(){
        return tSourceProxy;
    }

    /**
     * 请使用{@link #expression()}或者{@link Expression#count()}
     * COUNT(*)
     * @return 返回类型为Long
     */
    public ColumnFunctionComparableNumberChainExpression<Long> count() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(getEntitySQLContext(),null,null, f->{
            return f.count(c->{});
        }, Long.class);
    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<Long> count(ColumnObjectFunctionAvailable<TProperty, ?> column) {
        return column.count();
    }
    /**
     * 请使用{@link #expression()}或者{@link Expression#intCount()}
     * COUNT(*)
     * @return 返回类型为Integer
     */
    public ColumnFunctionComparableNumberChainExpression<Integer> intCount() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(getEntitySQLContext(),null,null,f->{
            return f.count(c->{});
        }, Integer.class);
    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<Integer> intCount(ColumnObjectFunctionAvailable<TProperty, ?> column) {
        return column.intCount();
    }
    public <TProperty, TChain extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> TChain max(ColumnObjectFunctionAvailable<TProperty, TChain> column) {
        return column.max();
    }

    public <TProperty, TChain extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>> TChain min(ColumnObjectFunctionAvailable<TProperty, TChain> column) {
        return column.min();
    }
    public <TProperty extends Number> ColumnFunctionComparableNumberChainExpression<TProperty> sum(ColumnNumberFunctionAvailable<TProperty> column) {
        return column.sum();
    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<BigDecimal> sumBigDecimal(ColumnNumberFunctionAvailable<TProperty> column) {
        return column.sumBigDecimal();
    }
    public <TProperty> ColumnFunctionComparableNumberChainExpression<BigDecimal> avg(ColumnNumberFunctionAvailable<TProperty> column) {
        return column.avg();
    }
    public <TProperty> ColumnFunctionComparableStringChainExpression<String> join(ColumnStringFunctionAvailable<TProperty> column, String delimiter) {
        return column.join(delimiter);
    }

    protected <TKey extends PropTypeColumn<TKey1>, TKey1> void acceptGroupSelector(TKey key, GroupSelector s){

        if(key instanceof DSLSQLFunctionAvailable){
            Function<SQLFunc, SQLFunction> funcCreate = ((DSLSQLFunctionAvailable) key).func();
            SQLFunc fx = s.getRuntimeContext().fx();
            SQLFunction sqlFunction = funcCreate.apply(fx);
            s.columnFunc(key.getTable(),sqlFunction);
        }else{
            key.accept(s);
//            if(key instanceof SQLNativeDraft){
//            }else{
//                s.column(key.getTable(), key.getValue());
//            }
        }
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }
}