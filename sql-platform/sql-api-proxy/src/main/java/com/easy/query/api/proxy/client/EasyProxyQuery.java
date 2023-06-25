package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.proxy.ProxyQuery;

/**
 * create time 2023/6/21 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyProxyQuery {
    EasyQueryClient getEasyQueryClient();
    <TProxy extends ProxyQuery<TProxy, TEntity>,TEntity> ProxyQueryable<TProxy,TEntity> queryable(TProxy table);
}
