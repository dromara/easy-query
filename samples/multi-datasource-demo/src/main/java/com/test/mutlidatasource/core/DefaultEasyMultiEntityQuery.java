package com.test.mutlidatasource.core;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * create time 2024/3/13 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyMultiEntityQuery implements EasyMultiEntityQuery {
    private final Map<String, EasyEntityQuery> multi = new ConcurrentHashMap<>();
    private final ThreadLocal<String> currentDataSource = ThreadLocal.withInitial(() -> null);
    private final EasyEntityQuery easyEntityQuery;

    public DefaultEasyMultiEntityQuery(EasyEntityQuery easyEntityQuery,Map<String,EasyEntityQuery> extraEasyEntityQueryMap) {
        this.easyEntityQuery = easyEntityQuery;
        multi.putAll(extraEasyEntityQueryMap);
    }

    @Override
    public void setCurrent(String dataSource) {
        currentDataSource.set(dataSource);
    }

    @Override
    public EasyEntityQuery getByDataSource(String dataSource) {
        EasyEntityQuery entityQuery = multi.get(dataSource);
        Objects.requireNonNull(entityQuery, "entityQuery is null");
        return entityQuery;
    }

    @Override
    public <TResult> TResult executeScope(String dataSource, Function<EasyEntityQuery, TResult> dataSourceFunction) {
        EasyEntityQuery entityQuery = multi.get(dataSource);
        Objects.requireNonNull(entityQuery, "entityQuery is null");
        return dataSourceFunction.apply(entityQuery);
    }

    @Override
    public void clear() {
        currentDataSource.remove();
    }

    private EasyEntityQuery tryGetEntityQuery(){
        String ds = currentDataSource.get();
        if(ds==null){
            return easyEntityQuery;
        }
        EasyEntityQuery entityQuery = multi.get(ds);
        Objects.requireNonNull(entityQuery, "entityQuery is null");
        return entityQuery;
    }

    @Override
    public EasyQueryClient getEasyQueryClient() {
        return tryGetEntityQuery().getEasyQueryClient();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return tryGetEntityQuery().getRuntimeContext();
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(Class<T> entityClass) {
        return tryGetEntityQuery().queryable(entityClass);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(String sql, Class<T> entityClass) {
        return tryGetEntityQuery().queryable(entityClass);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(String sql, Class<T> entityClass, Collection<Object> params) {
        return tryGetEntityQuery().queryable(sql,entityClass,params);
    }

    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        return tryGetEntityQuery().beginTransaction(isolationLevel);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityInsertable<TProxy, T> insertable(T entity) {
        return tryGetEntityQuery().insertable(entity);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityInsertable<TProxy, T> insertable(Collection<T> entities) {
        return tryGetEntityQuery().insertable(entities);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> ExpressionUpdatable<TProxy, T> updatable(Class<T> entityClass) {
        return tryGetEntityQuery().updatable(entityClass);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityUpdatable<TProxy, T> updatable(T entity) {
        return tryGetEntityQuery().updatable(entity);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityUpdatable<TProxy, T> updatable(Collection<T> entities) {
        return tryGetEntityQuery().updatable(entities);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityDeletable<TProxy, T> deletable(T entity) {
        return tryGetEntityQuery().deletable(entity);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityDeletable<TProxy, T> deletable(Collection<T> entities) {
        return tryGetEntityQuery().deletable(entities);
    }

    @Override
    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> ExpressionDeletable<TProxy, T> deletable(Class<T> entityClass) {
        return tryGetEntityQuery().deletable(entityClass);
    }

    @Override
    public boolean addTracking(Object entity) {
        return tryGetEntityQuery().addTracking(entity);
    }

    @Override
    public boolean removeTracking(Object entity) {
        return tryGetEntityQuery().removeTracking(entity);
    }

    @Override
    public EntityState getTrackEntityStateNotNull(Object entity) {
        return tryGetEntityQuery().getTrackEntityStateNotNull(entity);
    }
}
