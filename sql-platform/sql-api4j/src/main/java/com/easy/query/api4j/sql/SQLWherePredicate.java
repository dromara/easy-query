package com.easy.query.api4j.sql;

import com.easy.query.api4j.sql.core.SQLLambdaNative;
import com.easy.query.api4j.sql.core.available.LambdaSQLFuncAvailable;
import com.easy.query.api4j.sql.core.filter.SQLAssertPredicate;
import com.easy.query.api4j.sql.core.filter.SQLFuncColumnPredicate;
import com.easy.query.api4j.sql.core.filter.SQLFuncValuePredicate;
import com.easy.query.api4j.sql.core.filter.SQLLikePredicate;
import com.easy.query.api4j.sql.core.filter.SQLRangePredicate;
import com.easy.query.api4j.sql.core.filter.SQLSelfPredicate;
import com.easy.query.api4j.sql.core.filter.SQLSubQueryPredicate;
import com.easy.query.api4j.sql.core.filter.SQLValuePredicate;
import com.easy.query.api4j.sql.core.filter.SQLValuesPredicate;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface SQLWherePredicate<T1> extends EntitySQLTableOwner<T1>, LambdaSQLFuncAvailable<T1>, SQLLambdaNative<T1, SQLWherePredicate<T1>>
        , SQLLikePredicate<T1, SQLWherePredicate<T1>>
        , SQLRangePredicate<T1, SQLWherePredicate<T1>>
        , SQLSelfPredicate<T1, SQLWherePredicate<T1>>
        , SQLFuncColumnPredicate<T1, SQLWherePredicate<T1>>
        , SQLFuncValuePredicate<T1, SQLWherePredicate<T1>>
        , SQLValuePredicate<T1, SQLWherePredicate<T1>>
        , SQLValuesPredicate<T1, SQLWherePredicate<T1>>
        , SQLSubQueryPredicate<T1, SQLWherePredicate<T1>>
        , SQLAssertPredicate<T1, SQLWherePredicate<T1>> {
    WherePredicate<T1> getWherePredicate();

    default TableAvailable getTable() {
        return getWherePredicate().getTable();
    }

    default QueryRuntimeContext getRuntimeContext() {
        return getWherePredicate().getRuntimeContext();
    }


    default <TProperty> SQLWherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default <TProperty> SQLWherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        getWherePredicate().columnFunc(condition, columnPropertyFunction, sqlPredicateCompare, val);
        return this;
    }


    default <T2> SQLWherePredicate<T2> then(SQLWherePredicate<T2> sub) {
        getWherePredicate().then(sub.getWherePredicate());
        return sub;
    }

    default SQLWherePredicate<T1> and() {
        return and(true);
    }

    default SQLWherePredicate<T1> and(boolean condition) {
        getWherePredicate().and(condition);
        return this;
    }

    default SQLWherePredicate<T1> and(SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    SQLWherePredicate<T1> and(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression);

    default <T2> SQLWherePredicate<T1> and(SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return and(true, t2SQLWherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> SQLWherePredicate<T1> and(boolean condition, SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression);

    default SQLWherePredicate<T1> or() {
        return or(true);
    }

    default SQLWherePredicate<T1> or(boolean condition) {
        getWherePredicate().or(condition);
        return this;
    }

    default SQLWherePredicate<T1> or(SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    SQLWherePredicate<T1> or(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression);

    default <T2> SQLWherePredicate<T1> or(SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return or(true, t2SQLWherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> SQLWherePredicate<T1> or(boolean condition, SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression);

    @Override
    default SQLWherePredicate<T1> isBank(boolean condition, Property<T1, String> column) {
        getWherePredicate().isBank(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    @Override
    default SQLWherePredicate<T1> isNotBank(boolean condition, Property<T1, String> column) {
        getWherePredicate().isNotBank(condition, EasyLambdaUtil.getPropertyName(column));
        return this;
    }
}
