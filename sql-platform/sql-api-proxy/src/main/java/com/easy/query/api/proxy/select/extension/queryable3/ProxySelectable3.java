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
import com.easy.query.core.proxy.SQLSelect;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelectable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ProxySelectable<T1Proxy,T1>,ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    /**
     * 对当前表达式返回自定义select列
     *
     * @param sqlSelects
     * @return
     */
    default ProxyQueryable<T1Proxy, T1> select(SQLSelect... sqlSelects){

        ClientQueryable<T1> select = getClientQueryable3().select(columnSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelects)){
                for (SQLSelect sqlSelect : sqlSelects) {
                    sqlSelect.accept(columnSelector.getSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default  <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLSelectAs... sqlSelectAs){

        ClientQueryable<TR> select = getClientQueryable3().select(trProxy.getEntityClass(), columnAsSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelectAs)){
                for (SQLSelectAs sqlAsSelect : sqlSelectAs) {
                    sqlAsSelect.accept(columnAsSelector.getAsSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

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
