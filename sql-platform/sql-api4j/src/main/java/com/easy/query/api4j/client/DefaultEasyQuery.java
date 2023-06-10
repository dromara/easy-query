package com.easy.query.api4j.client;

import com.easy.query.api4j.delete.EntityDeletable;
import com.easy.query.api4j.delete.ExpressionDeletable;
import com.easy.query.api4j.delete.impl.EasyEntityDeletable;
import com.easy.query.api4j.delete.impl.EasyExpressionDeletable;
import com.easy.query.api4j.insert.EasyEntityInsertable;
import com.easy.query.api4j.insert.EntityInsertable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.api4j.update.ExpressionUpdatable;
import com.easy.query.api4j.update.impl.EasyEntityUpdatable;
import com.easy.query.api4j.update.impl.EasyExpressionUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;

import java.util.Collection;

/**
 * @author xuejiaming
 * @Date: 2023/3/6 13:30
 */
public class DefaultEasyQuery implements EasyQuery {
    private final EasyQueryClient easyQueryClient;

    public DefaultEasyQuery(EasyQueryClient easyQueryClient) {
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
    public <T> Queryable<T> queryable(Class<T> clazz) {
        return new EasyQueryable<>(easyQueryClient.queryable(clazz));
    }

    @Override
    public <T> Queryable<T> queryable(String sql, Class<T> clazz) {
        return new EasyQueryable<>(easyQueryClient.queryable(sql, clazz));
    }

    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        return easyQueryClient.beginTransaction(isolationLevel);
    }

//    @Override
//    public <T> Insert<T> insert(Class<T> clazz) {
//        return new MySQLInsert<>(clazz,new InsertContext(runtimeContext));
//    }

    @Override
    public <T> EntityInsertable<T> insertable(T entity) {
        return new EasyEntityInsertable<>(easyQueryClient.insertable(entity));
    }

    @Override
    public <T> EntityInsertable<T> insertable(Collection<T> entities) {
        return new EasyEntityInsertable<>(easyQueryClient.insertable(entities));
    }

    @Override
    public <T> ExpressionUpdatable<T> updatable(Class<T> entityClass) {
        return new EasyExpressionUpdatable<>(easyQueryClient.updatable(entityClass));
    }

    @Override
    public <T> EntityUpdatable<T> updatable(T entity) {
        return new EasyEntityUpdatable<>(easyQueryClient.updatable(entity));
    }

    @Override
    public <T> EntityUpdatable<T> updatable(Collection<T> entities) {
        return new EasyEntityUpdatable<>(easyQueryClient.updatable(entities));
    }

    @Override
    public <T> EntityDeletable<T> deletable(T entity) {
        return new EasyEntityDeletable<>(easyQueryClient.deletable(entity));
    }
    @Override
    public <T> EntityDeletable<T> deletable(Collection<T> entities) {
        return new EasyEntityDeletable<>(easyQueryClient.deletable(entities));
    }


    @Override
    public <T> ExpressionDeletable<T> deletable(Class<T> entityClass) {
        return new EasyExpressionDeletable<>(easyQueryClient.deletable(entityClass));
    }

    @Override
    public boolean addTracking(Object entity) {
        return easyQueryClient.addTracking(entity);
    }
}
