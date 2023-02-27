package org.easy.query.mysql;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import org.easy.query.core.basic.api.EntityUpdate;
import org.easy.query.core.basic.api.ExpressionUpdate;
import org.easy.query.core.basic.api.Insert;
import org.easy.query.core.basic.jdbc.tx.Transaction;
import org.easy.query.core.impl.InsertContext;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.UpdateContext;
import org.easy.query.mysql.base.*;
import org.easy.query.core.abstraction.client.EasyQuery;
import org.easy.query.core.basic.api.Select1;

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
    public MySQLEasyQuery(EasyQueryRuntimeContext runtimeContext){

        this.runtimeContext = runtimeContext;
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <T1> Select1<T1> select(Class<T1> clazz) {
        return new MySQLSelect1<>(clazz,new SelectContext(runtimeContext));
    }

    @Override
    public <T1> Select1<T1> select(Class<T1> clazz, String alias) {
        return new MySQLSelect1<>(clazz,new SelectContext(runtimeContext,alias));
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
    public <T1> Insert<T1> insert(T1 entity) {
        if(entity==null){
            return new MySQLLazyInsert<T1>(new InsertContext(runtimeContext));
        }
        return new MySQLInsert<>((Class<T1>) entity.getClass(),new InsertContext(runtimeContext)).insert(entity);
    }

    @Override
    public <T1> Insert<T1> insert(Collection<T1> entities) {
        if(entities==null||entities.isEmpty()){
            return new MySQLLazyInsert<T1>(new InsertContext(runtimeContext));
        }
        MySQLInsert<T1> t1MySQLInsert = new MySQLInsert<>((Class<T1>) entities.iterator().next().getClass(), new InsertContext(runtimeContext));
        t1MySQLInsert.insert(entities);
        return t1MySQLInsert;
    }

    @Override
    public <T1> ExpressionUpdate<T1> update(Class<T1> entityClass) {
        return new MySQLExpressionUpdate<T1>(entityClass, new UpdateContext(runtimeContext,true));
    }

    @Override
    public <T1> EntityUpdate<T1> update(T1 entity) {
        if(entity==null){
            return new MySQLLazyUpdate<>();
        }
        return new MySQLEntityUpdate<>(Arrays.asList(entity), new UpdateContext(runtimeContext,false));
    }

    @Override
    public <T1> EntityUpdate<T1> update(Collection<T1> entities) {
        if(entities==null||entities.isEmpty()){
            return new MySQLLazyUpdate<>();
        }
        return new MySQLEntityUpdate<>(entities, new UpdateContext(runtimeContext,false));
    }
}
