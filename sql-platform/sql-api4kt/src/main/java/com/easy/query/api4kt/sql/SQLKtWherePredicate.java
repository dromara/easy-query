package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.sql.core.SQLLambdaKtNative;
import com.easy.query.api4kt.sql.core.available.SQLKtLambdaFuncAvailable;
import com.easy.query.api4kt.sql.core.filter.SQLKtAssertPredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtFuncValuePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtLikePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtRangePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtSelfPredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtSubQueryPredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtValuePredicate;
import com.easy.query.api4kt.sql.core.filter.SQLKtValuesPredicate;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
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
public interface SQLKtWherePredicate<T1> extends EntitySQLTableOwner<T1>, SQLKtLambdaFuncAvailable<T1>, SQLLambdaKtNative<T1, SQLKtWherePredicate<T1>>
        , SQLKtAssertPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtRangePredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtSelfPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtSubQueryPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtFuncValuePredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtValuePredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtValuesPredicate<T1, SQLKtWherePredicate<T1>>
        , SQLKtLikePredicate<T1, SQLKtWherePredicate<T1>> {
    WherePredicate<T1> getWherePredicate();

    default TableAvailable getTable() {
        return getWherePredicate().getTable();
    }
    default QueryRuntimeContext getRuntimeContext() {
        return getWherePredicate().getRuntimeContext();
    }

    default <TProperty> SQLKtWherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    default <TProperty> SQLKtWherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, TProperty val) {
        getWherePredicate().columnFunc(condition, columnPropertyFunction, sqlPredicateCompare, val);
        return this;
    }

    default <T2> SQLKtWherePredicate<T2> then(SQLKtWherePredicate<T2> sub) {
        getWherePredicate().then(sub.getWherePredicate());
        return sub;
    }

    default SQLKtWherePredicate<T1> and() {
        return and(true);
    }

    default SQLKtWherePredicate<T1> and(boolean condition) {
        getWherePredicate().and(condition);
        return this;
    }

    default SQLKtWherePredicate<T1> and(SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    SQLKtWherePredicate<T1> and(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression);

    default <T2> SQLKtWherePredicate<T1> and(SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return and(true, t2SQLKtWherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> SQLKtWherePredicate<T1> and(boolean condition, SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression);

    default SQLKtWherePredicate<T1> or() {
        return or(true);
    }

    default SQLKtWherePredicate<T1> or(boolean condition) {
        getWherePredicate().or(condition);
        return this;
    }

    default SQLKtWherePredicate<T1> or(SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    SQLKtWherePredicate<T1> or(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression);


    default <T2> SQLKtWherePredicate<T1> or(SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return or(true, t2SQLKtWherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> SQLKtWherePredicate<T1> or(boolean condition, SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression);
}
