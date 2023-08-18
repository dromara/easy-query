package com.easy.query.api4kt.select.extension.queryable2;

import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWhereAggregatePredicateImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable2<T1, T2> extends ClientKtQueryable2Available<T1, T2>, KtQueryable2Available<T1, T2> {

    default KtQueryable2<T1, T2> having(SQLExpression2<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having((predicate1, predicate2) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> having(boolean condition, SQLExpression2<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>> predicateExpression) {
        getClientQueryable2().having(condition, (predicate1, predicate2) -> {
            predicateExpression.apply(new SQLKtWhereAggregatePredicateImpl<>(predicate1), new SQLKtWhereAggregatePredicateImpl<>(predicate2));
        });
        return getQueryable2();
    }
    default KtQueryable2<T1, T2> havingMerge(SQLExpression1<Tuple2<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default KtQueryable2<T1, T2> havingMerge(boolean condition, SQLExpression1<Tuple2<SQLKtWhereAggregatePredicate<T1>, SQLKtWhereAggregatePredicate<T2>>> predicateExpression){
        return having(condition,(t1, t2)->{
            predicateExpression.apply(new Tuple2<>(t1, t2));
        });
    }

}
