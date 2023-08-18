package com.easy.query.api4j.select.extension.queryable3;

import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable3<T1, T2,T3> extends ClientQueryable3Available<T1, T2,T3>, Queryable3Available<T1, T2,T3> {

    default Queryable3<T1, T2,T3> having(SQLExpression3<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2,T3> having(boolean condition, SQLExpression3<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having(condition, (predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2), new SQLWhereAggregatePredicateImpl<>(predicate3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2,T3> havingMerge(SQLExpression1<Tuple3<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable3<T1, T2,T3> havingMerge(boolean condition, SQLExpression1<Tuple3<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>, SQLWhereAggregatePredicate<T3>>> predicateExpression) {
        return having(condition, (t, t1, t2) -> {
            predicateExpression.apply(new Tuple3<>(t, t1, t2));
        });
    }
}
