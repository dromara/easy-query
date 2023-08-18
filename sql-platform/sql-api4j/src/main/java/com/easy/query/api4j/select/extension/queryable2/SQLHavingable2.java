package com.easy.query.api4j.select.extension.queryable2;

import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.impl.SQLWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHavingable2<T1, T2> extends ClientQueryable2Available<T1, T2>, Queryable2Available<T1, T2> {

    default Queryable2<T1, T2> having(SQLExpression2<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having((predicate1, predicate2) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2));
        });
        return getQueryable2();
    }

    default Queryable2<T1, T2> having(boolean condition, SQLExpression2<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having(condition, (predicate1, predicate2) -> {
            predicateExpression.apply(new SQLWhereAggregatePredicateImpl<>(predicate1), new SQLWhereAggregatePredicateImpl<>(predicate2));
        });
        return getQueryable2();
    }
    default Queryable2<T1, T2> havingMerge(SQLExpression1<Tuple2<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default Queryable2<T1, T2> havingMerge(boolean condition, SQLExpression1<Tuple2<SQLWhereAggregatePredicate<T1>, SQLWhereAggregatePredicate<T2>>> predicateExpression){
        return having(condition,(t1, t2)->{
            predicateExpression.apply(new Tuple2<>(t1, t2));
        });
    }

}
