package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.api.proxy.entity.save.EntitySavable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.configuration.LoadIncludeConfiguration;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.proxy.DbSet;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.trigger.TriggerEvent;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * create time 2025/7/24 16:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface BaseEntityClient extends EasyBaseQuery {
    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(Class<T> entityClass);

    <TProxy extends ProxyEntity<TProxy, T>, T> EntityQueryable<TProxy, T> queryable(TProxy tProxy);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(String sql, Class<T> entityClass);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> queryable(String sql, Class<T> entityClass, Collection<Object> params);


    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityInsertable<TProxy, T> insertable(T entity);

    <TProxy extends ProxyEntity<TProxy, T>, T> EntityInsertable<TProxy, T> insertable(TProxy tProxy);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityInsertable<TProxy, T> insertable(Collection<T> entities);

    /**
     * 表达式更新 更新条件和set值需要自定义
     *
     * @param entityClass
     * @param <TProxy>
     * @param <T>
     * @return
     */
    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> ExpressionUpdatable<TProxy, T> updatable(Class<T> entityClass);

    <TProxy extends ProxyEntity<TProxy, T>, T> ExpressionUpdatable<TProxy, T> expressionUpdatable(TProxy tProxy);

    /**
     * 对象更新 更新条件默认是对象的主键
     *
     * @param entity
     * @param <TProxy>
     * @param <T>
     * @return
     */
    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityUpdatable<TProxy, T> updatable(T entity);

    <TProxy extends ProxyEntity<TProxy, T>, T> EntityUpdatable<TProxy, T> entityUpdatable(TProxy tProxy);

    /**
     * 对象更新 更新条件默认是对象的主键
     *
     * @param entities
     * @param <TProxy>
     * @param <T>
     * @return
     */
    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityUpdatable<TProxy, T> updatable(Collection<T> entities);

    /**
     * 对象删除 删除条件为对象的主键
     *
     * @param entity
     * @param <TProxy>
     * @param <T>
     * @return
     */
    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityDeletable<TProxy, T> deletable(T entity);

    <TProxy extends ProxyEntity<TProxy, T>, T> ExpressionDeletable<TProxy, T> expressionDeletable(TProxy tProxy);

    <TProxy extends ProxyEntity<TProxy, T>, T> EntityDeletable<TProxy, T> entityDeletable(TProxy tProxy);

    /**
     * 对象删除 删除条件为对象的主键
     *
     * @param entities
     * @param <TProxy>
     * @param <T>
     * @return
     */

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityDeletable<TProxy, T> deletable(Collection<T> entities);

    /**
     * 表达式删除,删除条件自定义
     *
     * @param entityClass
     * @param <TProxy>
     * @param <T>
     * @return
     */
    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> ExpressionDeletable<TProxy, T> deletable(Class<T> entityClass);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntitySavable<TProxy, T> savable(T entity);

    <TProxy extends ProxyEntity<TProxy, T>, T> EntitySavable<TProxy, T> savable(TProxy tProxy);

    <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntitySavable<TProxy, T> savable(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy, T>, T> DbSet<TProxy, T> createDbSet(TProxy tProxy);

    default <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> void loadInclude(T entity, SQLFuncExpression1<TProxy, PropColumn> navigateProperty) {
        if (entity == null) {
            return;
        }
        loadInclude(Collections.singletonList(entity), navigateProperty);
    }

    default <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> void loadInclude(T entity, SQLFuncExpression1<TProxy, PropColumn> navigateProperty, SQLActionExpression1<LoadIncludeConfiguration> configure) {
        if (entity == null) {
            return;
        }
        loadInclude(Collections.singletonList(entity), navigateProperty, configure);
    }

    default <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> void loadInclude(List<T> entities, SQLFuncExpression1<TProxy, PropColumn> navigateProperty) {
        loadInclude(entities, navigateProperty, null);
    }

    default <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> void loadInclude(List<T> entities, SQLFuncExpression1<TProxy, PropColumn> navigateProperty, SQLActionExpression1<LoadIncludeConfiguration> configure) {
        if (EasyCollectionUtil.isEmpty(entities)) {
            return;
        }
        Class<T> entityClass = EasyObjectUtil.typeCast(entities.get(0).getClass());
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        PropColumn propColumn = navigateProperty.apply(tProxy);
        getEasyQueryClient().loadInclude(entities, propColumn.getValue(), configure);
    }

    default DatabaseCodeFirst getDatabaseCodeFirst() {
        return getEasyQueryClient().getDatabaseCodeFirst();
    }

    default void setMigrationParser(MigrationEntityParser migrationParser) {
        getEasyQueryClient().setMigrationParser(migrationParser);
    }

    default void addTriggerListener(Consumer<TriggerEvent> eventConsumer) {
        getEasyQueryClient().addTriggerListener(eventConsumer);
    }

    /**
     * 按包加载数据库实体对象
     *
     * @param packageNames
     */
    default void loadTableEntityByPackage(String... packageNames) {
        getEasyQueryClient().loadTableEntityByPackage(packageNames);
    }

    /**
     * 按包加载数据库实体对象并且自动执行ddl操作
     *
     * @param packageNames
     */
    default void syncTableByPackage(String... packageNames) {
        getEasyQueryClient().syncTableByPackage(packageNames);
    }

    default void syncTableByPackage(int groupSize, String... packageNames) {
        getEasyQueryClient().syncTableByPackage(groupSize, packageNames);
    }

    /**
     * 开启一个新的追踪环境
     * @param trackHandle
     * @return
     * @param <T>
     */
    default <T> T trackScope(SQLFuncExpression<T> trackHandle) {
        return getEasyQueryClient().trackScope(trackHandle);
    }
    default <T> T noTackScope(SQLFuncExpression<T> trackHandle) {
        return getEasyQueryClient().noTackScope(trackHandle);
    }

}
