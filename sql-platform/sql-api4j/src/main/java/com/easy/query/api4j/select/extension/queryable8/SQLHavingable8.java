package com.easy.query.api4j.select.extension.queryable8;

import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, Queryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(SQLExpression8<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>> predicateExpression) {
        getClientQueryable8().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6), new SQLWhereAggregatePredicateImpl<>(predicate7), new SQLWhereAggregatePredicateImpl<>(predicate8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(boolean condition, SQLExpression8<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>> predicateExpression) {
        getClientQueryable8().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6), new SQLWhereAggregatePredicateImpl<>(predicate7), new SQLWhereAggregatePredicateImpl<>(predicate8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> havingMerge(SQLExpression1<Tuple8<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> havingMerge(boolean condition, SQLExpression1<Tuple8<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>>> predicateExpression) {
        return having(condition, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            predicateExpression.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }
}
