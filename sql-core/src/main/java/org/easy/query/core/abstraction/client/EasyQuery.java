package org.easy.query.core.abstraction.client;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.api.delete.EasyDelete;
import org.easy.query.core.basic.api.delete.EasyExpressionDelete;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.update.EntityUpdate;
import org.easy.query.core.basic.api.update.ExpressionUpdate;
import org.easy.query.core.basic.api.insert.Insert;
import org.easy.query.core.basic.jdbc.tx.Transaction;

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
    <T1> Queryable<T1> query(Class<T1> clazz);
    <T1> Queryable<T1> query(Class<T1> clazz, String alias);
    default Transaction beginTransaction(){
        return beginTransaction(null);
    }
    Transaction beginTransaction(Integer isolationLevel);

    <T1> Insert<T1> insert(T1 entity);
    <T1> Insert<T1> insert(Collection<T1> entities);
    <T1> ExpressionUpdate<T1> update(Class<T1> entityClass);
    <T1> EntityUpdate<T1> update(T1 entity);
    <T1> EntityUpdate<T1> update(Collection<T1> entities);
    <T1> EasyDelete<T1> delete(Collection<T1> entities);
    <T1> EasyExpressionDelete<T1> delete(Class<T1> entityClass);
}
