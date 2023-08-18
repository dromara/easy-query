package com.easy.query.api4j.select.extension.queryable5;

import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable5<T1, T2, T3, T4, T5> extends ClientQueryable5Available<T1, T2, T3, T4, T5>, Queryable5Available<T1, T2, T3, T4, T5> {

    default Queryable5<T1, T2, T3, T4, T5> having(SQLExpression5<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>> predicateExpression) {
        getClientQueryable5().having((predicate1, predicate2, predicate3, predicate4, predicate5) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> having(boolean condition, SQLExpression5<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>> predicateExpression) {
        getClientQueryable5().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> havingMerge(SQLExpression1<Tuple5<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable5<T1, T2, T3, T4, T5> havingMerge(boolean condition, SQLExpression1<Tuple5<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5) -> {
            predicateExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}