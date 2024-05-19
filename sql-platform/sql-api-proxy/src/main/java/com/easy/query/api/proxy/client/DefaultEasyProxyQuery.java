package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.entity.delete.EntityOnlyDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.delete.impl.EasyEntityOnlyDeletable;
import com.easy.query.api.proxy.entity.delete.impl.EasyExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EasyEntityOnlyInsertable;
import com.easy.query.api.proxy.entity.insert.EntityOnlyInsertable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityOnlyUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.api.proxy.entity.update.impl.EasyEntityOnlyUpdate;
import com.easy.query.api.proxy.entity.update.impl.EasyExpressionUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * create time 2023/6/24 11:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyProxyQuery implements EasyProxyQuery {

    private final EasyQueryClient easyQueryClient;

    public DefaultEasyProxyQuery(EasyQueryClient easyQueryClient) {
        this.easyQueryClient = easyQueryClient;
    }
    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> EntityQueryable<TProxy, T> queryable(TProxy table) {
        return new EasyEntityQueryable<>(table,easyQueryClient.queryable(table.getEntityClass()));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> EntityQueryable<TProxy, T> queryable(String sql, TProxy table) {
        return new EasyEntityQueryable<>(table, easyQueryClient.queryable(sql, table.getEntityClass()));
    }

    @Override
    public <T> EntityOnlyInsertable<T> insertable(T entity) {
        return new EasyEntityOnlyInsertable<>(easyQueryClient.insertable(entity));
    }

    @Override
    public <T> EntityOnlyInsertable<T> insertable(Collection<T> entities) {
        return new EasyEntityOnlyInsertable<>(easyQueryClient.insertable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> ExpressionUpdatable<TProxy, T> updatable(TProxy table) {
        return new EasyExpressionUpdatable<>(table,easyQueryClient.updatable(table.getEntityClass()));
    }

    @Override
    public <T> EntityOnlyUpdatable<T> updatable(T entity) {
        return new EasyEntityOnlyUpdate<>(easyQueryClient.updatable(entity));
    }

    @Override
    public <T> EntityOnlyUpdatable<T> updatable(Collection<T> entities) {
        return new EasyEntityOnlyUpdate<>(easyQueryClient.updatable(entities));
    }

    @Override
    public <T> EntityOnlyDeletable<T> deletable(T entity) {
        return new EasyEntityOnlyDeletable<>(easyQueryClient.deletable(entity));
    }

    @Override
    public <T> EntityOnlyDeletable<T> deletable(Collection<T> entities) {
        return new EasyEntityOnlyDeletable<>(easyQueryClient.deletable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T> ExpressionDeletable<TProxy, T> deletable(TProxy table) {
        return new EasyExpressionDeletable<>(table,easyQueryClient.deletable(table.getEntityClass()));
    }

    @Override
    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }

}
