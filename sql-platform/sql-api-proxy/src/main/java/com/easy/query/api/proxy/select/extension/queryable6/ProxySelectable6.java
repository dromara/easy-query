package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.ProxySelectable;
import com.easy.query.api.proxy.select.extension.queryable6.sql.MultiProxyAsSelector6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.MultiProxySelector6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.impl.MultiProxyAsSelector6Impl;
import com.easy.query.api.proxy.select.extension.queryable6.sql.impl.MultiProxySelector6Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelectable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ProxySelectable<T1Proxy, T1>,ClientProxyQueryable6Available<T1, T2, T3, T4, T5, T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {



    default ProxyQueryable<T1Proxy, T1> select(SQLExpression1<MultiProxySelector6<T1Proxy,T2Proxy,T3Proxy,T4Proxy,T5Proxy,T6Proxy>> selectExpression){

        ClientQueryable<T1> select = getClientQueryable6().select(columnSelector -> {
            selectExpression.apply(new MultiProxySelector6Impl<>(columnSelector.getSelector(), get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy(),get5Proxy(),get6Proxy()));
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }



    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression1<MultiProxyAsSelector6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy,TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable6().select(trProxy.getEntityClass(), (t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new MultiProxyAsSelector6Impl<>(t.getAsSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(),trProxy));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }
}
