package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;

/**
 * create time 2023/12/9 00:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityNavigateInclude<T,TProxy extends ProxyEntity<TProxy,T>> {
    TProxy get1Proxy();
    NavigateInclude<T> getNavigateInclude();
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty >, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<TPropertyProxy,TProperty> asQueryable(SQLNavigateColumn<TProxy,TProperty> sqlNavigateColumn){
        return  asQueryable(sqlNavigateColumn,null);
    }
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty >, TProperty   extends ProxyEntityAvailable<TProperty,TPropertyProxy>> EntityQueryable<TPropertyProxy,TProperty> asQueryable(SQLNavigateColumn<TProxy,TProperty> sqlNavigateColumn,Integer groupSize){
        ClientQueryable<TProperty> clientQueryable = getNavigateInclude().with(sqlNavigateColumn.getValue(),groupSize);
        TPropertyProxy tPropertyProxy = EntityQueryProxyManager.create(clientQueryable.queryClass());
        return new EasyEntityQueryable<>(tPropertyProxy,clientQueryable);
    }
}
