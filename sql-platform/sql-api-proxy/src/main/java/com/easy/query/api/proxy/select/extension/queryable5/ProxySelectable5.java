package com.easy.query.api.proxy.select.extension.queryable5;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.ProxySelectable;
import com.easy.query.api.proxy.select.extension.queryable5.sql.MultiProxyAsSelector5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.MultiProxySelector5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.impl.MultiProxyAsSelector5Impl;
import com.easy.query.api.proxy.select.extension.queryable5.sql.impl.MultiProxySelector5Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelectable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ProxySelectable<T1Proxy,T1>,ClientProxyQueryable5Available<T1, T2, T3, T4, T5>, ProxyQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {

    /**
     * 对当前表达式返回自定义select列
     *
     * @param sqlSelects
     * @return
     */
    default ProxyQueryable<T1Proxy, T1> select(SQLSelectExpression... sqlSelects){

        ClientQueryable<T1> select = getClientQueryable5().select(columnSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelects)){
                for (SQLSelectExpression sqlSelect : sqlSelects) {
                    sqlSelect.accept(columnSelector.getSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default  <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLSelectAsExpression... sqlSelectAs){

        ClientQueryable<TR> select = getClientQueryable5().select(trProxy.getEntityClass(), columnAsSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelectAs)){
                for (SQLSelectAsExpression sqlAsSelect : sqlSelectAs) {
                    sqlAsSelect.accept(columnAsSelector.getAsSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    default ProxyQueryable<T1Proxy, T1> select(SQLExpression1<MultiProxySelector5<T1Proxy,T2Proxy,T3Proxy,T4Proxy,T5Proxy>> selectExpression){

        ClientQueryable<T1> select = getClientQueryable5().select(columnSelector -> {
            selectExpression.apply(new MultiProxySelector5Impl<>(columnSelector.getSelector(), get1Proxy(),get2Proxy(),get3Proxy(),get4Proxy(),get5Proxy()));
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }


    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression1<MultiProxyAsSelector5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy,TRProxy, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable5().select(trProxy.getEntityClass(), (t, t1, t2, t3,t4) -> {
            selectExpression.apply(new MultiProxyAsSelector5Impl<>(t.getAsSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(),trProxy));
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }
}
