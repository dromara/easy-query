package com.easy.query.core.api.client;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.api.SQLApiFactory;
import com.easy.query.core.basic.api.delete.EntityDeletable;
import com.easy.query.core.basic.jdbc.con.ConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.basic.plugin.track.TrackContext;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2023/3/6 13:30
 * @author xuejiaming
 */
public class DefaultEasyQuery implements EasyQuery {
    private final QueryRuntimeContext runtimeContext;
    private final SQLApiFactory easySQLApiFactory;
    public DefaultEasyQuery(QueryRuntimeContext runtimeContext){

        this.runtimeContext = runtimeContext;
        easySQLApiFactory =runtimeContext.getSQLApiFactory();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlQuery(sql,clazz,parameters);
    }

    @Override
    public List<Map<String, Object>> sqlQueryMap(String sql, List<Object> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlQueryMap(sql,parameters);
    }

    @Override
    public long sqlExecute(String sql, List<Object> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(sql,parameters);
    }

    @Override
    public <T> Queryable<T> queryable(Class<T> clazz, String alias) {
        return easySQLApiFactory.createQueryable(clazz,runtimeContext,alias);
    }

    @Override
    public <T> Queryable<T> queryable(String sql, Class<T> clazz, String alias) {
        return easySQLApiFactory.createQueryable(sql,clazz,runtimeContext,alias);
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
        if(entity==null){
            return easySQLApiFactory.createEmptyInsertable(runtimeContext,null);
        }
        return easySQLApiFactory.createInsertable((Class<T>) entity.getClass(),runtimeContext,null).insert(entity);
    }

    @Override
    public <T> Insertable<T> insertable(Collection<T> entities) {
        if(entities==null||entities.isEmpty()){
            return easySQLApiFactory.createEmptyInsertable(runtimeContext,null);
        }
        return easySQLApiFactory.createInsertable((Class<T>) entities.iterator().next().getClass(),runtimeContext,null).insert(entities);
    }

    @Override
    public <T> ExpressionUpdatable<T> updatable(Class<T> entityClass) {
        return easySQLApiFactory.createExpressionUpdatable(entityClass,runtimeContext,null);
    }

    @Override
    public <T> EntityUpdatable<T> updatable(T entity) {
        if(entity==null){
            return easySQLApiFactory.createEmptyEntityUpdatable();
        }
        return easySQLApiFactory.createEntityUpdatable(entity,runtimeContext,null);
    }

    @Override
    public <T> EntityUpdatable<T> updatable(Collection<T> entities) {
        if(entities==null||entities.isEmpty()){
            return easySQLApiFactory.createEmptyEntityUpdatable();
        }
        return easySQLApiFactory.createEntityUpdatable(entities,runtimeContext,null);
    }

    @Override
    public <T> EntityDeletable<T> deletable(T entity) {
        if(entity==null){
            return easySQLApiFactory.createEmptyEntityDeletable();
        }
        return easySQLApiFactory.createEntityDeletable(entity,runtimeContext,null);
    }
    @Override
    public <T> EntityDeletable<T> deletable(Collection<T> entities) {
        if(entities==null||entities.isEmpty()){
            return easySQLApiFactory.createEmptyEntityDeletable();
        }
        return easySQLApiFactory.createEntityDeletable(entities,runtimeContext,null);
    }


    @Override
    public <T> ExpressionDeletable<T> deletable(Class<T> entityClass) {
        return easySQLApiFactory.createExpressionDeletable(entityClass,runtimeContext,null);
    }

    @Override
    public boolean addTracking(Object entity) {
        TrackManager trackManager = runtimeContext.getTrackManager();
        TrackContext currentTrackContext = trackManager.getCurrentTrackContext();
        if(currentTrackContext!=null){
            return currentTrackContext.addTracking(entity);
        }
        return false;
    }
}
