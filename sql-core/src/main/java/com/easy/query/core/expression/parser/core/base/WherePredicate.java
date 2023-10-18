package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.SQLFxAvailable;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.expression.parser.core.base.core.filter.AssertPredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.FuncColumnPredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.FuncValuePredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.LikePredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.RangePredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.SelfPredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.SubQueryPredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.ValuePredicate;
import com.easy.query.core.expression.parser.core.base.core.filter.ValuesPredicate;

/**
 * @author xuejiaming
 * @FileName: WherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 */
public interface WherePredicate<T1> extends EntitySQLTableOwner<T1>, SQLFxAvailable, SQLPropertyNative<WherePredicate<T1>>
        , SelfPredicate<T1, WherePredicate<T1>>
        , ValuePredicate<T1, WherePredicate<T1>>
        , FuncValuePredicate<T1, WherePredicate<T1>>
        , FuncColumnPredicate<T1, WherePredicate<T1>>
        , ValuesPredicate<T1, WherePredicate<T1>>
        , RangePredicate<T1, WherePredicate<T1>>
        , LikePredicate<T1, WherePredicate<T1>>
        , AssertPredicate<T1, WherePredicate<T1>>
        , SubQueryPredicate<T1, WherePredicate<T1>> {


    default QueryRuntimeContext getRuntimeContext(){
        return getFilter().getRuntimeContext();
    }
    default WherePredicate<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        return columnFunc(true, columnPropertyFunction, sqlPredicateCompare, val);
    }

    WherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val);


    <T2> WherePredicate<T2> then(WherePredicate<T2> sub);

    default WherePredicate<T1> and() {
        return and(true);
    }

    WherePredicate<T1> and(boolean condition);

    default WherePredicate<T1> and(SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return and(true, sqlWherePredicateSQLExpression);
    }

    WherePredicate<T1> and(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression);

    default <T2> WherePredicate<T1> and(WherePredicate<T2> t2WherePredicate, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return and(true, t2WherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> WherePredicate<T1> and(boolean condition, WherePredicate<T2> t2WherePredicate, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression);

    default WherePredicate<T1> or() {
        return or(true);
    }

    WherePredicate<T1> or(boolean condition);

    default WherePredicate<T1> or(SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        return or(true, sqlWherePredicateSQLExpression);
    }

    WherePredicate<T1> or(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression);

    default <T2> WherePredicate<T1> or(WherePredicate<T2> t2WherePredicate, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression) {
        return or(true, t2WherePredicate, sqlWherePredicateSQLExpression);
    }

    <T2> WherePredicate<T1> or(boolean condition, WherePredicate<T2> t2WherePredicate, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression);

}
