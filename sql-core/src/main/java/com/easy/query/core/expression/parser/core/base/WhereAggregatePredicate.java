package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.SQLTableOwner;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * @author xuejiaming
 * @FileName: AggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:17
 */
public interface WhereAggregatePredicate<T1> extends SQLTableOwner {
    AggregateFilter getAggregateFilter();

    QueryRuntimeContext getRuntimeContext();

    default WhereAggregatePredicate<T1> avg(String property, AggregatePredicateCompare compare, Object val) {
        return avg(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> avg(boolean condition, String property, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false), property, compare, val);
    }

    default WhereAggregatePredicate<T1> avgDistinct(String property, AggregatePredicateCompare compare, Object val) {
        return avgDistinct(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> avgDistinct(boolean condition, String property, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true), property, compare, val);
    }

    default WhereAggregatePredicate<T1> min(String property, AggregatePredicateCompare compare, Object val) {
        return min(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> min(boolean condition, String property, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMinFunction(), property, compare, val);
    }

    default WhereAggregatePredicate<T1> max(String property, AggregatePredicateCompare compare, Object val) {
        return max(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> max(boolean condition, String property, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMaxFunction(), property, compare, val);
    }

    default WhereAggregatePredicate<T1> sum(String property, AggregatePredicateCompare compare, Object val) {
        return sum(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> sum(boolean condition, String property, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(false), property, compare, val);
    }

    default WhereAggregatePredicate<T1> sumDistinct(String property, AggregatePredicateCompare compare, Object val) {
        return sum(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> sumDistinct(boolean condition, String property, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(true), property, compare, val);
    }

    default WhereAggregatePredicate<T1> countDistinct(String property, AggregatePredicateCompare compare, Object val) {
        return countDistinct(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> countDistinct(boolean condition, String property, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(true), property, compare, val);
    }

    default WhereAggregatePredicate<T1> count(String property, AggregatePredicateCompare compare, Object val) {
        return count(true, property, compare, val);
    }

    default WhereAggregatePredicate<T1> count(boolean condition, String property, SQLPredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(false), property, compare, val);
    }

    WhereAggregatePredicate<T1> func(boolean condition, ColumnFunction columnFunction, String property, SQLPredicateCompare compare, Object val);


    <T2> WhereAggregatePredicate<T2> then(WhereAggregatePredicate<T2> sub);

    default WhereAggregatePredicate<T1> and() {
        return and(true);
    }

    WhereAggregatePredicate<T1> and(boolean condition);

    default WhereAggregatePredicate<T1> and(SQLExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return and(true, sqlAggregatePredicateSQLExpression);
    }

    WhereAggregatePredicate<T1> and(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

    default WhereAggregatePredicate<T1> or() {
        return or(true);
    }

    WhereAggregatePredicate<T1> or(boolean condition);

    default WhereAggregatePredicate<T1> or(SQLExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return or(true, sqlAggregatePredicateSQLExpression);
    }

    WhereAggregatePredicate<T1> or(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

}
