package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyOrderSelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression5;
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

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAsc(boolean condition, SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        if (condition) {
            getClientQueryable4().orderByAsc((t, t1, t2, t3) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(t.getOrderSelector()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return getQueryable4();
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAscMerge(SQLExpression2<ProxyOrderSelector, Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByAscMerge(boolean condition, SQLExpression2<ProxyOrderSelector, Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return orderByAsc(condition, (selector, t, t1, t2, t3) -> {
            selectExpression.apply(selector, new Tuple4<>(t, t1, t2, t3));
        });
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDesc(boolean condition, SQLExpression5<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        if (condition) {
            getClientQueryable4().orderByDesc((t, t1, t2, t3) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(t.getOrderSelector()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
            });
        }
        return getQueryable4();
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDescMerge(SQLExpression2<ProxyOrderSelector, Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByDescMerge(boolean condition, SQLExpression2<ProxyOrderSelector, Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return orderByDesc(condition, (selector, t, t1, t2, t3) -> {
            selectExpression.apply(selector, new Tuple4<>(t, t1, t2, t3));
        });
    }

}
