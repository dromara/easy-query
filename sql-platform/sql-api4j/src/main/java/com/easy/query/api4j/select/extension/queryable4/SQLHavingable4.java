package com.easy.query.api4j.select.extension.queryable4;

import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable4<T1, T2,T3,T4> extends ClientQueryable4Available<T1, T2,T3,T4>, Queryable4Available<T1, T2,T3,T4> {

    default Queryable4<T1, T2,T3,T4> having(SQLExpression4<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having((predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2,T3,T4> having(boolean condition, SQLExpression4<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>> predicateExpression) {
        getClientQueryable4().having(condition, (predicate1, predicate2, predicate3, predicate4) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3), new SQLWhereAggregatePredicateImpl<>(predicate4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2,T3,T4> havingMerge(SQLExpression1<Tuple4<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable4<T1, T2,T3,T4> havingMerge(boolean condition, SQLExpression1<Tuple4<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>, SQLWhereAggregatePredicate<T4>>> predicateExpression) {
        return having(condition, (t, t1, t2,t3) -> {
            predicateExpression.apply(new Tuple4<>(t, t1, t2, t3));
        });
    }
}
