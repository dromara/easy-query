package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;
import com.easy.query.core.proxy.grouping.SQLGroupQueryable;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2026/2/9 16:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class AggregateProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractProxyEntity<TProxy, TEntity> {

    public static <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> AggregateProxy<TProxy, TEntity> of(TProxy TProxy) {
        return new AggregateProxy<>(TProxy);
    }
    @Override
    public Class<TEntity> getEntityClass() {
        return TProxy.getEntityClass();
    }

    protected final TProxy TProxy;

    public AggregateProxy(TProxy TProxy) {
        this.TProxy = TProxy;
        this.entitySQLContext = TProxy.getEntitySQLContext();
    }

    public NumberTypeExpression<Long> count() {
        return where(null).count();
    }


    public SQLGroupQueryable<TProxy> distinct() {
        return where(null).distinct();
    }

    public <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector) {
        return where(null).count(columnSelector);
    }


    //    public <TProperty> ColumnFunctionComparableNumberChainExpression<Integer> intCount(ColumnObjectFunctionAvailable<TProperty, ?> column) {
//        return column.intCount();
//    }
    public NumberTypeExpression<Integer> intCount() {
        return where(null).intCount();
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

    public <TProperty> AnyTypeExpression<TProperty> max(SQLFuncExpression1<TProxy, PropTypeColumn<TProperty>> columnSelector) {
        return where(null).max(columnSelector);
    }

    public <TProperty> AnyTypeExpression<TProperty> min(SQLFuncExpression1<TProxy, PropTypeColumn<TProperty>> columnSelector) {
        return where(null).min(columnSelector);
    }

    public <TProperty extends Number> NumberTypeExpression<TProperty> sum(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TProperty>> columnSelector) {
        return where(null).sum(columnSelector);
    }

    public <TProperty extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TProperty>> columnSelector) {
        return where(null).sumBigDecimal(columnSelector);
    }

    public <TProperty extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TProperty>> columnSelector) {
        return where(null).avg(columnSelector);
    }

    public <TProperty> StringTypeExpression<String> joining(SQLFuncExpression1<TProxy, PropTypeColumn<TProperty>> columnSelector) {
        return where(null).joining(columnSelector, ",");
    }


    public <TProperty> StringTypeExpression<String> joining(SQLFuncExpression1<TProxy, PropTypeColumn<TProperty>> columnSelector, String delimiter) {
        return where(null).joining(columnSelector, delimiter);
    }


    public SQLGroupQueryable<TProxy> where(SQLActionExpression1<TProxy> where) {
        return new DefaultSQLGroupQueryable<>(TProxy, this.entitySQLContext, where, null);
    }
}
