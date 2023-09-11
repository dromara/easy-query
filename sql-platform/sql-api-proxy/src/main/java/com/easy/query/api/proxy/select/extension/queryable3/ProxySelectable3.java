package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.ProxySelectable;
import com.easy.query.api.proxy.select.extension.queryable3.sql.MultiProxyAsSelector3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.MultiProxySelector3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.impl.MultiProxyAsSelector3Impl;
import com.easy.query.api.proxy.select.extension.queryable3.sql.impl.MultiProxySelector3Impl;
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
public interface ProxySelectable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ProxySelectable<T1Proxy,T1>,ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    default ProxyQueryable<T1Proxy, T1> select(SQLExpression1<MultiProxySelector3<T1Proxy,T2Proxy,T3Proxy>> selectExpression){

        ClientQueryable<T1> select = getClientQueryable3().select(columnSelector -> {
            selectExpression.apply(new MultiProxySelector3Impl<>(columnSelector.getSelector(), get1Proxy(),get2Proxy(),get3Proxy()));
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression1<MultiProxyAsSelector3<T1Proxy, T2Proxy, T3Proxy,TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable3().select(trProxy.getEntityClass(), (selector1, selector2, selector3) -> {
            selectExpression.apply(new MultiProxyAsSelector3Impl<>(selector2.getAsSelector(), get1Proxy(), get2Proxy(), get3Proxy(),trProxy));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }
}
