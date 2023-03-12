package com.easy.query.core.api.client;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.delete.EntityDeletable;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.basic.api.insert.Insertable;

import java.util.Collection;

/**
 *
 * @FileName: JQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:27
 * @Created by xuejiaming
 */
public interface EasyQuery {
    EasyQueryRuntimeContext getRuntimeContext();
   default  <T> Queryable<T> queryable(Class<T> clazz){
       return queryable(clazz,"t");
   }
    <T> Queryable<T> queryable(Class<T> clazz, String alias);
    default Transaction beginTransaction(){
        return beginTransaction(null);
    }
    Transaction beginTransaction(Integer isolationLevel);

    <T> Insertable<T> insertable(T entity);
    <T> Insertable<T> insertable(Collection<T> entities);
    <T> ExpressionUpdatable<T> updatable(Class<T> entityClass);
    <T> EntityUpdatable<T> updatable(T entity);
    <T> EntityUpdatable<T> updatable(Collection<T> entities);
    <T> EntityDeletable<T> deletable(T entity);
    <T> EntityDeletable<T> deletable(Collection<T> entities);
    <T> ExpressionDeletable<T> deletable(Class<T> entityClass);
}
