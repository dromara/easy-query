package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.select.ProxyQueryable;

/**
 * create time 2023/6/21 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyProxyQuery {
    <TProxy extends ProxyQuery<TProxy, TEntity>,TEntity> ProxyQueryable<TProxy,TEntity> queryable(TProxy table);
}
