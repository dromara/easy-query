package com.easy.query.core.api.def;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.delete.impl.EasyClientEntityDeletable;
import com.easy.query.core.basic.api.delete.impl.EasyClientExpressionDeletable;
import com.easy.query.core.basic.api.delete.impl.EasyEmptyClientEntityDeletable;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.api.insert.EasyClientInsertable;
import com.easy.query.core.basic.api.insert.EasyEmptyClientInsertable;
import com.easy.query.core.basic.api.insert.map.EasyEmptyMapClientInsertable;
import com.easy.query.core.basic.api.insert.map.EasyMapClientInsertable;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.jdbc.EasyJdbcExecutor;
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
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable10;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable2;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable3;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable4;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable5;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable6;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable7;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable8;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable9;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyClientEntityUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyEmptyClientEntityUpdatable;
import com.easy.query.core.basic.api.update.map.EasyEmptyMapClientUpdatable;
import com.easy.query.core.basic.api.update.map.EasyMapClientUpdatable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.MapUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/6 08:27
 */
public class DefaultSQLClientApiFactory implements SQLClientApiFactory {
    private final ExpressionBuilderFactory expressionBuilderFactory;

    public DefaultSQLClientApiFactory(ExpressionBuilderFactory expressionBuilderFactory) {
        this.expressionBuilderFactory = expressionBuilderFactory;
    }

    @Override
    public JdbcExecutor createJdbcExecutor(QueryRuntimeContext runtimeContext) {
        return new EasyJdbcExecutor(runtimeContext);
    }

