package com.easy.query.api.proxy.entity.select.extension.queryable5;

import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression5;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityHavingable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientEntityQueryable5Available<T1, T2, T3, T4, T5>, EntityQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> having(SQLFuncExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        return having(true, predicateExpression);
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> having(boolean condition, SQLFuncExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        if (condition) {
            getClientQueryable5().having((t, t1, t2, t3, t4) -> {
                SQLAggregatePredicateExpression sqlAggregatePredicateExpression = predicateExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy());
                sqlAggregatePredicateExpression.accept(t.getAggregateFilter());
            });
        }
        return getQueryable5();
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> havingMerge(SQLFuncExpression1<Tuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>,SQLAggregatePredicateExpression> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> havingMerge(boolean condition, SQLFuncExpression1<Tuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>,SQLAggregatePredicateExpression> predicateExpression) {
        return having(condition, (t, t1, t2, t3, t4) -> {
            return predicateExpression.apply(new Tuple5<>(t, t1, t2, t3, t4));
        });
    }

}
