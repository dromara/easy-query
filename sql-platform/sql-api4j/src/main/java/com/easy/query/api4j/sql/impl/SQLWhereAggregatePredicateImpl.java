package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/18 22:41
 */
public class SQLWhereAggregatePredicateImpl<T1> implements SQLWhereAggregatePredicate<T1> {
    private final WhereAggregatePredicate<T1> whereAggregatePredicate;

    public SQLWhereAggregatePredicateImpl(WhereAggregatePredicate<T1> whereAggregatePredicate) {
        this.whereAggregatePredicate = whereAggregatePredicate;
    }

    @Override
    public WhereAggregatePredicate<T1> getWhereAggregatePredicate() {
        return whereAggregatePredicate;
    }

    @Override
    public SQLWhereAggregatePredicate<T1> and(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            getWhereAggregatePredicate().and(whereAggregatePredicate -> {
                SQLWhereAggregatePredicateImpl<T1> sqlWhereAggregatePredicate = new SQLWhereAggregatePredicateImpl<>(whereAggregatePredicate);
                sqlAggregatePredicateSQLExpression.apply(sqlWhereAggregatePredicate);
            });
        }
        return this;
    }

    @Override
    public SQLWhereAggregatePredicate<T1> or(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            getWhereAggregatePredicate().or(whereAggregatePredicate -> {
                SQLWhereAggregatePredicateImpl<T1> sqlWhereAggregatePredicate = new SQLWhereAggregatePredicateImpl<>(whereAggregatePredicate);
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
    public SQLWhereAggregatePredicate<T1> castChain() {
        return this;
    }
}
