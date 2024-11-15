package com.easy.query.core.api;

import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.jdbc.JdbcExecutor;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * create time 2023/6/1 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLClientApiFactory {

    JdbcExecutor createJdbcExecutor(QueryRuntimeContext runtimeContext);

    <T> ClientQueryable<T> createQueryable(Class<T> clazz, QueryRuntimeContext runtimeContext);
    <T> ClientQueryable<T> createQueryable(Class<T> clazz, QueryRuntimeContext runtimeContext, ExpressionContext expressionContext);

    default <T> ClientQueryable<T> createQueryable(String sql, Class<T> clazz, QueryRuntimeContext runtimeContext) {
        return createQueryable(sql, Collections.emptyList(), clazz, runtimeContext);
    }

    <T> ClientQueryable<T> createQueryable(String sql, Collection<Object> sqlParams, Class<T> clazz, QueryRuntimeContext runtimeContext);

    <T> ClientQueryable<T> cloneQueryable(ClientQueryable<T> source);

    <T1, T2> ClientQueryable2<T1, T2> cloneQueryable(ClientQueryable2<T1, T2> source);

    <T1, T2, T3> ClientQueryable3<T1, T2, T3> cloneQueryable(ClientQueryable3<T1, T2, T3> source);

    <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> cloneQueryable(ClientQueryable4<T1, T2, T3, T4> source);

    <T1, T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> cloneQueryable(ClientQueryable5<T1, T2, T3, T4, T5> source);

    <T1, T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> cloneQueryable(ClientQueryable6<T1, T2, T3, T4, T5, T6> source);

    <T1, T2, T3, T4, T5, T6, T7> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> cloneQueryable(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> source);

    <T1, T2, T3, T4, T5, T6, T7, T8> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> cloneQueryable(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> source);

    <T1, T2, T3, T4, T5, T6, T7, T8, T9> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cloneQueryable(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> source);

    <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> cloneQueryable(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> source);

    <T> ClientQueryable<T> createQueryable(Class<T> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
    <T> ClientQueryable<T> createQueryable(Class<T> clazz, EntityMetadata entityMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder);


    <T> ClientQueryable<T> createUnionQueryable(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLUnionEnum sqlUnion, Collection<ClientQueryable<T>> unionQueries);


    //    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType,EasyQueryRuntimeContext runtimeContext);
    <T1, T2> ClientQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2> ClientQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, ClientQueryable<T2> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3> ClientQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3> ClientQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, ClientQueryable<T3> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, ClientQueryable<T4> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> createQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> createQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, ClientQueryable<T5> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> createQueryable6(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> createQueryable6(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, ClientQueryable<T6> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> createQueryable7(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> createQueryable7(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, ClientQueryable<T7> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7, T8> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> createQueryable8(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7, T8> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> createQueryable8(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, ClientQueryable<T8> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7, T8, T9> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> createQueryable9(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7, T8, T9> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> createQueryable9(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, ClientQueryable<T9> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> createQueryable10(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, Class<T10> t10Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> createQueryable10(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, ClientQueryable<T10> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <T> ClientInsertable<T> createInsertable(Class<T> clazz, QueryRuntimeContext runtimeContext);
    MapClientInsertable<Map<String,Object>> createMapInsertable(QueryRuntimeContext runtimeContext);

    /**
     * 创建一个空的insert接口
     *
     * @param runtimeContext
     * @param <T>
     * @return
     */
    <T> ClientInsertable<T> createEmptyInsertable(QueryRuntimeContext runtimeContext);

    <T> ClientInsertable<T> createInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpressionBuilder);
    MapClientInsertable<Map<String,Object>> createMapInsertable(EntityInsertExpressionBuilder entityInsertExpressionBuilder);
    MapClientInsertable<Map<String,Object>> createEmptyMapInsertable(QueryRuntimeContext runtimeContext);

    <T> ClientEntityUpdatable<T> createEmptyEntityUpdatable();
    MapClientUpdatable<Map<String,Object>> createEmptyMapUpdatable();

    <T> ClientEntityUpdatable<T> createEntityUpdatable(T entity, QueryRuntimeContext runtimeContext);

    <T> ClientEntityUpdatable<T> createEntityUpdatable(Collection<T> entities, QueryRuntimeContext runtimeContext);
    <T> MapClientUpdatable<Map<String,Object>> createMapUpdatable(Map<String,Object> entity, QueryRuntimeContext runtimeContext);

    <T> MapClientUpdatable<Map<String,Object>> createMapUpdatable(Collection<Map<String,Object>> entities, QueryRuntimeContext runtimeContext);

    <T> ClientExpressionUpdatable<T> createExpressionUpdatable(Class<T> entityClass, QueryRuntimeContext runtimeContext);

    <T> ClientEntityDeletable<T> createEmptyEntityDeletable();

    <T> ClientEntityDeletable<T> createEntityDeletable(T entity, QueryRuntimeContext runtimeContext);

    <T> ClientEntityDeletable<T> createEntityDeletable(Collection<T> entities, QueryRuntimeContext runtimeContext);

    <T> ClientExpressionDeletable<T> createExpressionDeletable(Class<T> entityClass, QueryRuntimeContext runtimeContext);
}
