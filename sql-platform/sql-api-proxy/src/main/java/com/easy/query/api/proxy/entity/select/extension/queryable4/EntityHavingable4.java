package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityHavingable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        return having(true, predicateExpression);
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition, SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        if (condition) {
            getClientQueryable4().having((t, t1, t2,t3) -> {
                SQLAggregatePredicateExpression sqlAggregatePredicateExpression = predicateExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
                sqlAggregatePredicateExpression.accept(t.getAggregateFilter());
            });
        }
        return getQueryable4();
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> havingMerge(SQLFuncExpression1<Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>,SQLAggregatePredicateExpression> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> havingMerge(boolean condition, SQLFuncExpression1<Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>,SQLAggregatePredicateExpression> predicateExpression) {
        return having(condition, (t,t1, t2, t3) -> {
            return predicateExpression.apply(new Tuple4<>(t,t1, t2, t3));
        });
    }

}
