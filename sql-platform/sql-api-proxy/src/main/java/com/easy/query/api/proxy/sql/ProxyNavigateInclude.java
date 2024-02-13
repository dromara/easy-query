package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;

import java.util.Objects;

/**
 * create time 2023/6/18 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyNavigateInclude<T1> {
    NavigateInclude<T1> getNavigateInclude();
   default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty,T1Proxy extends ProxyEntity<T1Proxy,T1>> ProxyQueryable<TPropertyProxy,TProperty> one(TPropertyProxy tPropertyProxy){
       return  one(tPropertyProxy,null);
   }
   default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty,T1Proxy extends ProxyEntity<T1Proxy,T1>> ProxyQueryable<TPropertyProxy,TProperty> one(TPropertyProxy tPropertyProxy,Integer groupSize){

       Objects.requireNonNull(tPropertyProxy.getNavValue(),"include [navValue] is null");
       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().with(tPropertyProxy.getNavValue(),groupSize);
       return new EasyProxyQueryable<>(tPropertyProxy,clientQueryable);
   }
   default  <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<TPropertyProxy,TProperty> many(SQLQueryable<TPropertyProxy,TProperty> tPropertyProxy){
       return many(tPropertyProxy,null);
   }
   default  <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<TPropertyProxy,TProperty> many(SQLQueryable<TPropertyProxy,TProperty> tPropertyProxy,Integer groupSize){
       Objects.requireNonNull(tPropertyProxy.getNavValue(),"include [navValue] is null");
       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(tPropertyProxy.getNavValue(), groupSize);
       return new EasyProxyQueryable<>(tPropertyProxy.getQueryable().get1Proxy(), clientQueryable);
   }
}
