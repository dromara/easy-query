package com.easy.query.api4kt.select.extension.queryable10;

import com.easy.query.api4kt.select.KtQueryable10;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 09:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends ClientKtQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, KtQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> having(SQLExpression10<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>, SQLKtWhereAggregatePredicate<T10>> predicateExpression) {
        getClientQueryable10().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8, predicate9, predicate10) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7), new SQLKtWhereAggregatePredicateImpl<>(predicate8), new SQLKtWhereAggregatePredicateImpl<>(predicate9), new SQLKtWhereAggregatePredicateImpl<>(predicate10));
        });
        return getQueryable10();
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> having(boolean condition, SQLExpression10<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>, SQLKtWhereAggregatePredicate<T10>> predicateExpression) {
        getClientQueryable10().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8, predicate9, predicate10) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3), new SQLKtWhereAggregatePredicateImpl<>(predicate4), new SQLKtWhereAggregatePredicateImpl<>(predicate5), new SQLKtWhereAggregatePredicateImpl<>(predicate6), new SQLKtWhereAggregatePredicateImpl<>(predicate7), new SQLKtWhereAggregatePredicateImpl<>(predicate8), new SQLKtWhereAggregatePredicateImpl<>(predicate9), new SQLKtWhereAggregatePredicateImpl<>(predicate10));
        });
        return getQueryable10();
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> havingMerge(SQLExpression1<Tuple10<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>, SQLKtWhereAggregatePredicate<T10>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> havingMerge(boolean condition, SQLExpression1<Tuple10<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>, SQLKtWhereAggregatePredicate<T4>, SQLKtWhereAggregatePredicate<T5>, SQLKtWhereAggregatePredicate<T6>, SQLKtWhereAggregatePredicate<T7>, SQLKtWhereAggregatePredicate<T8>, SQLKtWhereAggregatePredicate<T9>, SQLKtWhereAggregatePredicate<T10>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            predicateExpression.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}
