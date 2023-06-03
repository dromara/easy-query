package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * @author xuejiaming
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 */
public class SQLKtWherePredicateImpl<T1> implements SQLKtWherePredicate<T1> {
    private final WherePredicate<T1> wherePredicate;

    public SQLKtWherePredicateImpl(WherePredicate<T1> wherePredicate) {
        this.wherePredicate = wherePredicate;
    }

    @Override
    public WherePredicate<T1> getWherePredicate() {
        return wherePredicate;
    }

    @Override
    public SQLKtWherePredicate<T1> and(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        getWherePredicate().and(condition, predicate -> {
            SQLKtWherePredicate<T1> sqlPredicate = new SQLKtWherePredicateImpl<T1>(predicate);
            sqlWherePredicateSQLExpression.apply(sqlPredicate);
        });
        return this;
    }

    @Override
    public SQLKtWherePredicate<T1> or(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        getWherePredicate().or(condition, predicate -> {
            SQLKtWherePredicate<T1> sqlPredicate = new SQLKtWherePredicateImpl<T1>(predicate);
            sqlWherePredicateSQLExpression.apply(sqlPredicate);
        });
        return this;
    }
}
