package com.easy.query.api.proxy.select.extension.queryable8;

import com.easy.query.api.proxy.select.ProxyQueryable8;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression9;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable8<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> extends ClientProxyQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, ProxyQueryable8Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> {


    default ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupBy(SQLExpression9<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupBy(boolean condition, SQLExpression9<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> selectExpression) {
        if (condition) {
            getClientQueryable8().groupBy((t, t1, t2, t3, t4, t5, t6, t7) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(t.getGroupSelector()), getProxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy());
            });
        }
        return getQueryable8();
    }

    default ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupByMerge(SQLExpression2<ProxyGroupSelector, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> groupByMerge(boolean condition, SQLExpression2<ProxyGroupSelector, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> selectExpression) {
        return groupBy(condition, (groupSelector, t, t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(groupSelector, new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }
}
