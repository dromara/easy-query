package com.easy.query.api4j.sql;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * @author xuejiaming
 * @FileName: AggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:17
 */
public interface SQLWhereAggregatePredicate<T1> extends EntitySQLTableOwner<T1> {
    WhereAggregatePredicate<T1> getWhereAggregatePredicate();

    default TableAvailable getTable() {
        return getWhereAggregatePredicate().getTable();
    }

    default QueryRuntimeContext getRuntimeContext() {
        return getWhereAggregatePredicate().getRuntimeContext();
    }

    default SQLWhereAggregatePredicate<T1> avg(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return avg(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> avg(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        getWhereAggregatePredicate().avg(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> avgDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return avgDistinct(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> avgDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        getWhereAggregatePredicate().avgDistinct(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> min(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return min(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> min(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        getWhereAggregatePredicate().min(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> max(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return max(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> max(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        getWhereAggregatePredicate().max(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> sum(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return sum(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> sum(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        getWhereAggregatePredicate().sum(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> sumDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return sum(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> sumDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        getWhereAggregatePredicate().sumDistinct(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> countDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return countDistinct(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> countDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        getWhereAggregatePredicate().countDistinct(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> count(Property<T1, ?> column, AggregatePredicateCompare compare, Object val) {
        return count(true, column, compare, val);
    }

    default SQLWhereAggregatePredicate<T1> count(boolean condition, Property<T1, ?> column, SQLPredicateCompare compare, Object val) {
        getWhereAggregatePredicate().count(condition, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> func(boolean condition, ColumnFunction columnFunction, Property<T1, ?> column, SQLPredicateCompare compare, Object val) {
        getWhereAggregatePredicate().func(condition, columnFunction, EasyLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }


    default <T2> SQLWhereAggregatePredicate<T2> then(SQLWhereAggregatePredicate<T2> sub) {
        getWhereAggregatePredicate().then(sub.getWhereAggregatePredicate());
        return sub;
    }

    default SQLWhereAggregatePredicate<T1> and() {
        return and(true);
    }

    default SQLWhereAggregatePredicate<T1> and(boolean condition) {
        getWhereAggregatePredicate().and(true);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> and(SQLExpression1<SQLWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return and(true, sqlAggregatePredicateSQLExpression);
    }

    SQLWhereAggregatePredicate<T1> and(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

    default SQLWhereAggregatePredicate<T1> or() {
        return or(true);
    }

    default SQLWhereAggregatePredicate<T1> or(boolean condition) {
        getWhereAggregatePredicate().or(condition);
        return this;
    }

    default SQLWhereAggregatePredicate<T1> or(SQLExpression1<SQLWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return or(true, sqlAggregatePredicateSQLExpression);
    }

    SQLWhereAggregatePredicate<T1> or(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

}
