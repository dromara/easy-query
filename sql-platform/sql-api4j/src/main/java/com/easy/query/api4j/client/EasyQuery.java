package com.easy.query.api4j.client;

import com.easy.query.api4j.delete.EntityDeletable;
import com.easy.query.api4j.delete.ExpressionDeletable;
import com.easy.query.api4j.insert.EntityInsertable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.api4j.update.ExpressionUpdatable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.configuration.LoadIncludeConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/2/5 21:27
 * lambda表达式可查询客户端
 *
 * @author xuejiaming
 */
public interface EasyQuery {
    /**
     * 获取属性模式的查询客户端
     * @return 属性模式查询客户端
     */
    EasyQueryClient getEasyQueryClient();

    /**
     * 获取当前easy-query的上下文
     * @return easy-query的上下文
     */
    QueryRuntimeContext getRuntimeContext();

    /**
     * 按sql查询结果映射到 {@param clazz} 对象上
     * @param sql 查询的sql
     * @param clazz 返回的结果字节
     * @return 查询结果集
     * @param <T> 返回结果类型
     */
    default <T> List<T> sqlQuery(String sql, Class<T> clazz) {
        return getEasyQueryClient().sqlQuery(sql, clazz);
    }

    /**
     * 按sql查询结果映射到 {@param clazz} 对象上
     * @param sql 查询的sql
     * @param clazz 返回的结果字节
     * @param parameters sql参数
     * @return 查询结果集
     * @param <T> 返回结果类型
     */
    default <T> List<T> sqlQuery(String sql, Class<T> clazz, List<Object> parameters) {
        return getEasyQueryClient().sqlQuery(sql, clazz, parameters);
    }

    /**
     * 按sql查询结果映射到 {@param clazz} 对象上
     * @param sql 查询的sql
     * @param clazz 返回的结果字节
     * @param parameters sql参数 easyQuery参数
     * @return 查询结果集
     * @param <T> 返回结果类型
     */
    default <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters) {
        return getEasyQueryClient().sqlEasyQuery(sql, clazz, parameters);
    }

    /**
     * 按sql查询结果映射到 Map 对象上
     * @param sql 查询的sql
     * @return 查询结果集
     */
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

    /**
     * 创建一个可查询表达式
     * @param clazz 被查询的对象字节
     * @return 可查询的表达式接口
     * @param <T> 被查询的对象类型
     */
    <T> Queryable<T> queryable(Class<T> clazz);

    /**
     * 通过sql语句匿名表模式创建一个可查询表达式
     * select * from ( {@param sql} ) t
     * @param sql sql语句
     * @param clazz 被查询的对象字节
     * @return 可查询的表达式接口
     * @param <T> 被查询的对象类型
     */
    default <T> Queryable<T> queryable(String sql, Class<T> clazz) {
        return queryable(sql, clazz, Collections.emptyList());
    }

    <T> Queryable<T> queryable(String sql, Class<T> clazz, Collection<Object> sqlParams);

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

    <T> EntityInsertable<T> insertable(T entity);

    <T> EntityInsertable<T> insertable(Collection<T> entities);

    /**
     * 表达式更新自定义条件和更新列
     * @param entityClass
     * @return
     * @param <T>
     */
    <T> ExpressionUpdatable<T> updatable(Class<T> entityClass);

    /**
     * 对象更新更新默认条件为主键
     * @param entity
     * @return
     * @param <T>
     */
    <T> EntityUpdatable<T> updatable(T entity);

    /**
     * 对象更新更新默认条件为主键
     * @param entities
     * @return
     * @param <T>
     */
    <T> EntityUpdatable<T> updatable(Collection<T> entities);


    /**
     * 对象删除,删除条件为对象主键
     * @param entity
     * @return
     * @param <T>
     */
    <T> EntityDeletable<T> deletable(T entity);

    /**
     * 对象删除,删除条件为对象主键
     * @param entities
     * @return
     * @param <T>
     */
    <T> EntityDeletable<T> deletable(Collection<T> entities);

    /**
     * 表达式删除
     * @param entityClass
     * @return
     * @param <T>
     */
    <T> ExpressionDeletable<T> deletable(Class<T> entityClass);

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

    default <T> void loadInclude(T entity,Property<T,?> navigateProperty){
        loadInclude(Collections.singletonList(entity),navigateProperty);
    }
    default <T> void loadInclude(T entity,Property<T,?> navigateProperty, SQLExpression1<LoadIncludeConfiguration> configure){
        loadInclude(Collections.singletonList(entity),navigateProperty,configure);
    }
    default <T> void loadInclude(List<T> entities,Property<T,?> navigateProperty){
        loadInclude(entities,navigateProperty,null);
    }
   default <T> void loadInclude(List<T> entities, Property<T,?> navigateProperty, SQLExpression1<LoadIncludeConfiguration> configure){
       getEasyQueryClient().loadInclude(entities, EasyLambdaUtil.getPropertyName(navigateProperty),configure);
   }
}
