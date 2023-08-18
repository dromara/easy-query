package com.easy.query.api4kt.select.extension.queryable4;

import com.easy.query.api4kt.select.KtQueryable4;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable4<T1, T2, T3, T4> extends ClientKtQueryable4Available<T1, T2, T3, T4>, KtQueryable4Available<T1, T2, T3, T4> {

    default KtQueryable4<T1, T2, T3, T4> having(SQLExpression4<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having((predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> having(boolean condition, SQLExpression4<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having(condition, (predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> havingMerge(SQLExpression1<Tuple4<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable4<T1, T2, T3, T4> havingMerge(boolean condition, SQLExpression1<Tuple4<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4) -> {
            predicateExpression.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }
}
