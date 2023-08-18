package com.easy.query.api4kt.select.extension.queryable5;

import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable5<T1, T2, T3, T4, T5> extends ClientKtQueryable5Available<T1, T2, T3, T4, T5>, KtQueryable5Available<T1, T2, T3, T4, T5> {

    default KtQueryable5<T1, T2, T3, T4, T5> having(SQLExpression5<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>> predicateExpression) {
        getClientQueryable5().having((predicate1, predicate2, predicate3, predicate4, predicate5) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> having(boolean condition, SQLExpression5<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>> predicateExpression) {
        getClientQueryable5().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> havingMerge(SQLExpression1<Tuple5<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable5<T1, T2, T3, T4, T5> havingMerge(boolean condition, SQLExpression1<Tuple5<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5) -> {
            predicateExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}