package com.easy.query.api4j.select.extension.queryable10;

import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 09:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends ClientQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Queryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> having(SQLExpression10<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>, SQLWhereAggregatePredicate<T9>, SQLWhereAggregatePredicate<T10>> predicateExpression) {
        getClientQueryable10().having((predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8, predicate9, predicate10) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6), new SQLWhereAggregatePredicateImpl<>(predicate7), new SQLWhereAggregatePredicateImpl<>(predicate8), new SQLWhereAggregatePredicateImpl<>(predicate9), new SQLWhereAggregatePredicateImpl<>(predicate10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> having(boolean condition, SQLExpression10<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>, SQLWhereAggregatePredicate<T9>, SQLWhereAggregatePredicate<T10>> predicateExpression) {
        getClientQueryable10().having(condition, (predicate1, predicate2, predicate3, predicate4, predicate5, predicate6, predicate7, predicate8, predicate9, predicate10) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4), new SQLWhereAggregatePredicateImpl<>(predicate5), new SQLWhereAggregatePredicateImpl<>(predicate6), new SQLWhereAggregatePredicateImpl<>(predicate7), new SQLWhereAggregatePredicateImpl<>(predicate8), new SQLWhereAggregatePredicateImpl<>(predicate9), new SQLWhereAggregatePredicateImpl<>(predicate10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> havingMerge(SQLExpression1<Tuple10<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>, SQLWhereAggregatePredicate<T9>, SQLWhereAggregatePredicate<T10>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> havingMerge(boolean condition, SQLExpression1<Tuple10<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>, SQLWhereAggregatePredicate<T5>, SQLWhereAggregatePredicate<T6>, SQLWhereAggregatePredicate<T7>, SQLWhereAggregatePredicate<T8>, SQLWhereAggregatePredicate<T9>, SQLWhereAggregatePredicate<T10>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            predicateExpression.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}
