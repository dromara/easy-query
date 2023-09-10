package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyAggregateFilter2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.impl.MultiProxyAggregateFilter2Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy,T1,T2Proxy, T2> {

    default ProxyQueryable2<T1Proxy,T1,T2Proxy, T2> having(SQLExpression1<MultiProxyAggregateFilter2<T1Proxy, T2Proxy>> predicateExpression) {
        return having(true,predicateExpression);
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy, T2> having(boolean condition, SQLExpression1<MultiProxyAggregateFilter2<T1Proxy, T2Proxy>> predicateExpression) {
        if (condition) {
            getClientQueryable2().having((predicate1, predicate2) -> {
                predicateExpression.apply(new MultiProxyAggregateFilter2Impl<>(predicate1.getAggregateFilter(), get1Proxy(), get2Proxy()));
            });
        }
        return getQueryable2();
    }

}
