package com.easy.query.core.api;

import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.api.jdbc.JdbcExecutor;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

import java.util.Collection;

/**
 * create time 2023/6/1 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLClientApiFactory {

    JdbcExecutor createJdbcExecutor(QueryRuntimeContext runtimeContext);

    <T> ClientQueryable<T> createQueryable(Class<T> clazz, QueryRuntimeContext runtimeContext);

    <T> ClientQueryable<T> createQueryable(String sql, Class<T> clazz, QueryRuntimeContext runtimeContext);

    <T> ClientQueryable<T> cloneQueryable(ClientQueryable<T> source);

    <T> ClientQueryable<T> createQueryable(Class<T> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder);


    <T> ClientQueryable<T> createUnionQueryable(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLUnionEnum sqlUnion, Collection<ClientQueryable<T>> unionQueries);


    //    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType,EasyQueryRuntimeContext runtimeContext);
    <T1, T2> ClientQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2> ClientQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, ClientQueryable<T2> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3> ClientQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3> ClientQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, ClientQueryable<T3> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, ClientQueryable<T4> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4,T5> ClientQueryable5<T1, T2, T3, T4,T5> createQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class,Class<T5> t5Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4,T5> ClientQueryable5<T1, T2, T3, T4,T5> createQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, ClientQueryable<T5> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
    <T1, T2, T3, T4,T5,T6> ClientQueryable6<T1, T2, T3, T4,T5,T6> createQueryable6(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class,Class<T5> t5Class,Class<T6> t6Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4,T5,T6> ClientQueryable6<T1, T2, T3, T4,T5,T6> createQueryable6(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class,Class<T5> t5Class, ClientQueryable<T6> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);


    <T> ClientInsertable<T> createInsertable(Class<T> clazz, QueryRuntimeContext runtimeContext);

    /**
     * 创建一个空的insert接口
     *
     * @param runtimeContext
     * @param <T>
     * @return
     */
    <T> ClientInsertable<T> createEmptyInsertable(QueryRuntimeContext runtimeContext);

    <T> ClientInsertable<T> createInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression);

    <T> ClientEntityUpdatable<T> createEmptyEntityUpdatable();

    <T> ClientEntityUpdatable<T> createEntityUpdatable(T entity, QueryRuntimeContext runtimeContext);

    <T> ClientEntityUpdatable<T> createEntityUpdatable(Collection<T> entities, QueryRuntimeContext runtimeContext);

    <T> ClientExpressionUpdatable<T> createExpressionUpdatable(Class<T> entityClass, QueryRuntimeContext runtimeContext);

    <T> ClientEntityDeletable<T> createEmptyEntityDeletable();

    <T> ClientEntityDeletable<T> createEntityDeletable(T entity, QueryRuntimeContext runtimeContext);

    <T> ClientEntityDeletable<T> createEntityDeletable(Collection<T> entities, QueryRuntimeContext runtimeContext);

    <T> ClientExpressionDeletable<T> createExpressionDeletable(Class<T> entityClass, QueryRuntimeContext runtimeContext);
}
