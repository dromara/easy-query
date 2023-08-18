package com.easy.query.api4j.select.extension.queryable7;

import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable7<T1, T2, T3, T4, T5, T6, T7> extends ClientQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, Queryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> having(SQLExpression7<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>> predicateExpression) {
        getClientQueryable7().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6), new SQLWhereAggregatePredicateImpl<>(predicate7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> having(boolean condition, SQLExpression7<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>> predicateExpression) {
        getClientQueryable7().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6), new SQLWhereAggregatePredicateImpl<>(predicate7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> havingMerge(SQLExpression1<Tuple7<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> havingMerge(boolean condition, SQLExpression1<Tuple7<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            predicateExpression.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}