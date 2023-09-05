//package com.easy.query.api.proxy.sql;
//
//import com.easy.query.api.proxy.select.ProxyQueryable;
//import com.easy.query.core.basic.api.select.ClientQueryable;
//import com.easy.query.core.expression.parser.core.base.NavigateInclude;
//import com.easy.query.core.proxy.ProxyEntity;
//
//import java.util.Collection;
//
///**
// * create time 2023/7/31 15:29
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface SQLProxyNavigateInclude<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
//    NavigateInclude<T1> getNavigateInclude();
//    default  <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<TPropertyProxy,TProperty> one(KProperty1<? super T1,TProperty> navigate){
//        return one(navigate,null);
//    }
//    default  <TProperty> ProxyQueryable<TProperty> one(KProperty1<? super T1,TProperty> navigate,Integer groupSize){
//        ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(EasyKtLambdaUtil.getPropertyName(navigate),groupSize);
//        return new ProxyQueryable<>(clientQueryable);
//    }
//    default  <TProperty> ProxyQueryable<TProperty> many(KProperty1<? super T1, Collection<TProperty>> navigate){
//        return many(navigate,null);
//    }
//    default  <TProperty> ProxyQueryable<TProperty> many(KProperty1<? super T1, Collection<TProperty>> navigate,Integer groupSize){
//        ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(EasyKtLambdaUtil.getPropertyName(navigate),groupSize);
//        return new EasyKtQueryable<>(clientQueryable);
//    }
//}
