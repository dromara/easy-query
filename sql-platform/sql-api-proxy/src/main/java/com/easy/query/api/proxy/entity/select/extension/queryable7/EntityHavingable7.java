package com.easy.query.api.proxy.entity.select.extension.queryable7;

import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.core.common.tuple.MergeTuple7;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression7;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityHavingable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ClientEntityQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, EntityQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> having(SQLFuncExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        return having(true, predicateExpression);
    }

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> having(boolean condition, SQLFuncExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        if (condition) {
            getClientQueryable7().having((t, t1, t2, t3, t4, t5, t6) -> {
                SQLAggregatePredicateExpression sqlAggregatePredicateExpression = predicateExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy());
                sqlAggregatePredicateExpression.accept(t.getAggregateFilter());
            });
        }
        return getQueryable7();
    }

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> havingMerge(SQLFuncExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>,SQLAggregatePredicateExpression> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> havingMerge(boolean condition, SQLFuncExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>,SQLAggregatePredicateExpression> predicateExpression) {
        return having(condition, (t, t1, t2, t3, t4, t5, t6) -> {
            return predicateExpression.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

}
