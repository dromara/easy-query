package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyOrderSelectorImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression4;
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

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAsc(boolean condition, SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        if (condition) {
            getClientQueryable3().orderByAsc((selector1, selector2, selector3) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(selector1.getOrderSelector()), getProxy(), get2Proxy(), get3Proxy());
            });
        }
        return getQueryable3();
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAscMerge(SQLExpression2<ProxyOrderSelector, Tuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByAscMerge(boolean condition, SQLExpression2<ProxyOrderSelector, Tuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderByAsc(condition, (selector, t1, t2, t3) -> {
            selectExpression.apply(selector, new Tuple3<>(t1, t2, t3));
        });
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDesc(boolean condition, SQLExpression4<ProxyOrderSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        if (condition) {
            getClientQueryable3().orderByDesc((selector1, selector2, selector3) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(selector1.getOrderSelector()), getProxy(), get2Proxy(), get3Proxy());
            });
        }
        return getQueryable3();
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDescMerge(SQLExpression2<ProxyOrderSelector, Tuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByDescMerge(boolean condition, SQLExpression2<ProxyOrderSelector, Tuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return orderByDesc(condition, (selector, t1, t2, t3) -> {
            selectExpression.apply(selector, new Tuple3<>(t1, t2, t3));
        });
    }

}
