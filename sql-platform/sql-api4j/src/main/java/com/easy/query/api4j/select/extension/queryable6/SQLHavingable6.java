package com.easy.query.api4j.select.extension.queryable6;

import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable6<T1, T2, T3, T4, T5, T6> extends ClientQueryable6Available<T1, T2, T3, T4, T5, T6>, Queryable6Available<T1, T2, T3, T4, T5, T6> {

    default Queryable6<T1, T2, T3, T4, T5, T6> having(SQLExpression6<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>> predicateExpression) {
        getClientQueryable6().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> having(boolean condition, SQLExpression6<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>> predicateExpression) {
        getClientQueryable6().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> havingMerge(SQLExpression1<Tuple6<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> havingMerge(boolean condition, SQLExpression1<Tuple6<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6) -> {
            predicateExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}