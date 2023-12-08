package com.easy.query.api.proxy.entity.select.extension.queryable7;

import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.core.common.tuple.MergeTuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ClientEntityQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, EntityQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> orderBy(SQLExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> selectExpression) {
        return orderBy(true, selectExpression);
    }

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> orderBy(boolean condition, SQLExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> selectExpression) {
        if (condition) {
            getClientQueryable7().orderByAsc((t, t1, t2, t3, t4, t5, t6) -> {
                get1Proxy().getEntitySQLContext()._orderBy(t.getOrderSelector(),()->{
                    selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy());
                });
            });
        }
        return getQueryable7();
    }

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> orderByMerge(SQLExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> selectExpression) {
        return orderByMerge(true, selectExpression);
    }

    default EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> orderByMerge(boolean condition, SQLExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> selectExpression) {
        return orderBy(condition, (t, t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

}
