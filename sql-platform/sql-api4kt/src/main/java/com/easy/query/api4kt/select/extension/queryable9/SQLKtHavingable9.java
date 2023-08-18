package com.easy.query.api4kt.select.extension.queryable9;

import com.easy.query.api4kt.select.KtQueryable9;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientKtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, KtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> having(SQLExpression9<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>> predicateExpression) {
        getClientQueryable9().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8, predicate9) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7), new SQLKtWhereAggregatePredicateImpl<>(predicate8), new SQLKtWhereAggregatePredicateImpl<>(predicate9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> having(boolean condition, SQLExpression9<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>> predicateExpression) {
        getClientQueryable9().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8, predicate9) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7), new SQLKtWhereAggregatePredicateImpl<>(predicate8), new SQLKtWhereAggregatePredicateImpl<>(predicate9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> havingMerge(SQLExpression1<Tuple9<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> havingMerge(boolean condition, SQLExpression1<Tuple9<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            predicateExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
