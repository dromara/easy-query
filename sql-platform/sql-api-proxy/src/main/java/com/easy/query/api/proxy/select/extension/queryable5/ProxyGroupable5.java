package com.easy.query.api.proxy.select.extension.queryable5;

import com.easy.query.api.proxy.select.ProxyQueryable5;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientProxyQueryable5Available<T1, T2, T3, T4, T5>, ProxyQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {


    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupBy(SQLExpression6<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupBy(boolean condition, SQLExpression6<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> selectExpression) {
        if (condition) {
            getClientQueryable5().groupBy((t, t1, t2, t3, t4) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(t.getGroupSelector()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy());
            });
        }
        return getQueryable5();
    }

    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupByMerge(SQLExpression2<ProxyGroupSelector, Tuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupByMerge(boolean condition, SQLExpression2<ProxyGroupSelector, Tuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> selectExpression) {
        return groupBy(condition, (groupSelector, t, t1, t2, t3, t4) -> {
            selectExpression.apply(groupSelector, new Tuple5<>(t, t1, t2, t3, t4));
        });
    }
}
