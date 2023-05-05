package com.easy.query.core.expression.parser.core;

import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @author xuejiaming
 * @FileName: AggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:17
 */
public interface SqlAggregatePredicate<T1> {
    TableAvailable getTable();

    default SqlAggregatePredicate<T1> avg(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return avg(true, column, compare, val);
    }

    default SqlAggregatePredicate<T1> avg(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, EasyAggregate.MIN, column, compare, val);
    }

    default SqlAggregatePredicate<T1> min(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return min(true, column, compare, val);
    }

    default SqlAggregatePredicate<T1> min(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, EasyAggregate.MIN, column, compare, val);
    }

    default SqlAggregatePredicate<T1> max(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return max(true, column, compare, val);
    }

    default SqlAggregatePredicate<T1> max(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, EasyAggregate.MAX, column, compare, val);
    }

    default SqlAggregatePredicate<T1> sum(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return sum(true, column, compare, val);
    }

    default SqlAggregatePredicate<T1> sum(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, EasyAggregate.SUM, column, compare, val);
    }

    default SqlAggregatePredicate<T1> countDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return countDistinct(true, column, compare, val);
    }

    default SqlAggregatePredicate<T1> countDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return func(condition, EasyAggregate.COUNT_DISTINCT, column, compare, val);
    }

    default SqlAggregatePredicate<T1> count(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return count(true, column, compare, val);
    }

    default SqlAggregatePredicate<T1> count(boolean condition, Property<T1, ?> column, SqlPredicateCompare compare, Object val) {
        return func(condition, EasyAggregate.COUNT, column, compare, val);
    }

    SqlAggregatePredicate<T1> func(boolean condition, EasyFunc easyAggregate, Property<T1, ?> column, SqlPredicateCompare compare, Object val);


    <T2> SqlAggregatePredicate<T2> then(SqlAggregatePredicate<T2> sub);

    default SqlAggregatePredicate<T1> and() {
        return and(true);
    }

    SqlAggregatePredicate<T1> and(boolean condition);

    default SqlAggregatePredicate<T1> and(SqlExpression<SqlAggregatePredicate<T1>> predicateSqlExpression) {
        return and(true, predicateSqlExpression);
    }

    SqlAggregatePredicate<T1> and(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateSqlExpression);

    default SqlAggregatePredicate<T1> or() {
        return or(true);
    }

    SqlAggregatePredicate<T1> or(boolean condition);

    default SqlAggregatePredicate<T1> or(SqlExpression<SqlAggregatePredicate<T1>> predicateSqlExpression) {
        return or(true, predicateSqlExpression);
    }

    SqlAggregatePredicate<T1> or(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateSqlExpression);

}
