package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable6;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientProxyQueryable6Available<T1, T2, T3, T4, T5, T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {


    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupBy(SQLExpression7<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupBy(boolean condition, SQLExpression7<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> selectExpression) {
        if (condition) {
            getClientQueryable6().groupBy((t, t1, t2, t3, t4, t5) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(t.getGroupSelector()), getProxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
            });
        }
        return getQueryable6();
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupByMerge(SQLExpression2<ProxyGroupSelector, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> groupByMerge(boolean condition, SQLExpression2<ProxyGroupSelector, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> selectExpression) {
        return groupBy(condition, (groupSelector, t, t1, t2, t3, t4,t5) -> {
            selectExpression.apply(groupSelector, new Tuple6<>(t, t1, t2, t3, t4,t5));
        });
    }
}
