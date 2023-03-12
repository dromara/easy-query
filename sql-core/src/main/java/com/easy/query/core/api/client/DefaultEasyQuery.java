package com.easy.query.core.api.client;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.basic.api.delete.EntityDeletable;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;

import java.util.Collection;

/**
 * @FileName: EasySqlQuery.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:30
 * @Created by xuejiaming
 */
public class DefaultEasyQuery implements EasyQuery {
    private final EasyQueryRuntimeContext runtimeContext;
    private final EasySqlApiFactory easySqlApiFactory;
    public DefaultEasyQuery(EasyQueryRuntimeContext runtimeContext){

        this.runtimeContext = runtimeContext;
        easySqlApiFactory=runtimeContext.getSqlApiFactory();
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <T> Queryable<T> query(Class<T> clazz) {
        return easySqlApiFactory.createQueryable(clazz,runtimeContext);
    }

    @Override
    public <T> Queryable<T> query(Class<T> clazz, String alias) {
        return easySqlApiFactory.createQueryable(clazz,runtimeContext,alias);
    }
    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        return connectionManager.beginTransaction(isolationLevel);
    }

//    @Override
//    public <T> Insert<T> insert(Class<T> clazz) {
//        return new MySQLInsert<>(clazz,new InsertContext(runtimeContext));
//    }

    @Override
    public <T> Insertable<T> insert(T entity) {
        if(entity==null){
            return easySqlApiFactory.createEmptyInsertable(runtimeContext,null);
        }
        return easySqlApiFactory.createInsertable((Class<T>) entity.getClass(),runtimeContext,null).insert(entity);
    }

    @Override
    public <T> Insertable<T> insert(Collection<T> entities) {
        if(entities==null||entities.isEmpty()){
            return easySqlApiFactory.createEmptyInsertable(runtimeContext,null);
        }
        return easySqlApiFactory.createInsertable((Class<T>) entities.iterator().next().getClass(),runtimeContext,null).insert(entities);
    }

    @Override
    public <T> ExpressionUpdatable<T> update(Class<T> entityClass) {
        return easySqlApiFactory.createExpressionUpdatable(entityClass,runtimeContext,null);
    }

    @Override
    public <T> EntityUpdatable<T> update(T entity) {
        if(entity==null){
            return easySqlApiFactory.createEmptyEntityUpdatable();
        }
        return easySqlApiFactory.createEntityUpdatable(entity,runtimeContext,null);
    }

    @Override
    public <T> EntityUpdatable<T> update(Collection<T> entities) {
        if(entities==null||entities.isEmpty()){
            return easySqlApiFactory.createEmptyEntityUpdatable();
        }
        return easySqlApiFactory.createEntityUpdatable(entities,runtimeContext,null);
    }

    @Override
    public <T> EntityDeletable<T> delete(T entity) {
        if(entity==null){
            return easySqlApiFactory.createEmptyEntityDeletable();
        }
        return easySqlApiFactory.createEntityDeletable(entity,runtimeContext,null);
    }
    @Override
    public <T> EntityDeletable<T> delete(Collection<T> entities) {
        if(entities==null||entities.isEmpty()){
            return easySqlApiFactory.createEmptyEntityDeletable();
        }
        return easySqlApiFactory.createEntityDeletable(entities,runtimeContext,null);
    }


    @Override
    public <T> ExpressionDeletable<T> delete(Class<T> entityClass) {
        return easySqlApiFactory.createExpressionDeletable(entityClass,runtimeContext,null);
    }
}
