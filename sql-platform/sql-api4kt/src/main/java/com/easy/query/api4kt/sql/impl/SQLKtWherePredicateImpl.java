package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
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

    @Override
    public <T2> SQLKtWherePredicate<T1> and(boolean condition, SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        getWherePredicate().and(condition,t2SQLKtWherePredicate.getWherePredicate() ,(predicate1,predicate2) -> {
            SQLKtWherePredicate<T1> sqlPredicate1 = new SQLKtWherePredicateImpl<T1>(predicate1);
            SQLKtWherePredicate<T2> sqlPredicate2 = new SQLKtWherePredicateImpl<T2>(predicate2);
            sqlWherePredicateSQLExpression.apply(sqlPredicate1,sqlPredicate2);
        });
        return this;
    }

    @Override
    public <T2> SQLKtWherePredicate<T1> or(boolean condition, SQLKtWherePredicate<T2> t2SQLKtWherePredicate, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> sqlWherePredicateSQLExpression) {
        getWherePredicate().or(condition,t2SQLKtWherePredicate.getWherePredicate() ,(predicate1,predicate2) -> {
            SQLKtWherePredicate<T1> sqlPredicate1 = new SQLKtWherePredicateImpl<T1>(predicate1);
            SQLKtWherePredicate<T2> sqlPredicate2 = new SQLKtWherePredicateImpl<T2>(predicate2);
            sqlWherePredicateSQLExpression.apply(sqlPredicate1,sqlPredicate2);
        });
        return this;
    }

    @Override
    public <T> SQLPropertyNative<T> getSQLPropertyNative() {
        return EasyObjectUtil.typeCastNullable(wherePredicate);
    }

    @Override
    public SQLKtWherePredicate<T1> castChain() {
        return this;
    }

}
