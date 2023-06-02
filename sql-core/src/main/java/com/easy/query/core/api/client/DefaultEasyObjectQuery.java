package com.easy.query.core.api.client;

import com.easy.query.core.api.SQLObjectApiFactory;
import com.easy.query.core.basic.api.delete.EntityObjectDeletable;
import com.easy.query.core.basic.api.delete.ExpressionObjectDeletable;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.basic.api.select.ObjectQueryable;
import com.easy.query.core.basic.api.update.EntityObjectUpdatable;
import com.easy.query.core.basic.api.update.ExpressionObjectUpdatable;
import com.easy.query.core.basic.jdbc.con.ConnectionManager;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.basic.plugin.track.TrackContext;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.context.QueryRuntimeContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author xuejiaming
 * @Date: 2023/3/6 13:30
 */
public class DefaultEasyObjectQuery implements EasyObjectQuery {
    private final QueryRuntimeContext runtimeContext;
    private final SQLObjectApiFactory easySQLApiFactory;

    public DefaultEasyObjectQuery(QueryRuntimeContext runtimeContext) {

        this.runtimeContext = runtimeContext;
        easySQLApiFactory = runtimeContext.getSQLObjectApiFactory();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlQuery(sql, clazz, parameters);
    }

    @Override
    public List<Map<String, Object>> sqlQueryMap(String sql, List<Object> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlQueryMap(sql, parameters);
    }

    @Override
    public long sqlExecute(String sql, List<Object> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(sql, parameters);
    }

    @Override
    public <T> ObjectQueryable<T> queryable(Class<T> clazz) {
        return easySQLApiFactory.createQueryable(clazz, runtimeContext);
    }

    @Override
    public <T> ObjectQueryable<T> queryable(String sql, Class<T> clazz) {
        return easySQLApiFactory.createQueryable(sql, clazz, runtimeContext);
    }

    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        ConnectionManager connectionManager = runtimeContext.getConnectionManager();
        return connectionManager.beginTransaction(isolationLevel);
    }

//    @Override
//    public <T> Insert<T> insert(Class<T> clazz) {
//        return new MySQLInsert<>(clazz,new InsertContext(runtimeContext));
//    }

    @Override
    public <T> Insertable<T> insertable(T entity) {
        if (entity == null) {
            return easySQLApiFactory.createEmptyInsertable(runtimeContext);
        }
        return easySQLApiFactory.createInsertable((Class<T>) entity.getClass(), runtimeContext).insert(entity);
    }

    @Override
    public <T> Insertable<T> insertable(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return easySQLApiFactory.createEmptyInsertable(runtimeContext);
        }
        return easySQLApiFactory.createInsertable((Class<T>) entities.iterator().next().getClass(), runtimeContext).insert(entities);
    }

    @Override
    public <T> ExpressionObjectUpdatable<T> updatable(Class<T> entityClass) {
        return easySQLApiFactory.createExpressionUpdatable(entityClass, runtimeContext);
    }

    @Override
    public <T> EntityObjectUpdatable<T> updatable(T entity) {
        if (entity == null) {
            return easySQLApiFactory.createEmptyEntityUpdatable();
        }
        return easySQLApiFactory.createEntityUpdatable(entity, runtimeContext);
    }

    @Override
    public <T> EntityObjectUpdatable<T> updatable(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return easySQLApiFactory.createEmptyEntityUpdatable();
        }
        return easySQLApiFactory.createEntityUpdatable(entities, runtimeContext);
    }

    @Override
    public <T> EntityObjectDeletable<T> deletable(T entity) {
        if (entity == null) {
            return easySQLApiFactory.createEmptyEntityDeletable();
        }
        return easySQLApiFactory.createEntityDeletable(entity, runtimeContext);
    }

    @Override
    public <T> EntityObjectDeletable<T> deletable(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return easySQLApiFactory.createEmptyEntityDeletable();
        }
        return easySQLApiFactory.createEntityDeletable(entities, runtimeContext);
    }


    @Override
    public <T> ExpressionObjectDeletable<T> deletable(Class<T> entityClass) {
        return easySQLApiFactory.createExpressionDeletable(entityClass, runtimeContext);
    }

    @Override
    public boolean addTracking(Object entity) {
        TrackManager trackManager = runtimeContext.getTrackManager();
        TrackContext currentTrackContext = trackManager.getCurrentTrackContext();
        if (currentTrackContext != null) {
            return currentTrackContext.addTracking(entity);
        }
        return false;
    }
}
