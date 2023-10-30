package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.sql.core.SQLLambdaKtNative;
import com.easy.query.api4kt.sql.core.available.SQLKtLambdaFuncAvailable;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import kotlin.reflect.KProperty1;

/**
 * @author xuejiaming
 * @FileName: AggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:17
 */
public interface SQLKtWhereAggregatePredicate<T1> extends EntitySQLTableOwner<T1>, SQLKtLambdaFuncAvailable<T1>, SQLLambdaKtNative<T1,SQLKtWhereAggregatePredicate<T1>> {
    WhereAggregatePredicate<T1> getWhereAggregatePredicate();

    default TableAvailable getTable() {
        return getWhereAggregatePredicate().getTable();
    }

    default QueryRuntimeContext getRuntimeContext() {
        return getWhereAggregatePredicate().getRuntimeContext();
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> avg(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return avg(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> avg(boolean condition, KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().avg(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> avgDistinct(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return avgDistinct(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> avgDistinct(boolean condition, KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().avgDistinct(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> min(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return min(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> min(boolean condition, KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().min(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> max(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return max(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> max(boolean condition, KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().max(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> sum(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return sum(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> sum(boolean condition, KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().sum(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> sumDistinct(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return sumDistinct(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> sumDistinct(boolean condition, KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().sumDistinct(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> countDistinct(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return countDistinct(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> countDistinct(boolean condition, KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().countDistinct(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> count(KProperty1<? super T1, TProperty> column, AggregatePredicateCompare compare, TProperty val) {
        return count(true, column, compare, val);
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> count(boolean condition, KProperty1<? super T1, TProperty> column, SQLPredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().count(condition, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <TProperty> SQLKtWhereAggregatePredicate<T1> func(boolean condition, ColumnFunction columnFunction, KProperty1<? super T1, TProperty> column, SQLPredicateCompare compare, TProperty val) {
        getWhereAggregatePredicate().func(condition, columnFunction, EasyKtLambdaUtil.getPropertyName(column), compare, val);
        return this;
    }

    default <T2> SQLKtWhereAggregatePredicate<T2> then(SQLKtWhereAggregatePredicate<T2> sub) {
        getWhereAggregatePredicate().then(sub.getWhereAggregatePredicate());
        return sub;
    }

    default SQLKtWhereAggregatePredicate<T1> and() {
        return and(true);
    }

    default SQLKtWhereAggregatePredicate<T1> and(boolean condition) {
        getWhereAggregatePredicate().and(condition);
        return this;
    }

    default SQLKtWhereAggregatePredicate<T1> and(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return and(true, sqlAggregatePredicateSQLExpression);
    }

    SQLKtWhereAggregatePredicate<T1> and(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

    default SQLKtWhereAggregatePredicate<T1> or() {
        return or(true);
    }

    default SQLKtWhereAggregatePredicate<T1> or(boolean condition) {
        getWhereAggregatePredicate().or(condition);
        return this;
    }

    default SQLKtWhereAggregatePredicate<T1> or(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return or(true, sqlAggregatePredicateSQLExpression);
    }

    SQLKtWhereAggregatePredicate<T1> or(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

}
