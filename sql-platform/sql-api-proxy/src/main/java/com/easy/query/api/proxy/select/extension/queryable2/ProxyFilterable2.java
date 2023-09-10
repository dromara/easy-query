package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyFilter2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyFilter2Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFilterable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where2(SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> whereExpression) {
        return where2(true,whereExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where2(boolean condition, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> whereExpression) {
        if (condition) {
            getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
                whereExpression.apply(new MultiProxyFilter2Impl<>(wherePredicate2.getFilter(), get1Proxy(), get2Proxy()));
            });
        }
        return getQueryable2();
    }
}
