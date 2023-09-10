package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.MultiProxyOrderSelector3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.impl.MultiProxyOrderSelector3Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(SQLExpression1<MultiProxyOrderSelector3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(boolean condition, SQLExpression1<MultiProxyOrderSelector3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable3().orderByAsc((selector1, selector2, selector3) -> {
                selectExpression.apply(new MultiProxyOrderSelector3Impl<>(selector1.getOrderSelector(), get1Proxy(), get2Proxy(), get3Proxy()));
            });
        }
        return getQueryable3();
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(SQLExpression1<MultiProxyOrderSelector3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(boolean condition, SQLExpression1<MultiProxyOrderSelector3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable3().orderByDesc((selector1, selector2, selector3) -> {
                selectExpression.apply(new MultiProxyOrderSelector3Impl<>(selector1.getOrderSelector(), get1Proxy(), get2Proxy(), get3Proxy()));
            });
        }
        return getQueryable3();
    }

}
