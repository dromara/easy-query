package com.easy.query.api.proxy.select.extension.queryable7;

import com.easy.query.api.proxy.select.ProxyQueryable7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.MultiProxyAggregateFilter7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.impl.MultiProxyAggregateFilter7Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ClientProxyQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, ProxyQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {

    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> having(SQLExpression1<MultiProxyAggregateFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> predicateExpression) {
        return having(true, predicateExpression);
    }

    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> having(boolean condition, SQLExpression1<MultiProxyAggregateFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> predicateExpression) {
        if (condition) {
            getClientQueryable7().having((t, t1, t2, t3, t4, t5, t6) -> {
                predicateExpression.apply(new MultiProxyAggregateFilter7Impl<>(t.getAggregateFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy()));
            });
        }
        return getQueryable7();
    }
}
