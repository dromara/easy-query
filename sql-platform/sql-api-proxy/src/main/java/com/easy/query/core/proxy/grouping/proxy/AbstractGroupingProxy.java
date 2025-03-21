package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;
import com.easy.query.core.proxy.grouping.SQLGroupQueryable;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/28 14:57
 * 抽象的group代理对象
 *
 * @author xuejiaming
 */
public abstract class AbstractGroupingProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TSourceProxy> extends AbstractProxyEntity<TProxy, TEntity> implements SQLGroupByExpression {

    protected final TSourceProxy tSourceProxy;

    public AbstractGroupingProxy(TSourceProxy tSourceProxy) {
        this.tSourceProxy = tSourceProxy;
    }

    /**
     * 当仅单表是group就是当前表
     * 如果是多表下比如join下那么groups就是MergeTuple2-10最多10个如有需要可以提交issue或者自行扩展
     *
     * @return
     */
    public TSourceProxy groupTable() {
        return tSourceProxy;
    }
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        return where(null).count();
    }

    public ColumnFunctionCompareComparableNumberChainExpression<Long> count(boolean distinct) {
        return where(null).distinct(distinct).count();
    }


    //    public <TProperty> ColumnFunctionComparableNumberChainExpression<Integer> intCount(ColumnObjectFunctionAvailable<TProperty, ?> column) {
//        return column.intCount();
//    }
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount() {
        return where(null).intCount();
    }

    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(boolean distinct) {
        return where(null).distinct(distinct).intCount();
    }

    protected <TKey extends PropTypeColumn<TKey1>, TKey1> void acceptGroupSelector(TKey key, GroupSelector s) {

        if (key instanceof DSLSQLFunctionAvailable) {
            Function<SQLFunc, SQLFunction> funcCreate = ((DSLSQLFunctionAvailable) key).func();
            SQLFunc fx = s.getRuntimeContext().fx();
            SQLFunction sqlFunction = funcCreate.apply(fx);
            s.columnFunc(key.getTable(), sqlFunction);
        } else {
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

    public <TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> max(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TProperty>> columnSelector) {
        return where(null).max(columnSelector);
    }

    public <TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> min(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TProperty>> columnSelector) {
        return where(null).min(columnSelector);
    }

    public <TProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<TProperty> sum(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TProperty>> columnSelector) {
        return where(null).sum(columnSelector);
    }

    public <TProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TProperty>> columnSelector) {
        return where(null).sumBigDecimal(columnSelector);
    }

    public <TProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(SQLFuncExpression1<TSourceProxy, ColumnNumberFunctionAvailable<TProperty>> columnSelector) {
        return where(null).avg(columnSelector);
    }

    /**
     * 请使用函数 {@link #joining(SQLFuncExpression1)}
     * @param columnSelector
     * @param delimiter
     * @return
     * @param <TProperty>
     */
    @Deprecated
    public <TProperty> ColumnFunctionCompareComparableStringChainExpression<String> join(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TProperty>> columnSelector, String delimiter) {
        return where(null).joining(columnSelector, delimiter);
    }
    public <TProperty> ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TProperty>> columnSelector) {
        return where(null).joining(columnSelector, ",");
    }


    public <TProperty> ColumnFunctionCompareComparableStringChainExpression<String> joining(SQLFuncExpression1<TSourceProxy, PropTypeColumn<TProperty>> columnSelector, String delimiter) {
        return where(null).joining(columnSelector, delimiter);
    }


    public SQLGroupQueryable<TSourceProxy> where(SQLExpression1<TSourceProxy> where) {
        return new DefaultSQLGroupQueryable<>(tSourceProxy, this.entitySQLContext, where);
    }
//
//    public <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy element(int index, SQLFuncExpression1<TSourceProxy,T1Proxy> elementSelector) {
//        return new DefaultSQLGroupQueryable<>(tSourceProxy, this.entitySQLContext, condition, where);
//    }

}