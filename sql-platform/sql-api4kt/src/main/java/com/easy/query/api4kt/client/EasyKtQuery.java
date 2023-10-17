package com.easy.query.api4kt.client;

import com.easy.query.api4kt.delete.KtEntityDeletable;
import com.easy.query.api4kt.delete.KtExpressionDeletable;
import com.easy.query.api4kt.func.KtLambdaSQLFunc;
import com.easy.query.api4kt.insert.KtEntityInsertable;
import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.update.KtEntityUpdatable;
import com.easy.query.api4kt.update.KtExpressionUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xuejiaming
 * @FileName: JQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:27
 */
public interface EasyKtQuery {
    EasyQueryClient getEasyQueryClient();

    QueryRuntimeContext getRuntimeContext();

    default <T> List<T> sqlQuery(String sql, Class<T> clazz) {
        return getEasyQueryClient().sqlQuery(sql, clazz);
    }

    default <T> List<T> sqlQuery(String sql, Class<T> clazz, List<Object> parameters) {
        return getEasyQueryClient().sqlQuery(sql, clazz, parameters);
    }

    default <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters) {
        return getEasyQueryClient().sqlEasyQuery(sql, clazz, parameters);
    }

    default List<Map<String, Object>> sqlQueryMap(String sql) {
        return getEasyQueryClient().sqlQueryMap(sql);
    }

    default List<Map<String, Object>> sqlQueryMap(String sql, List<Object> parameters) {
        return getEasyQueryClient().sqlQueryMap(sql, parameters);
    }

    default long sqlExecute(String sql) {
        return getEasyQueryClient().sqlExecute(sql);
    }

    default long sqlExecute(String sql, List<Object> parameters) {
        return getEasyQueryClient().sqlExecute(sql, parameters);
    }

    <T> KtQueryable<T> queryable(Class<T> clazz);

    default <T> KtQueryable<T> queryable(String sql, Class<T> clazz) {
        return queryable(sql, clazz, Collections.emptyList());
    }

    <T> KtQueryable<T> queryable(String sql, Class<T> clazz, Collection<Object> sqlParams);

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

    <T> KtEntityInsertable<T> insertable(T entity);

    <T> KtEntityInsertable<T> insertable(Collection<T> entities);

    <T> KtExpressionUpdatable<T> updatable(Class<T> entityClass);

    <T> KtEntityUpdatable<T> updatable(T entity);

    <T> KtEntityUpdatable<T> updatable(Collection<T> entities);

    <T> KtEntityDeletable<T> deletable(T entity);

    <T> KtEntityDeletable<T> deletable(Collection<T> entities);

    <T> KtExpressionDeletable<T> deletable(Class<T> entityClass);

    /**
     * 添加到当前环境的追踪里面
     * 如果当前线程未开启追踪那么添加直接忽略无效
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
