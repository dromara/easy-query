package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyOrderSelectorImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(SQLExpression3<ProxyOrderSelector, T1Proxy, T2Proxy> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAsc(boolean condition, SQLExpression3<ProxyOrderSelector, T1Proxy, T2Proxy> selectExpression) {
        if (condition) {
            getClientQueryable2().orderByAsc((selector1, selector2) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(selector2.getOrderSelector()), getQueryable2().get1Proxy(), getQueryable2().get2Proxy());
            });
        }
        return getQueryable2();
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAscMerge(SQLExpression2<ProxyOrderSelector, Tuple2<T1Proxy, T2Proxy>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByAscMerge(boolean condition, SQLExpression2<ProxyOrderSelector, Tuple2<T1Proxy, T2Proxy>> selectExpression) {
        return orderByAsc(condition, (selector, t1, t2) -> {
            selectExpression.apply(selector, new Tuple2<>(t1, t2));
        });
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(SQLExpression3<ProxyOrderSelector, T1Proxy, T2Proxy> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDesc(boolean condition, SQLExpression3<ProxyOrderSelector, T1Proxy, T2Proxy> selectExpression) {
        if (condition) {
            getClientQueryable2().orderByDesc((selector1, selector2) -> {
                selectExpression.apply(new ProxyOrderSelectorImpl(selector2.getOrderSelector()), getQueryable2().get1Proxy(), getQueryable2().get2Proxy());
            });
        }
        return getQueryable2();
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDescMerge(SQLExpression2<ProxyOrderSelector, Tuple2<T1Proxy, T2Proxy>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> orderByDescMerge(boolean condition, SQLExpression2<ProxyOrderSelector, Tuple2<T1Proxy, T2Proxy>> selectExpression) {
        return orderByDesc(condition, (selector, t1, t2) -> {
            selectExpression.apply(selector, new Tuple2<>(t1, t2));
        });
    }

}
