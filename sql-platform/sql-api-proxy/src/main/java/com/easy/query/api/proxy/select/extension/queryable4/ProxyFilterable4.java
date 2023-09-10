package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxyFilter4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.impl.MultiProxyFilter4Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFilterable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2
        , T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where4(SQLExpression1<MultiProxyFilter4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> whereExpression) {
        return where4(true, whereExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> where4(boolean condition, SQLExpression1<MultiProxyFilter4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> whereExpression) {
        if (condition) {
            getClientQueryable4().where((t, t1, t2, t3) -> {
                whereExpression.apply(new MultiProxyFilter4Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy()));
            });
        }
        return getQueryable4();
    }
}
