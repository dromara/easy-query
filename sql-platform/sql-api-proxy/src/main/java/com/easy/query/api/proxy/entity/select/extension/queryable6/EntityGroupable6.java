package com.easy.query.api.proxy.entity.select.extension.queryable6;

import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression6;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientEntityQueryable6Available<T1, T2, T3, T4, T5, T6>, EntityQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {


    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupBy(SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, SQLGroupByExpression> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupBy(boolean condition, SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, SQLGroupByExpression> selectExpression) {
        if (condition) {
            getClientQueryable6().groupBy((t, t1, t2, t3, t4, t5) -> {
                SQLGroupByExpression sqlGroupByExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
                sqlGroupByExpression.accept(t.getGroupSelector());
            });
        }
        return getQueryable6();
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupByMerge(SQLFuncExpression1<Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>,SQLGroupByExpression> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupByMerge(boolean condition, SQLFuncExpression1<Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>,SQLGroupByExpression> selectExpression) {
        return groupBy(condition, (t, t1, t2, t3, t4,t5) -> {
            return selectExpression.apply(new Tuple6<>(t, t1, t2, t3, t4,t5));
        });
    }
}
