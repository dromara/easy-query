package com.easy.query.core.api;

import com.easy.query.core.basic.api.delete.EntityObjectDeletable;
import com.easy.query.core.basic.api.delete.ExpressionObjectDeletable;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.basic.api.jdbc.JdbcExecutor;
import com.easy.query.core.basic.api.select.ObjectQueryable;
import com.easy.query.core.basic.api.select.ObjectQueryable2;
import com.easy.query.core.basic.api.select.ObjectQueryable3;
import com.easy.query.core.basic.api.select.ObjectQueryable4;
import com.easy.query.core.basic.api.update.EntityObjectUpdatable;
import com.easy.query.core.basic.api.update.ExpressionObjectUpdatable;
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
public interface SQLObjectApiFactory {

    JdbcExecutor createJdbcExecutor(QueryRuntimeContext runtimeContext);

    <T> ObjectQueryable<T> createQueryable(Class<T> clazz, QueryRuntimeContext runtimeContext);

    <T> ObjectQueryable<T> createQueryable(String sql, Class<T> clazz, QueryRuntimeContext runtimeContext);

    <T> ObjectQueryable<T> cloneQueryable(ObjectQueryable<T> source);

    <T> ObjectQueryable<T> createQueryable(Class<T> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder);


    <T> ObjectQueryable<T> createUnionQueryable(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLUnionEnum sqlUnion, Collection<ObjectQueryable<T>> unionQueries);


    //    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType,EasyQueryRuntimeContext runtimeContext);
    <T1, T2> ObjectQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2> ObjectQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, ObjectQueryable<T2> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3> ObjectQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3> ObjectQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, ObjectQueryable<T3> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4> ObjectQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4> ObjectQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, ObjectQueryable<T4> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);


    <T> Insertable<T> createInsertable(Class<T> clazz, QueryRuntimeContext runtimeContext);

    /**
     * 创建一个空的insert接口
     *
     * @param runtimeContext
     * @param <T>
     * @return
     */
    <T> Insertable<T> createEmptyInsertable(QueryRuntimeContext runtimeContext);

    <T> Insertable<T> createInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression);

    <T> EntityObjectUpdatable<T> createEmptyEntityUpdatable();

    <T> EntityObjectUpdatable<T> createEntityUpdatable(T entity, QueryRuntimeContext runtimeContext);

    <T> EntityObjectUpdatable<T> createEntityUpdatable(Collection<T> entities, QueryRuntimeContext runtimeContext);

    <T> ExpressionObjectUpdatable<T> createExpressionUpdatable(Class<T> entityClass, QueryRuntimeContext runtimeContext);

    <T> EntityObjectDeletable<T> createEmptyEntityDeletable();

    <T> EntityObjectDeletable<T> createEntityDeletable(T entity, QueryRuntimeContext runtimeContext);

    <T> EntityObjectDeletable<T> createEntityDeletable(Collection<T> entities, QueryRuntimeContext runtimeContext);

    <T> ExpressionObjectDeletable<T> createExpressionDeletable(Class<T> entityClass, QueryRuntimeContext runtimeContext);
}
