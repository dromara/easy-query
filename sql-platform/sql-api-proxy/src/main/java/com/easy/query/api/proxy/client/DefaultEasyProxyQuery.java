package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.proxy.ProxyQuery;

/**
 * create time 2023/6/24 11:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyProxyQuery implements EasyProxyQuery{
    private final EasyQueryClient easyQueryClient;

    public DefaultEasyProxyQuery(EasyQueryClient easyQueryClient) {
        this.easyQueryClient = easyQueryClient;
    }

    @Override
    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }
    @Override
    public <TProxy extends ProxyQuery<TProxy, TEntity>, TEntity> ProxyQueryable<TProxy, TEntity> queryable(TProxy table) {
        return new EasyProxyQueryable<>(table, easyQueryClient.queryable(table.getEntityClass()));
    }
}
