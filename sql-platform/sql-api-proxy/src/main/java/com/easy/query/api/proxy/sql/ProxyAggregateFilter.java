package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/23 14:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAggregateFilter {
    AggregateFilter getAggregateFilter();

    default QueryRuntimeContext getRuntimeContext() {
        return getAggregateFilter().getRuntimeContext();
    }

    default ProxyAggregateFilter avg(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return avg(true, column, compare, val);
    }

    default ProxyAggregateFilter avg(boolean condition, SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false), column, compare, val);
    }

    default ProxyAggregateFilter avgDistinct(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return avgDistinct(true, column, compare, val);
    }

    default ProxyAggregateFilter avgDistinct(boolean condition, SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true), column, compare, val);
    }

    default ProxyAggregateFilter min(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return min(true, column, compare, val);
    }

    default ProxyAggregateFilter min(boolean condition, SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMinFunction(), column, compare, val);
    }

    default ProxyAggregateFilter max(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return max(true, column, compare, val);
    }

    default ProxyAggregateFilter max(boolean condition, SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMaxFunction(), column, compare, val);
    }

    default ProxyAggregateFilter sum(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return sum(true, column, compare, val);
    }

    default ProxyAggregateFilter sum(boolean condition, SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(false), column, compare, val);
    }

    default ProxyAggregateFilter sumDistinct(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return sum(true, column, compare, val);
    }

    default ProxyAggregateFilter sumDistinct(boolean condition, SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(true), column, compare, val);
    }

    default ProxyAggregateFilter countDistinct(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return countDistinct(true, column, compare, val);
    }

    default ProxyAggregateFilter countDistinct(boolean condition, SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(true), column, compare, val);
    }

    default ProxyAggregateFilter count(SQLColumn<?> column, AggregatePredicateCompare compare, Object val) {
        return count(true, column, compare, val);
    }

    default ProxyAggregateFilter count(boolean condition, SQLColumn<?> column, SQLPredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(false), column, compare, val);
    }

    default ProxyAggregateFilter func(boolean condition, ColumnFunction columnFunction, SQLColumn<?> column, SQLPredicateCompare compare, Object val) {
        if (condition) {
            getAggregateFilter().func(column.getTable(), columnFunction, column.value(), compare, val);
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
