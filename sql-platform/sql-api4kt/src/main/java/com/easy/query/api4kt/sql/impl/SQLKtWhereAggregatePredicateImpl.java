package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/18 22:41
 */
public class SQLKtWhereAggregatePredicateImpl<T1> implements SQLKtWhereAggregatePredicate<T1> {
    private final WhereAggregatePredicate<T1> whereAggregatePredicate;

    public SQLKtWhereAggregatePredicateImpl(WhereAggregatePredicate<T1> whereAggregatePredicate) {
        this.whereAggregatePredicate = whereAggregatePredicate;
    }

    @Override
    public WhereAggregatePredicate<T1> getWhereAggregatePredicate() {
        return whereAggregatePredicate;
    }

    @Override
    public SQLKtWhereAggregatePredicate<T1> and(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            getWhereAggregatePredicate().and(whereAggregatePredicate -> {
                SQLKtWhereAggregatePredicateImpl<T1> sqlWhereAggregatePredicate = new SQLKtWhereAggregatePredicateImpl<>(whereAggregatePredicate);
                sqlAggregatePredicateSQLExpression.apply(sqlWhereAggregatePredicate);
            });
        }
        return this;
    }

    @Override
    public SQLKtWhereAggregatePredicate<T1> or(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            getWhereAggregatePredicate().or(whereAggregatePredicate -> {
                SQLKtWhereAggregatePredicateImpl<T1> sqlWhereAggregatePredicate = new SQLKtWhereAggregatePredicateImpl<>(whereAggregatePredicate);
                sqlAggregatePredicateSQLExpression.apply(sqlWhereAggregatePredicate);
            });
        }
        return this;
    }

    @Override
    public <T> SQLPropertyNative<T> getSQLPropertyNative() {
        return EasyObjectUtil.typeCastNullable(whereAggregatePredicate);
    }

    @Override
    public SQLKtWhereAggregatePredicate<T1> castTChain() {
        return this;
    }
}
