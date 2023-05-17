package com.easy.query.core.expression.parser.core;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @author xuejiaming
 * @FileName: AggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:17
 */
public interface SQLAggregatePredicate<T1> {
    TableAvailable getTable();
    EasyQueryRuntimeContext getRuntimeContext();

    default SQLAggregatePredicate<T1> avg(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return avg(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> avg(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false), column, compare, val);
    }
    default SQLAggregatePredicate<T1> avgDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return avgDistinct(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> avgDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true), column, compare, val);
    }

    default SQLAggregatePredicate<T1> min(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return min(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> min(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMinFunction(), column, compare, val);
    }

    default SQLAggregatePredicate<T1> max(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return max(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> max(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createMaxFunction(), column, compare, val);
    }

    default SQLAggregatePredicate<T1> sum(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return sum(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> sum(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(false), column, compare, val);
    }
    default SQLAggregatePredicate<T1> sumDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return sum(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> sumDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createSumFunction(true), column, compare, val);
    }

    default SQLAggregatePredicate<T1> countDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return countDistinct(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> countDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(true), column, compare, val);
    }

    default SQLAggregatePredicate<T1> count(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return count(true, column, compare, val);
    }

    default SQLAggregatePredicate<T1> count(boolean condition, Property<T1, ?> column, SQLPredicateCompare compare, Object val) {
        return func(condition, getRuntimeContext().getColumnFunctionFactory().createCountFunction(false), column, compare, val);
    }

    SQLAggregatePredicate<T1> func(boolean condition, ColumnFunction columnFunction, Property<T1, ?> column, SQLPredicateCompare compare, Object val);


    <T2> SQLAggregatePredicate<T2> then(SQLAggregatePredicate<T2> sub);

    default SQLAggregatePredicate<T1> and() {
        return and(true);
    }

    SQLAggregatePredicate<T1> and(boolean condition);

    default SQLAggregatePredicate<T1> and(SQLExpression<SQLAggregatePredicate<T1>> predicateSqlExpression) {
        return and(true, predicateSqlExpression);
    }

    SQLAggregatePredicate<T1> and(boolean condition, SQLExpression<SQLAggregatePredicate<T1>> predicateSqlExpression);

    default SQLAggregatePredicate<T1> or() {
        return or(true);
    }

    SQLAggregatePredicate<T1> or(boolean condition);

    default SQLAggregatePredicate<T1> or(SQLExpression<SQLAggregatePredicate<T1>> predicateSqlExpression) {
        return or(true, predicateSqlExpression);
    }

    SQLAggregatePredicate<T1> or(boolean condition, SQLExpression<SQLAggregatePredicate<T1>> predicateSqlExpression);

}
