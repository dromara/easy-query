package com.easy.query.api4kt.select.extension.queryable6;

import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable6<T1, T2, T3, T4, T5, T6> extends ClientKtQueryable6Available<T1, T2, T3, T4, T5, T6>, KtQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default KtQueryable6<T1, T2, T3, T4, T5, T6> having(SQLExpression6<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>> predicateExpression) {
        getClientQueryable6().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> having(boolean condition, SQLExpression6<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>> predicateExpression) {
        getClientQueryable6().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> havingMerge(SQLExpression1<Tuple6<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> havingMerge(boolean condition, SQLExpression1<Tuple6<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6) -> {
            predicateExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}