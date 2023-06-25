package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.delete.ProxyEntityDeletable;
import com.easy.query.api.proxy.delete.ProxyExpressionDeletable;
import com.easy.query.api.proxy.delete.impl.EasyProxyEntityDeletable;
import com.easy.query.api.proxy.delete.impl.EasyProxyExpressionDeletable;
import com.easy.query.api.proxy.insert.EasyProxyEntityInsertable;
import com.easy.query.api.proxy.insert.ProxyEntityInsertable;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.update.ProxyEntityUpdatable;
import com.easy.query.api.proxy.update.ProxyExpressionUpdatable;
import com.easy.query.api.proxy.update.ProxyOnlyEntityUpdatable;
import com.easy.query.api.proxy.update.impl.EasyProxyEntityUpdatable;
import com.easy.query.api.proxy.update.impl.EasyProxyExpressionUpdatable;
import com.easy.query.api.proxy.update.impl.EasyProxyOnlyEntityUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

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
    public QueryRuntimeContext getRuntimeContext() {
        return easyQueryClient.getRuntimeContext();
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> ProxyQueryable<TProxy, TEntity> queryable(TProxy table) {
        return new EasyProxyQueryable<>(table, easyQueryClient.queryable(table.getEntityClass()));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> ProxyQueryable<TProxy, T> queryable(String sql, TProxy table) {
        return new EasyProxyQueryable<>(table, easyQueryClient.queryable(sql,table.getEntityClass()));
    }

    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        return easyQueryClient.beginTransaction(isolationLevel);
    }

    @Override
    public <T> ProxyEntityInsertable<T> insertable(T entity) {
        return new EasyProxyEntityInsertable<>(easyQueryClient.insertable(entity));
    }

    @Override
    public <T> ProxyEntityInsertable<T> insertable(Collection<T> entities) {
        return new EasyProxyEntityInsertable<>(easyQueryClient.insertable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> ProxyExpressionUpdatable<TProxy, T> updatable(TProxy proxy) {
        return new EasyProxyExpressionUpdatable<>(proxy,easyQueryClient.updatable(proxy.getEntityClass()));
    }

    @Override
    public <T> ProxyOnlyEntityUpdatable<T> updatable(T entity) {
        return new EasyProxyOnlyEntityUpdatable<>(easyQueryClient.updatable(entity));
    }

    @Override
    public <T> ProxyOnlyEntityUpdatable<T> updatable(Collection<T> entities) {
        return new EasyProxyOnlyEntityUpdatable<>(easyQueryClient.updatable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> ProxyEntityUpdatable<TProxy, T> updatable(T entity, TProxy proxy) {
        return new EasyProxyEntityUpdatable<>(proxy,easyQueryClient.updatable(entity));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> ProxyEntityUpdatable<TProxy, T> updatable(Collection<T> entities, TProxy proxy) {

        return new EasyProxyEntityUpdatable<>(proxy,easyQueryClient.updatable(entities));
    }

    @Override
    public <T> ProxyEntityDeletable<T> deletable(T entity) {
        return new EasyProxyEntityDeletable<>(easyQueryClient.deletable(entity));
    }

    @Override
    public <T> ProxyEntityDeletable<T> deletable(Collection<T> entities) {
        return new EasyProxyEntityDeletable<>(easyQueryClient.deletable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> ProxyExpressionDeletable<TProxy, T> deletable(TProxy proxy) {
        return new EasyProxyExpressionDeletable<>(proxy,easyQueryClient.deletable(proxy.getEntityClass()));
    }

    @Override
    public boolean addTracking(Object entity) {
        return easyQueryClient.addTracking(entity);
    }
}
