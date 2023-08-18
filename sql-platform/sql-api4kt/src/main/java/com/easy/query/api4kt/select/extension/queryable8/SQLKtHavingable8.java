package com.easy.query.api4kt.select.extension.queryable8;

import com.easy.query.api4kt.select.KtQueryable8;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientKtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, KtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(SQLExpression8<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>> predicateExpression) {
        getClientQueryable8().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7), new SQLKtWhereAggregatePredicateImpl<>(predicate8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(boolean condition, SQLExpression8<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>> predicateExpression) {
        getClientQueryable8().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7), new SQLKtWhereAggregatePredicateImpl<>(predicate8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> havingMerge(SQLExpression1<Tuple8<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> havingMerge(boolean condition, SQLExpression1<Tuple8<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            predicateExpression.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }
}
