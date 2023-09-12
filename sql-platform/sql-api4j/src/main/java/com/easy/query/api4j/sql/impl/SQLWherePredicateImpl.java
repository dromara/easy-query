package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * @author xuejiaming
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 */
public class SQLWherePredicateImpl<T1> implements SQLWherePredicate<T1> {
    private final WherePredicate<T1> wherePredicate;

    public SQLWherePredicateImpl(WherePredicate<T1> wherePredicate) {
        this.wherePredicate = wherePredicate;
    }

    @Override
    public WherePredicate<T1> getWherePredicate() {
        return wherePredicate;
    }

    @Override
    public SQLWherePredicate<T1> and(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        getWherePredicate().and(condition, predicate -> {
            SQLWherePredicate<T1> sqlPredicate = new SQLWherePredicateImpl<T1>(predicate);
            sqlWherePredicateSQLExpression.apply(sqlPredicate);
        });
        return this;
    }

    @Override
    public SQLWherePredicate<T1> or(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        getWherePredicate().or(condition, predicate -> {
            SQLWherePredicate<T1> sqlPredicate = new SQLWherePredicateImpl<T1>(predicate);
            sqlWherePredicateSQLExpression.apply(sqlPredicate);
        });
        return this;
    }

    @Override
    public <T2> SQLWherePredicate<T1> and(boolean condition, SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        getWherePredicate().and(condition,t2SQLWherePredicate.getWherePredicate(), (predicate1,predicate2) -> {
            SQLWherePredicate<T1> sqlPredicate1 = new SQLWherePredicateImpl<T1>(predicate1);
            SQLWherePredicate<T2> sqlPredicate2 = new SQLWherePredicateImpl<T2>(predicate2);
            sqlWherePredicateSQLExpression.apply(sqlPredicate1,sqlPredicate2);
        });
        return this;
    }

    @Override
    public <T2> SQLWherePredicate<T1> or(boolean condition, SQLWherePredicate<T2> t2SQLWherePredicate, SQLExpression2<SQLWherePredicate<T1>, SQLWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        getWherePredicate().or(condition,t2SQLWherePredicate.getWherePredicate(), (predicate1,predicate2) -> {
            SQLWherePredicate<T1> sqlPredicate1 = new SQLWherePredicateImpl<T1>(predicate1);
            SQLWherePredicate<T2> sqlPredicate2 = new SQLWherePredicateImpl<T2>(predicate2);
            sqlWherePredicateSQLExpression.apply(sqlPredicate1,sqlPredicate2);
        });
        return this;
    }

    @Override
    public <T> SQLPropertyNative<T> getSQLPropertyNative() {
        return EasyObjectUtil.typeCastNullable(wherePredicate);
    }

    @Override
    public SQLWherePredicate<T1> castTChain() {
        return this;
    }
}
