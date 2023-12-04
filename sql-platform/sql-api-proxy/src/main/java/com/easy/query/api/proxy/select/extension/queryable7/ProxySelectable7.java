package com.easy.query.api.proxy.select.extension.queryable7;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.ProxySelectable;
import com.easy.query.api.proxy.select.extension.queryable7.sql.MultiProxyAsSelector7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.MultiProxySelector7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.impl.MultiProxyAsSelector7Impl;
import com.easy.query.api.proxy.select.extension.queryable7.sql.impl.MultiProxySelector7Impl;
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
public interface ProxySelectable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ProxySelectable<T1Proxy,T1>,ClientProxyQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, ProxyQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {

    /**
     * 对当前表达式返回自定义select列
     *
     * @param sqlSelects
     * @return
     */
    default ProxyQueryable<T1Proxy, T1> select(SQLSelect... sqlSelects){

        ClientQueryable<T1> select = getClientQueryable7().select(columnSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelects)){
                for (SQLSelect sqlSelect : sqlSelects) {
                    sqlSelect.accept(columnSelector.getSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default  <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLSelectAs... sqlSelectAs){

        ClientQueryable<TR> select = getClientQueryable7().select(trProxy.getEntityClass(), columnAsSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelectAs)){
                for (SQLSelectAs sqlAsSelect : sqlSelectAs) {
                    sqlAsSelect.accept(columnAsSelector.getAsSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    default ProxyQueryable<T1Proxy, T1> select(SQLExpression1<MultiProxySelector7<T1Proxy,T2Proxy,T3Proxy,T4Proxy,T5Proxy,T6Proxy,T7Proxy>> selectExpression){

        ClientQueryable<T1> select = getClientQueryable7().select(columnSelector -> {
            selectExpression.apply(new MultiProxySelector7Impl<>(columnSelector.getSelector(), get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy(),get5Proxy(),get6Proxy(),get7Proxy()));
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }


    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression1<MultiProxyAsSelector7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy,TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable7().select(trProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new MultiProxyAsSelector7Impl<>(t.getAsSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),trProxy));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }
}
