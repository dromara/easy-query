package com.easy.query.api4kt.select.extension.queryable3;

import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable3<T1, T2,T3> extends ClientKtQueryable3Available<T1, T2,T3>, KtQueryable3Available<T1, T2,T3> {

    default KtQueryable3<T1, T2,T3> having(SQLExpression3<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2,T3> having(boolean condition, SQLExpression3<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>> predicateExpression) {
        getClientQueryable3().having(condition, (predicate1, predicate2, predicate3) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2), new SQLKtWhereAggregatePredicateImpl<>(predicate3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2,T3> havingMerge(SQLExpression1<Tuple3<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable3<T1, T2,T3> havingMerge(boolean condition, SQLExpression1<Tuple3<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>, SQLKtWhereAggregatePredicate<T3>>> predicateExpression) {
        return having(condition, (t1, t2,t3) -> {
            predicateExpression.apply(new Tuple3<>(t1, t2,t3));
        });
    }
}
