package com.easy.query.api4j.client;

import com.easy.query.api4j.delete.EntityDeletable;
import com.easy.query.api4j.delete.ExpressionDeletable;
import com.easy.query.api4j.delete.impl.EasyEntityDeletable;
import com.easy.query.api4j.delete.impl.EasyExpressionDeletable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.api4j.update.ExpressionUpdatable;
import com.easy.query.api4j.update.impl.EasyEntityUpdatable;
import com.easy.query.api4j.update.impl.EasyExpressionUpdatable;
import com.easy.query.core.api.client.EasyObjectQuery;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author xuejiaming
 * @Date: 2023/3/6 13:30
 */
public class DefaultEasyQuery implements EasyQuery {
    private final EasyObjectQuery easyObjectQuery;

    public DefaultEasyQuery(EasyObjectQuery easyObjectQuery) {
        this.easyObjectQuery = easyObjectQuery;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return easyObjectQuery.getRuntimeContext();
    }

    @Override
    public <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters) {
        return easyObjectQuery.sqlEasyQuery(sql, clazz, parameters);
    }

    @Override
    public List<Map<String, Object>> sqlQueryMap(String sql, List<Object> parameters) {
        return easyObjectQuery.sqlQueryMap(sql, parameters);
    }

    @Override
    public long sqlExecute(String sql, List<Object> parameters) {
        return easyObjectQuery.sqlExecute(sql, parameters);
    }

    @Override
    public <T> Queryable<T> queryable(Class<T> clazz) {
        return new EasyQueryable<>(easyObjectQuery.queryable(clazz));
    }

    @Override
    public <T> Queryable<T> queryable(String sql, Class<T> clazz) {
        return new EasyQueryable<>(easyObjectQuery.queryable(sql, clazz));
    }

    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        return easyObjectQuery.beginTransaction(isolationLevel);
    }

//    @Override
//    public <T> Insert<T> insert(Class<T> clazz) {
//        return new MySQLInsert<>(clazz,new InsertContext(runtimeContext));
//    }

    @Override
    public <T> Insertable<T> insertable(T entity) {
        return easyObjectQuery.insertable(entity);
    }

    @Override
    public <T> Insertable<T> insertable(Collection<T> entities) {
        return easyObjectQuery.insertable(entities);
    }

    @Override
    public <T> ExpressionUpdatable<T> updatable(Class<T> entityClass) {
        return new EasyExpressionUpdatable<>(easyObjectQuery.updatable(entityClass));
    }

    @Override
    public <T> EntityUpdatable<T> updatable(T entity) {
        return new EasyEntityUpdatable<>(easyObjectQuery.updatable(entity));
    }

    @Override
    public <T> EntityUpdatable<T> updatable(Collection<T> entities) {
        return new EasyEntityUpdatable<>(easyObjectQuery.updatable(entities));
    }

    @Override
    public <T> EntityDeletable<T> deletable(T entity) {
        return new EasyEntityDeletable<>(easyObjectQuery.deletable(entity));
    }
    @Override
    public <T> EntityDeletable<T> deletable(Collection<T> entities) {
        return new EasyEntityDeletable<>(easyObjectQuery.deletable(entities));
    }


    @Override
    public <T> ExpressionDeletable<T> deletable(Class<T> entityClass) {
        return new EasyExpressionDeletable<>(easyObjectQuery.deletable(entityClass));
    }

    @Override
    public boolean addTracking(Object entity) {
        return easyObjectQuery.addTracking(entity);
    }
}
