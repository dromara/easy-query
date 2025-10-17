package com.easy.query.api.proxy.client;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/5/19 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyBaseQuery {
    EasyQueryClient getEasyQueryClient();

    default QueryRuntimeContext getRuntimeContext() {
        return getEasyQueryClient().getRuntimeContext();
    }

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
    default Transaction beginTransaction(Integer isolationLevel) {
        return getEasyQueryClient().beginTransaction(isolationLevel);
    }


    /**
     * 添加到当前环境的追踪里面
     * 如果当前线程未开启追踪那么添加直接忽略无效
     * 相同对象多次追踪结果还是true
     *
     * @param entity
     * @return true:添加成功,false:已经存在相同对象 或者未开启追踪
     */
    default boolean addTracking(Object entity) {
        return getEasyQueryClient().addTracking(entity);
    }

    default boolean removeTracking(Object entity) {
        return getEasyQueryClient().removeTracking(entity);
    }

    default EntityState getTrackEntityStateNotNull(@NotNull Object entity) {
        return getEasyQueryClient().getTrackEntityStateNotNull(entity);
    }

    default @Nullable EntityState getTrackEntityState(@NotNull Object entity) {
        return getEasyQueryClient().getTrackEntityState(entity);
    }

    default void notTrackingThen(@NotNull Object entity, SQLActionExpression actionExpression) {
        EntityState trackEntityState = getEasyQueryClient().getTrackEntityState(entity);
        if (trackEntityState == null) {
            actionExpression.apply();
        }
    }

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
