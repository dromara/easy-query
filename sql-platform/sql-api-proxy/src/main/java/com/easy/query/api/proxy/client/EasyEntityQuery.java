package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/9/19 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyEntityQuery {
    EasyQueryClient getEasyQueryClient();
    QueryRuntimeContext getRuntimeContext();

    default <T> List<T> sqlQuery(String sql, Class<T> clazz) {
        return getEasyQueryClient().sqlQuery(sql, clazz);
    }

    default <T> List<T> sqlQuery(String sql, Class<T> clazz, List<Object> parameters) {
        return getEasyQueryClient().sqlQuery(sql,clazz,parameters);
    }

    default  <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters){
        return getEasyQueryClient().sqlEasyQuery(sql,clazz,parameters);
    }

    default List<Map<String, Object>> sqlQueryMap(String sql) {
        return getEasyQueryClient().sqlQueryMap(sql);
    }

    default List<Map<String, Object>> sqlQueryMap(String sql, List<Object> parameters){
        return getEasyQueryClient().sqlQueryMap(sql,parameters);
    }

    default long sqlExecute(String sql) {
        return getEasyQueryClient().sqlExecute(sql);
    }

    default long sqlExecute(String sql, List<Object> parameters){
        return getEasyQueryClient().sqlExecute(sql,parameters);
    }
    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityQueryable<TProxy, T> queryable(Class<T> entityClass);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityQueryable<TProxy, T> queryable(String sql, Class<T> entityClass);

    default Transaction beginTransaction() {
        return beginTransaction(null);
    }

    /**
     * 数据库隔离级别:
     * Connection.TRANSACTION_READ_UNCOMMITTED,
     * Connection.TRANSACTION_READ_COMMITTED,
     * Connection.TRANSACTION_REPEATABLE_READ,
     * Connection.TRANSACTION_SERIALIZABLE
     *
     * @param isolationLevel null表示不使用任何指定隔离级别,使用默认的
     * @return
     */
    Transaction beginTransaction(Integer isolationLevel);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityInsertable<TProxy,T> insertable(T entity);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityInsertable<TProxy,T> insertable(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> ExpressionUpdatable<TProxy,T> updatable(Class<T> entityClass);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityUpdatable<TProxy,T> updatable(T entity);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityUpdatable<TProxy,T> updatable(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityDeletable<TProxy,T> deletable(T entity);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> EntityDeletable<TProxy,T> deletable(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T,TProxy>> ExpressionDeletable<TProxy,T> deletable(Class<T> entityClass);

    /**
     * 添加到当前环境的追踪里面
     * 如果当前线程未开启追踪那么添加直接忽略无效
     * 相同对象多次追踪结果还是true
     *
     * @param entity
     * @return true:添加成功,false:已经存在相同对象 或者未开启追踪
     */
    boolean addTracking(Object entity);
    boolean removeTracking(Object entity);
    EntityState getTrackEntityStateNotNull(Object entity);

    default MapClientInsertable<Map<String, Object>> mapInsertable(Map<String, Object> map) {
        return getEasyQueryClient().mapInsertable(map);
    }

    default MapClientInsertable<Map<String, Object>> mapInsertable(Collection<Map<String, Object>> maps) {
        return getEasyQueryClient().mapInsertable(maps);
    }
    default MapClientUpdatable<Map<String, Object>> mapUpdatable(Map<String, Object> map) {
        return getEasyQueryClient().mapUpdatable(map);
    }

    default MapClientUpdatable<Map<String, Object>> mapUpdatable(Collection<Map<String, Object>> maps) {
        return getEasyQueryClient().mapUpdatable(maps);
    }
}
