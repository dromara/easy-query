package org.easy.query.mysql;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.EasySqlApiFactory;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.delete.EasyDelete;
import org.easy.query.core.basic.api.delete.EasyExpressionDelete;
import org.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import org.easy.query.core.basic.api.update.EntityUpdate;
import org.easy.query.core.basic.api.update.ExpressionUpdate;
import org.easy.query.core.basic.api.insert.Insertable;
import org.easy.query.core.basic.jdbc.tx.Transaction;
import org.easy.query.core.query.EasySqlExpressionContext;
import org.easy.query.mysql.base.*;
import org.easy.query.core.abstraction.client.EasyQuery;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @FileName: DefaultJQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:28
 * @Created by xuejiaming
 */
public class MySQLEasyQuery implements EasyQuery {
    private final EasyQueryRuntimeContext runtimeContext;
    private final EasySqlApiFactory easySqlApiFactory;
    public MySQLEasyQuery(EasyQueryRuntimeContext runtimeContext){

        this.runtimeContext = runtimeContext;
        easySqlApiFactory=runtimeContext.getSqlApiFactory();
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <T1> Queryable<T1> query(Class<T1> clazz) {
        return easySqlApiFactory.createQueryable(clazz,runtimeContext);
    }

    @Override
    public <T1> Queryable<T1> query(Class<T1> clazz, String alias) {
        return easySqlApiFactory.createQueryable(clazz,runtimeContext,alias);
    }
    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        return connectionManager.beginTransaction(isolationLevel);
    }

//    @Override
//    public <T1> Insert<T1> insert(Class<T1> clazz) {
//        return new MySQLInsert<>(clazz,new InsertContext(runtimeContext));
//    }

    @Override
    public <T1> Insertable<T1> insert(T1 entity) {
        if(entity==null){
            return easySqlApiFactory.createEmptyInsertable(runtimeContext,null);
        }
        return easySqlApiFactory.createInsertable((Class<T1>) entity.getClass(),runtimeContext,null).insert(entity);
    }

    @Override
    public <T1> Insertable<T1> insert(Collection<T1> entities) {
        if(entities==null||entities.isEmpty()){
            return easySqlApiFactory.createEmptyInsertable(runtimeContext,null);
        }
        return easySqlApiFactory.createInsertable((Class<T1>) entities.iterator().next().getClass(),runtimeContext,null).insert(entities);
    }

    @Override
    public <T1> ExpressionUpdate<T1> update(Class<T1> entityClass) {
        return new MySQLExpressionUpdate<T1>(entityClass, new MySQLUpdateExpression(new EasySqlExpressionContext(runtimeContext,null),true));
    }

    @Override
    public <T1> EntityUpdate<T1> update(T1 entity) {
        if(entity==null){
            return new MySQLLazyUpdate<>();
        }
        return new MySQLEntityUpdate<>(Arrays.asList(entity), new MySQLUpdateExpression(new EasySqlExpressionContext(runtimeContext,null),false));
    }

    @Override
    public <T1> EntityUpdate<T1> update(Collection<T1> entities) {
        if(entities==null||entities.isEmpty()){
            return new MySQLLazyUpdate<>();
        }
        return new MySQLEntityUpdate<>(entities, new MySQLUpdateExpression(new EasySqlExpressionContext(runtimeContext,null),false));
    }

    @Override
    public <T1> EasyDelete<T1> delete(Collection<T1> entities) {
        if(entities==null||entities.isEmpty()){
            return new MySQLEmptyDelete<>();
        }
        return new MySQLDelete<>(entities,new MySQLDeleteExpression(new EasySqlExpressionContext(runtimeContext,null),false));
    }

    @Override
    public <T1> EasyExpressionDelete<T1> delete(Class<T1> entityClass) {
        return new MySQLExpressionDelete<T1>(entityClass,new MySQLDeleteExpression(new EasySqlExpressionContext(runtimeContext,null),true));
    }
}
