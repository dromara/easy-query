package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.MultiProxyFilter3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.impl.MultiProxyFilter3Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFilterable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2
        , T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2,T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> whereExpression) {
        return where(true,whereExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> where(boolean condition, SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> whereExpression) {
        if (condition) {
            getClientQueryable3().where((wherePredicate1, wherePredicate2, wherePredicate3) -> {
                whereExpression.apply(new MultiProxyFilter3Impl<>(wherePredicate2.getFilter(), get1Proxy(), get2Proxy(), get3Proxy()));
            });
        }
        return getQueryable3();
    }
}
