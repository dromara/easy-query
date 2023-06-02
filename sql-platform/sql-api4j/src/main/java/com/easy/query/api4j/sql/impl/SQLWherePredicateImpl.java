package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

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
}
