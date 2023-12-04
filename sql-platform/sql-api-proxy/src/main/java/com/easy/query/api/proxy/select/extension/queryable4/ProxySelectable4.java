package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.ProxySelectable;
import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxyAsSelector4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxySelector4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.impl.MultiProxyAsSelector4Impl;
import com.easy.query.api.proxy.select.extension.queryable4.sql.impl.MultiProxySelector4Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelect;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelectable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ProxySelectable<T1Proxy,T1>,ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {


    /**
     * 对当前表达式返回自定义select列
     *
     * @param sqlSelects
     * @return
     */
    default ProxyQueryable<T1Proxy, T1> select(SQLSelect... sqlSelects){

        ClientQueryable<T1> select = getClientQueryable4().select(columnSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelects)){
                for (SQLSelect sqlSelect : sqlSelects) {
                    sqlSelect.accept(columnSelector.getSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default  <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLSelectAs... sqlSelectAs){

        ClientQueryable<TR> select = getClientQueryable4().select(trProxy.getEntityClass(), columnAsSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelectAs)){
                for (SQLSelectAs sqlAsSelect : sqlSelectAs) {
                    sqlAsSelect.accept(columnAsSelector.getAsSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    default ProxyQueryable<T1Proxy, T1> select(SQLExpression1<MultiProxySelector4<T1Proxy,T2Proxy,T3Proxy,T4Proxy>> selectExpression){

        ClientQueryable<T1> select = getClientQueryable4().select(columnSelector -> {
            selectExpression.apply(new MultiProxySelector4Impl<>(columnSelector.getSelector(), get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy()));
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression1<MultiProxyAsSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy,TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable4().select(trProxy.getEntityClass(), (t, t1, t2, t3) -> {
            selectExpression.apply(new MultiProxyAsSelector4Impl<>(t.getAsSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),trProxy));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }
}
