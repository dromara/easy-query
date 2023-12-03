package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.api.proxy.sql.core.available.ProxySQLFuncAvailable;
import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/23 14:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAggregateFilter extends SQLProxyNative<ProxyAggregateFilter>, ProxySQLFuncAvailable {
    AggregateFilter getAggregateFilter();

    default QueryRuntimeContext getRuntimeContext() {
        return getAggregateFilter().getRuntimeContext();
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter avg(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return avg(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter avg(boolean condition, SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter avgDistinct(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return avgDistinct(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter avgDistinct(boolean condition, SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Comparable<TProperty>> ProxyAggregateFilter min(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return min(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Comparable<TProperty>> ProxyAggregateFilter min(boolean condition, SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMinFunction(), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Comparable<TProperty>> ProxyAggregateFilter max(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return max(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Comparable<TProperty>> ProxyAggregateFilter max(boolean condition, SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMaxFunction(), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter sum(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return sum(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter sum(boolean condition, SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(false), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter sumDistinct(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return sumDistinct(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty extends Number> ProxyAggregateFilter sumDistinct(boolean condition, SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(true), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty> ProxyAggregateFilter countDistinct(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return countDistinct(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty> ProxyAggregateFilter countDistinct(boolean condition, SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(true), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty> ProxyAggregateFilter count(SQLColumn<TProxy, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return count(true, column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty> ProxyAggregateFilter count(boolean condition, SQLColumn<TProxy, TProperty> column, SQLPredicateCompare compare, TProperty val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(false), column, compare, val);
    }

    default <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TProperty> ProxyAggregateFilter func(boolean condition, ColumnFunction columnFunction, SQLColumn<TProxy, TProperty> column, SQLPredicateCompare compare, TProperty val) {
        if (condition) {
            getAggregateFilter().func0(column.getTable(), columnFunction, column.value(), compare, val);
        }
        return this;
    }

    default ProxyAggregateFilter and() {
        return and(true);
    }

    default ProxyAggregateFilter and(boolean condition) {
        if (condition) {
            getAggregateFilter().and();
        }
        return this;
    }

    default ProxyAggregateFilter and(SQLExpression1<ProxyAggregateFilter> proxyAggregateFilterSQLExpression) {
        return and(true, proxyAggregateFilterSQLExpression);
    }

    default ProxyAggregateFilter and(boolean condition, SQLExpression1<ProxyAggregateFilter> proxyAggregateFilterSQLExpression) {
        if (condition) {
            getAggregateFilter().and(filter -> {
                proxyAggregateFilterSQLExpression.apply(new ProxyAggregateFilterImpl(filter));
            });
        }
        return this;
    }

    default ProxyAggregateFilter or() {
        return or(true);
    }

    default ProxyAggregateFilter or(boolean condition) {
        if (condition) {
            getAggregateFilter().or();
        }
        return this;
    }

    default ProxyAggregateFilter or(SQLExpression1<ProxyAggregateFilter> proxyAggregateFilterSQLExpression) {
        return or(true, proxyAggregateFilterSQLExpression);
    }

    default ProxyAggregateFilter or(boolean condition, SQLExpression1<ProxyAggregateFilter> proxyAggregateFilterSQLExpression) {
        if (condition) {
            getAggregateFilter().or(filter -> {
                proxyAggregateFilterSQLExpression.apply(new ProxyAggregateFilterImpl(filter));
            });
        }
        return this;
    }

}
