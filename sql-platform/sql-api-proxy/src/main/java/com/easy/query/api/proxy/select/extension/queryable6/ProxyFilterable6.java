package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.MultiProxyFilter6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.MultiProxyFilter6Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFilterable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientProxyQueryable6Available<T1, T2, T3, T4, T5, T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> where6(SQLExpression1<MultiProxyFilter6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> whereExpression) {
        return where6(true, whereExpression);
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> where6(boolean condition, SQLExpression1<MultiProxyFilter6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> whereExpression) {
        if (condition) {
            getClientQueryable6().where((t, t1, t2, t3, t4, t5) -> {
                whereExpression.apply(new MultiProxyFilter6Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy()));
            });
        }
        return getQueryable6();
    }
}
