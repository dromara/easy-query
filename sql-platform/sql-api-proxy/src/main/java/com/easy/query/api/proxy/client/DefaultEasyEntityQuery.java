package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.delete.impl.EasyEntityDeletable;
import com.easy.query.api.proxy.entity.delete.impl.EasyExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EasyEmptyEntityInsertable;
import com.easy.query.api.proxy.entity.insert.EasyEntityInsertable;
import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.api.proxy.entity.update.impl.EasyEmptyEntityUpdatable;
import com.easy.query.api.proxy.entity.update.impl.EasyEntityUpdatable;
import com.easy.query.api.proxy.entity.update.impl.EasyExpressionUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/9/19 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyEntityQuery implements EasyEntityQuery {

    private final EasyQueryClient easyQueryClient;

    public DefaultEasyEntityQuery(EasyQueryClient easyQueryClient) {
        this.easyQueryClient = easyQueryClient;
    }

    @Override
    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }


    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(Class<T> entityClass) {
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        return new EasyEntityQueryable<>(tProxy, easyQueryClient.queryable(entityClass));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(String sql, Class<T> entityClass) {
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        return new EasyEntityQueryable<>(tProxy, easyQueryClient.queryable(sql, entityClass));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(String sql, Class<T> entityClass, Collection<Object> params) {
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        return new EasyEntityQueryable<>(tProxy, easyQueryClient.queryable(sql, entityClass, params));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityInsertable<TProxy, T> insertable(T entity) {
        Objects.requireNonNull(entity, "entities is null");
        Class<T> aClass = EasyObjectUtil.typeCast(entity.getClass());
        TProxy tProxy = EntityQueryProxyManager.create(aClass);
        return new EasyEntityInsertable<>(tProxy, easyQueryClient.insertable(entity));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityInsertable<TProxy, T> insertable(Collection<T> entities) {
        Objects.requireNonNull(entities, "entities is null");
        if (EasyCollectionUtil.isEmpty(entities)) {
            return new EasyEmptyEntityInsertable<>();
        }
        T first = EasyCollectionUtil.first(entities);
        Class<T> aClass = EasyObjectUtil.typeCast(first.getClass());
        TProxy tProxy = EntityQueryProxyManager.create(aClass);
        return new EasyEntityInsertable<>(tProxy, easyQueryClient.insertable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> ExpressionUpdatable<TProxy, T> updatable(Class<T> entityClass) {
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        return new EasyExpressionUpdatable<>(tProxy, easyQueryClient.updatable(entityClass));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityUpdatable<TProxy, T> updatable(T entity) {
        Objects.requireNonNull(entity, "entity is null");
        Class<T> aClass = EasyObjectUtil.typeCast(entity.getClass());
        TProxy tProxy = EntityQueryProxyManager.create(aClass);
        return new EasyEntityUpdatable<>(tProxy, easyQueryClient.updatable(entity));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityUpdatable<TProxy, T> updatable(Collection<T> entities) {
        Objects.requireNonNull(entities, "entities is null");
        if (EasyCollectionUtil.isEmpty(entities)) {
            return new EasyEmptyEntityUpdatable<>();
        }
        T first = EasyCollectionUtil.first(entities);
        Class<T> aClass = EasyObjectUtil.typeCast(first.getClass());
        TProxy tProxy = EntityQueryProxyManager.create(aClass);
        return new EasyEntityUpdatable<>(tProxy, easyQueryClient.updatable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityDeletable<TProxy, T> deletable(T entity) {
        Objects.requireNonNull(entity, "entities is null");
        return new EasyEntityDeletable<>(easyQueryClient.deletable(entity));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityDeletable<TProxy, T> deletable(Collection<T> entities) {
        return new EasyEntityDeletable<>(easyQueryClient.deletable(entities));
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> ExpressionDeletable<TProxy, T> deletable(Class<T> entityClass) {
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        return new EasyExpressionDeletable<>(tProxy, easyQueryClient.deletable(entityClass));
    }

}
