package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {


    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(SQLExpression5<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(boolean condition, SQLExpression5<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        if (condition) {
            getClientQueryable4().groupBy((t, t1, t2,t3) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(t.getGroupSelector()), getProxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return getQueryable4();
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupByMerge(SQLExpression2<ProxyGroupSelector, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupByMerge(boolean condition, SQLExpression2<ProxyGroupSelector, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> selectExpression) {
        return groupBy(condition, (groupSelector, t, t1, t2,t3) -> {
            selectExpression.apply(groupSelector, new Tuple4<>(t, t1, t2,t3));
        });
    }
}
