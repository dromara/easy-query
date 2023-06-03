package com.easy.query.api4kt.client;

import com.easy.query.api4kt.delete.KtEntityDeletable;
import com.easy.query.api4kt.delete.KtExpressionDeletable;
import com.easy.query.api4kt.delete.impl.EasyKtEntityDeletable;
import com.easy.query.api4kt.delete.impl.EasyKtExpressionDeletable;
import com.easy.query.api4kt.insert.EasyKtEntityInsertable;
import com.easy.query.api4kt.insert.KtEntityInsertable;
import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.update.KtEntityUpdatable;
import com.easy.query.api4kt.update.KtExpressionUpdatable;
import com.easy.query.api4kt.update.impl.EasyKtEntityUpdatable;
import com.easy.query.api4kt.update.impl.EasyKtExpressionUpdatable;
import com.easy.query.core.api.client.EasyQueryClient;
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
public class DefaultEasyKtQuery implements EasyKtQuery {
    private final EasyQueryClient easyObjectQuery;

    public DefaultEasyKtQuery(EasyQueryClient easyObjectQuery) {
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
    public <T> KtQueryable<T> queryable(Class<T> clazz) {
        return new EasyKtQueryable<>(easyObjectQuery.queryable(clazz));
    }

    @Override
    public <T> KtQueryable<T> queryable(String sql, Class<T> clazz) {
        return new EasyKtQueryable<>(easyObjectQuery.queryable(sql, clazz));
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
    public <T> KtEntityInsertable<T> insertable(T entity) {
        return new EasyKtEntityInsertable<>(easyObjectQuery.insertable(entity));
    }

    @Override
    public <T> KtEntityInsertable<T> insertable(Collection<T> entities) {
        return new EasyKtEntityInsertable<>(easyObjectQuery.insertable(entities));
    }

    @Override
    public <T> KtExpressionUpdatable<T> updatable(Class<T> entityClass) {
        return new EasyKtExpressionUpdatable<>(easyObjectQuery.updatable(entityClass));
    }

    @Override
    public <T> KtEntityUpdatable<T> updatable(T entity) {
        return new EasyKtEntityUpdatable<>(easyObjectQuery.updatable(entity));
    }

    @Override
    public <T> KtEntityUpdatable<T> updatable(Collection<T> entities) {
        return new EasyKtEntityUpdatable<>(easyObjectQuery.updatable(entities));
    }

    @Override
    public <T> KtEntityDeletable<T> deletable(T entity) {
        return new EasyKtEntityDeletable<>(easyObjectQuery.deletable(entity));
    }

    @Override
    public <T> KtEntityDeletable<T> deletable(Collection<T> entities) {
        return new EasyKtEntityDeletable<>(easyObjectQuery.deletable(entities));
    }


    @Override
    public <T> KtExpressionDeletable<T> deletable(Class<T> entityClass) {
        return new EasyKtExpressionDeletable<>(easyObjectQuery.deletable(entityClass));
    }

    @Override
    public boolean addTracking(Object entity) {
        return easyObjectQuery.addTracking(entity);
    }
}
