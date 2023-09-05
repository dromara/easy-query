//package com.easy.query.api.proxy.sql;
//
//import com.easy.query.api.proxy.select.ProxyQueryable;
//import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
//import com.easy.query.api4kt.select.KtQueryable;
//import com.easy.query.api4kt.select.impl.EasyKtQueryable;
//import com.easy.query.api4kt.util.EasyKtLambdaUtil;
//import com.easy.query.core.basic.api.select.ClientQueryable;
//import com.easy.query.core.expression.parser.core.base.NavigateInclude;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.SQLColumn;
//import kotlin.reflect.KProperty1;
//
//import java.util.Collection;
//
///**
// * create time 2023/6/18 11:14
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface ProxyNavigateInclude<T1> {
//    NavigateInclude<T1> getNavigateInclude();
//   default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<TPropertyProxy,TProperty> one(SQLColumn<TProperty> navigate){
//       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(navigate.value());
//       return new EasyProxyQueryable<>(,clientQueryable);
//   }
//   default  <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<TPropertyProxy,TProperty> many(KProperty1<? super T1, Collection<TProperty>> navigate){
//       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(EasyKtLambdaUtil.getPropertyName(navigate));
//       return new EasyKtQueryable<>(clientQueryable);
//   }
//}
