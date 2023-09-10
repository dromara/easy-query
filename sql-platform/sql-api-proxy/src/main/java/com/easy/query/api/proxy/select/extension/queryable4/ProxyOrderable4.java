package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxyOrderSelector4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.impl.MultiProxyOrderSelector4Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(SQLExpression1<MultiProxyOrderSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(boolean condition, SQLExpression1<MultiProxyOrderSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable4().orderByAsc((t, t1, t2, t3) -> {
                selectExpression.apply(new MultiProxyOrderSelector4Impl<>(t.getOrderSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy()));
            });
        }
        return getQueryable4();
    }
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(SQLExpression1<MultiProxyOrderSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(boolean condition, SQLExpression1<MultiProxyOrderSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable4().orderByDesc((t, t1, t2, t3) -> {
                selectExpression.apply(new MultiProxyOrderSelector4Impl<>(t.getOrderSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy()));
            });
        }
        return getQueryable4();
    }

}
