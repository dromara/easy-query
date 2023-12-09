package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/12/9 00:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityNavigateInclude<T,TProxy extends ProxyEntity<TProxy,T>> {
    TProxy get1Proxy();
    NavigateInclude<T> getNavigateInclude();
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty >, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<TPropertyProxy,TProperty> one(SQLFuncExpression1<TProxy, SQLColumn<TProxy,TProperty>> navigate){
        return  one(navigate,null);
    }
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty,T1Proxy extends ProxyEntity<T1Proxy,T1>> ProxyQueryable<TPropertyProxy,TProperty> one(SQLColumn<T1Proxy,TProperty> navigate,TPropertyProxy tPropertyProxy){
//        return  one(navigate,tPropertyProxy,null);
//    }
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty >, TProperty   extends ProxyEntityAvailable<TProperty,TPropertyProxy>> EntityQueryable<TPropertyProxy,TProperty> one(SQLFuncExpression1<TProxy,SQLColumn<TProxy,TProperty>> navigate,Integer groupSize){
        SQLColumn<TProxy, TProperty> propertySQLColumn = navigate.apply(get1Proxy());
        ClientQueryable<TProperty> clientQueryable = getNavigateInclude().with(propertySQLColumn.getValue(),groupSize);
        TPropertyProxy tPropertyProxy = EntityQueryProxyManager.create(clientQueryable.queryClass());
        return new EasyEntityQueryable<>(tPropertyProxy,clientQueryable);
    }
}
