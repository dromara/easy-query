package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.ProxySelectable;
import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyAsSelector1;
import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxySelector1;
import com.easy.query.api.proxy.select.extension.queryable.sql.impl.MultiProxyAsSelector1Impl;
import com.easy.query.api.proxy.select.extension.queryable.sql.impl.MultiProxySelector1Impl;
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
public interface ProxySelectable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ProxySelectable<T1Proxy,T1>,ClientProxyQueryableAvailable<T1>, ProxyQueryableAvailable<T1Proxy, T1> {


    /**
     * 对当前表达式返回自定义select列
     *
     * @param sqlSelects
     * @return
     */
    default ProxyQueryable<T1Proxy, T1> select(SQLSelectExpression... sqlSelects){

        ClientQueryable<T1> select = getClientQueryable().select(columnSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelects)){
                for (SQLSelectExpression sqlSelect : sqlSelects) {
                    sqlSelect.accept(columnSelector.getSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(get1Proxy(), select);
    }

    default  <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLSelectAsExpression... sqlSelectAs){

        ClientQueryable<TR> select = getClientQueryable().select(trProxy.getEntityClass(), columnAsSelector -> {
            if(EasyArrayUtil.isNotEmpty(sqlSelectAs)){
                for (SQLSelectAsExpression sqlAsSelect : sqlSelectAs) {
                    sqlAsSelect.accept(columnAsSelector.getAsSelector());
                }
            }
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }
    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
   default ProxyQueryable<T1Proxy, T1> select(SQLExpression1<MultiProxySelector1<T1Proxy>> selectExpression){

       ClientQueryable<T1> select = getClientQueryable().select(columnSelector -> {
           selectExpression.apply(new MultiProxySelector1Impl<>(columnSelector.getSelector(), get1Proxy()));
       });
       return new EasyProxyQueryable<>(get1Proxy(), select);
   }


    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     * 多次select会在前一次基础上进行对上次结果进行匿名表处理
     *
     * @param trProxy
     * @param selectExpression
     * @param <TR>
     * @return
     */
   default  <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression1<MultiProxyAsSelector1<T1Proxy,TRProxy, TR>> selectExpression){

       ClientQueryable<TR> select = getClientQueryable().select(trProxy.getEntityClass(), columnAsSelector -> {
           selectExpression.apply(new MultiProxyAsSelector1Impl<>(columnAsSelector.getAsSelector(), get1Proxy(),trProxy));
       });
       return new EasyProxyQueryable<>(trProxy, select);
   }

}