    @Override
    public <T> ClientQueryable<T> createQueryable(Class<T> clazz, QueryRuntimeContext runtimeContext) {
        ExpressionContext queryExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext, clazz);
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.FROM, runtimeContext);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable<>(clazz, entityQueryExpressionBuilder);
    }

    @Override
    public <T> ClientQueryable<T> createQueryable(String sql,Collection<Object> sqlParams, Class<T> clazz,  QueryRuntimeContext runtimeContext) {


        ExpressionContext queryExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityQueryExpressionBuilder innerSQLEntityQueryExpressionBuilder = expressionBuilderFactory.createAnonymousQueryExpressionBuilder(sql,sqlParams, queryExpressionContext, clazz);
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.FROM, innerSQLEntityQueryExpressionBuilder);
//        //todo
//       innerSqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);


        EntityQueryExpressionBuilder entityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext, clazz);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable<>(clazz, entityQueryExpressionBuilder);
    }

    private <T> EntityQueryExpressionBuilder cloneEntityExpressionBuilder(ClientQueryable<T> source) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = source.getSQLEntityExpressionBuilder();
        return sqlEntityExpressionBuilder.cloneEntityExpressionBuilder();
    }

    @Override
    public <T> ClientQueryable<T> cloneQueryable(ClientQueryable<T> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable<>(source.queryClass(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2> ClientQueryable2<T1, T2> cloneQueryable(ClientQueryable2<T1, T2> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable2<>(source.queryClass(), source.queryClass2(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3> ClientQueryable3<T1, T2, T3> cloneQueryable(ClientQueryable3<T1, T2, T3> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable3<>(source.queryClass(), source.queryClass2(), source.queryClass3(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> cloneQueryable(ClientQueryable4<T1, T2, T3, T4> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable4<>(source.queryClass(), source.queryClass2(), source.queryClass3(), source.queryClass4(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> cloneQueryable(ClientQueryable5<T1, T2, T3, T4, T5> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable5<>(source.queryClass(), source.queryClass2(), source.queryClass3(), source.queryClass4(), source.queryClass5(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> cloneQueryable(ClientQueryable6<T1, T2, T3, T4, T5, T6> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable6<>(source.queryClass(), source.queryClass2(), source.queryClass3(), source.queryClass4(), source.queryClass5(), source.queryClass6(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> cloneQueryable(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable7<>(source.queryClass(), source.queryClass2(), source.queryClass3(), source.queryClass4(), source.queryClass5(), source.queryClass6(), source.queryClass7(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> cloneQueryable(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable8<>(source.queryClass(), source.queryClass2(), source.queryClass3(), source.queryClass4(), source.queryClass5(), source.queryClass6(), source.queryClass7(), source.queryClass8(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cloneQueryable(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable9<>(source.queryClass(), source.queryClass2(), source.queryClass3(), source.queryClass4(), source.queryClass5(), source.queryClass6(), source.queryClass7(), source.queryClass8(), source.queryClass9(), entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> cloneQueryable(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> source) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = cloneEntityExpressionBuilder(source);
        return new EasyClientQueryable10<>(source.queryClass(), source.queryClass2(), source.queryClass3(), source.queryClass4(), source.queryClass5(), source.queryClass6(), source.queryClass7(), source.queryClass8(), source.queryClass9(), source.queryClass10(), entityQueryExpressionBuilder);
    }


    /**
     * 判断单表不需要匿名多表才需要
     *
     * @param clazz
     * @param entityQueryExpressionBuilder
     * @param <T>
     * @return
     */
    @Override
    public <T> ClientQueryable<T> createQueryable(Class<T> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityQueryExpressionBuilder queryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext, clazz);
        EntityMetadata entityMetadata = queryExpressionContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        EntityTableExpressionBuilder anonymousTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.FROM, entityQueryExpressionBuilder);
        queryExpressionBuilder.addSQLEntityTableExpression(anonymousTable);
        return new EasyClientQueryable<>(clazz, queryExpressionBuilder);
    }

    @Override
    public <T> ClientQueryable<T> createUnionQueryable(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLUnionEnum sqlUnion, Collection<ClientQueryable<T>> unionQueries) {
        if (EasyCollectionUtil.isEmpty(unionQueries)) {
            throw new EasyQueryInvalidOperationException("cant create queryable with queryable union or union all");
        }
        List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders = new ArrayList<>(unionQueries.size());
        Iterator<ClientQueryable<T>> queryableIterator = unionQueries.iterator();
        ClientQueryable<T> firstQueryable = queryableIterator.next();
        Class<T> queryClass = firstQueryable.queryClass();
        QueryRuntimeContext runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(queryClass);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();


        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = firstQueryable.getSQLEntityExpressionBuilder();
        entityQueryExpressionBuilders.add(sqlEntityExpressionBuilder);
        while (queryableIterator.hasNext()) {
            ClientQueryable<T> unionQuery = queryableIterator.next();
            EntityQueryExpressionBuilder unionSQLEntityExpressionBuilder = unionQuery.getSQLEntityExpressionBuilder();
            entityQueryExpressionBuilders.add(unionSQLEntityExpressionBuilder);
        }
        EntityQueryExpressionBuilder innerSQLEntityQueryExpressionBuilder = expressionBuilderFactory.createAnonymousUnionQueryExpressionBuilder(entityQueryExpressionBuilders, queryExpressionContext, queryClass, sqlUnion);


        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.FROM, innerSQLEntityQueryExpressionBuilder);
//        //todo
//       innerSqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);


        EntityQueryExpressionBuilder sqlEntityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext, queryClass);
        sqlEntityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable<>(queryClass, sqlEntityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2> ClientQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);

        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable2<>(t1Class, t2Class, entityQueryExpressionBuilder);
    }

    /**
     * 只有当join外部的queryable的时候外部的queryable是独立的sql expression context
     *
     * @param t1Class
     * @param joinQueryable
     * @param selectTableInfoType
     * @param entityQueryExpressionBuilder
     * @param <T1>                         左表对象
     * @param <T2>                         右表对象
     * @return
     */
    @Override
    public <T1, T2> ClientQueryable2<T1, T2> createQueryable2(Class<T1> t1Class, ClientQueryable<T2> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        Class<T2> t2Class = joinQueryable.queryClass();

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();

        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable2<>(t1Class, t2Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3> ClientQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t3Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable3<>(t1Class, t2Class, t3Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3> ClientQueryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, ClientQueryable<T3> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        Class<T3> t3Class = joinQueryable.queryClass();

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t3Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();

        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable3<>(t1Class, t2Class, t3Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable4<>(t1Class, t2Class, t3Class, t4Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, ClientQueryable<T4> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        Class<T4> t4Class = joinQueryable.queryClass();

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();

        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable4<>(t1Class, t2Class, t3Class, t4Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> createQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable5<>(t1Class, t2Class, t3Class, t4Class, t5Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5> ClientQueryable5<T1, T2, T3, T4, T5> createQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, ClientQueryable<T5> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        Class<T5> t5Class = joinQueryable.queryClass();

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();

        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable5<>(t1Class, t2Class, t3Class, t4Class, t5Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> createQueryable6(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable6<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> createQueryable6(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, ClientQueryable<T6> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        Class<T6> t6Class = joinQueryable.queryClass();
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable6<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, entityQueryExpressionBuilder);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> createQueryable7(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable7<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> createQueryable7(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, ClientQueryable<T7> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        Class<T7> t7Class = joinQueryable.queryClass();
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable7<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> createQueryable8(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable8<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, t8Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> createQueryable8(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, ClientQueryable<T8> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        Class<T8> t8Class = joinQueryable.queryClass();
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable8<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, t8Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> createQueryable9(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable9<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, t8Class, t9Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> createQueryable9(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, ClientQueryable<T9> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        Class<T9> t9Class = joinQueryable.queryClass();
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable9<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, t8Class, t9Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> createQueryable10(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, Class<T10> t10Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, queryExpressionContext.getRuntimeContext());
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable10<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, t8Class, t9Class, t10Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> createQueryable10(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, ClientQueryable<T10> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        Class<T10> t10Class = joinQueryable.queryClass();
        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, selectTableInfoType, joinQueryableSQLEntityExpressionBuilder);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);

        return new EasyClientQueryable10<>(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, t8Class, t9Class, t10Class, entityQueryExpressionBuilder);

    }

    @Override
    public <T> ClientInsertable<T> createInsertable(Class<T> clazz, QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createEntityInsertExpressionBuilder(expressionContext, clazz);
        return createInsertable(clazz, entityInsertExpressionBuilder);
    }

    @Override
    public MapClientInsertable<Map<String, Object>> createMapInsertable(QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createMapInsertExpressionBuilder(expressionContext);
        return createMapInsertable(entityInsertExpressionBuilder);
    }

    @Override
    public <T> ClientInsertable<T> createEmptyInsertable(QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createEntityInsertExpressionBuilder(expressionContext, null);
        return new EasyEmptyClientInsertable<>(entityInsertExpressionBuilder);
    }

    @Override
    public <T> ClientInsertable<T> createInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression) {
        return new EasyClientInsertable<>(clazz, entityInsertExpression);
    }

    @Override
    public MapClientInsertable<Map<String, Object>> createMapInsertable(EntityInsertExpressionBuilder entityInsertExpressionBuilder) {
        return new EasyMapClientInsertable(entityInsertExpressionBuilder);
    }

    @Override
    public MapClientInsertable<Map<String, Object>> createEmptyMapInsertable(QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createEntityInsertExpressionBuilder(expressionContext, null);
        return new EasyEmptyMapClientInsertable(entityInsertExpressionBuilder);
    }

    @Override
    public <T> ClientEntityUpdatable<T> createEmptyEntityUpdatable() {
        return new EasyEmptyClientEntityUpdatable<>();
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> createEmptyMapUpdatable() {
        return new EasyEmptyMapClientUpdatable();
    }

    @Override
    public <T> ClientEntityUpdatable<T> createEntityUpdatable(T entity, QueryRuntimeContext runtimeContext) {
        return createEntityUpdatable(Collections.singletonList(entity), runtimeContext);
    }

    @Override
    public <T> ClientEntityUpdatable<T> createEntityUpdatable(Collection<T> entities, QueryRuntimeContext runtimeContext) {
        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        Class<T> clazz = EasyObjectUtil.typeCastNullable(EasyCollectionUtil.first(entities).getClass());
        EntityUpdateExpressionBuilder entityUpdateExpression = expressionBuilderFactory.createEntityUpdateExpressionBuilder(sqlExpressionContext, clazz, false);
        return new EasyClientEntityUpdatable<>(clazz, entities, entityUpdateExpression);
    }

    @Override
    public <T> MapClientUpdatable<Map<String,Object>> createMapUpdatable(Map<String, Object> entity, QueryRuntimeContext runtimeContext) {
        return createMapUpdatable(Collections.singletonList(entity),runtimeContext);
    }

    @Override
    public <T> MapClientUpdatable<Map<String,Object>> createMapUpdatable(Collection<Map<String, Object>> entities, QueryRuntimeContext runtimeContext) {
        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        MapUpdateExpressionBuilder entityUpdateExpression = expressionBuilderFactory.createMapUpdateExpressionBuilder(sqlExpressionContext);
        return new EasyMapClientUpdatable(entities, entityUpdateExpression);
    }

    @Override
    public <T> ClientExpressionUpdatable<T> createExpressionUpdatable(Class<T> entityClass, QueryRuntimeContext runtimeContext) {
        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityUpdateExpressionBuilder entityUpdateExpressionBuilder = expressionBuilderFactory.createEntityUpdateExpressionBuilder(sqlExpressionContext, entityClass, true);
        return new EasyClientExpressionUpdatable<>(entityClass, entityUpdateExpressionBuilder);
    }

    @Override
    public <T> ClientEntityDeletable<T> createEmptyEntityDeletable() {
        return new EasyEmptyClientEntityDeletable<>();
    }

    @Override
    public <T> ClientEntityDeletable<T> createEntityDeletable(T entity, QueryRuntimeContext runtimeContext) {
        return createEntityDeletable(Collections.singletonList(entity), runtimeContext);
    }

    @Override
    public <T> ClientEntityDeletable<T> createEntityDeletable(Collection<T> entities, QueryRuntimeContext runtimeContext) {
        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        Class<T> clazz = EasyObjectUtil.typeCastNullable(EasyCollectionUtil.first(entities).getClass());
        EntityDeleteExpressionBuilder entityDeleteExpressionBuilder = expressionBuilderFactory.createEntityDeleteExpressionBuilder(sqlExpressionContext, clazz, false);
        return new EasyClientEntityDeletable<>(clazz, entities, entityDeleteExpressionBuilder);
    }

    @Override
    public <T> ClientExpressionDeletable<T> createExpressionDeletable(Class<T> entityClass, QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityDeleteExpressionBuilder entityDeleteExpressionBuilder = expressionBuilderFactory.createEntityDeleteExpressionBuilder(expressionContext, entityClass, true);
        return new EasyClientExpressionDeletable<>(entityClass, entityDeleteExpressionBuilder);
    }
}
