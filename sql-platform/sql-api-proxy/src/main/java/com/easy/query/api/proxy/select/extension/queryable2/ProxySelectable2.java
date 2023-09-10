package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.ProxySelectable;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyAsSelector2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxySelector2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.impl.MultiProxyAsSelector2Impl;
import com.easy.query.api.proxy.select.extension.queryable2.sql.impl.MultiProxySelector2Impl;
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
public interface ProxySelectable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ProxySelectable<T1Proxy,T1>,ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {



    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    default ProxyQueryable<T1Proxy, T1> select(SQLExpression1<MultiProxySelector2<T1Proxy,T2Proxy>> selectExpression){

        ClientQueryable<T1> select = getClientQueryable2().select(columnSelector -> {
            selectExpression.apply(new MultiProxySelector2Impl<>(columnSelector.getSelector(), get1Proxy(),get2Proxy()));
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression1<MultiProxyAsSelector2<T1Proxy, T2Proxy,TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable2().select(trProxy.getEntityClass(), (selector1, selector2) -> {
            selectExpression.apply(new MultiProxyAsSelector2Impl<>(selector2.getAsSelector(), get1Proxy(), get2Proxy(),trProxy));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }
}
