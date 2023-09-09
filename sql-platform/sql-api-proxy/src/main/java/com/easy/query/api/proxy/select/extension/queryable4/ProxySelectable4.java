package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.impl.ProxyAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelectable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression5<ProxyAsSelector<TRProxy, TR>, T1Proxy, T2Proxy, T3Proxy, T4Proxy> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable4().select(trProxy.getEntityClass(), (t, t1, t2, t3) -> {
            selectExpression.apply(new ProxyAsSelectorImpl<>(trProxy, t.getAsSelector()), getProxy(), get2Proxy(), get3Proxy(), get4Proxy());
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> selectMerge(TRProxy trProxy, SQLExpression2<ProxyAsSelector<TRProxy, TR>, Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return select(trProxy, (selector, t, t1, t2, t3) -> {
            selectExpression.apply(selector, new Tuple4<>(t, t1, t2, t3));
        });
    }
}
