package com.easy.query.api4kt.select.extension.queryable7;

import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable7<T1, T2, T3, T4, T5, T6, T7> extends ClientKtQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, KtQueryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> having(SQLExpression7<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>> predicateExpression) {
        getClientQueryable7().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> having(boolean condition, SQLExpression7<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>> predicateExpression) {
        getClientQueryable7().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> havingMerge(SQLExpression1<Tuple7<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> havingMerge(boolean condition, SQLExpression1<Tuple7<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            predicateExpression.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}