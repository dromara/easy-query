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
import com.easy.query.core.basic.api.jdbc.EasyJdbcExecutor;
import com.easy.query.core.basic.api.jdbc.JdbcExecutor;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable2;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable3;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable4;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyClientEntityUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyEmptyClientEntityUpdatable;
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
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.FROM, runtimeContext);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable<>(clazz, entityQueryExpressionBuilder);
    }

    @Override
    public <T> ClientQueryable<T> createQueryable(String sql, Class<T> clazz, QueryRuntimeContext runtimeContext) {

        ExpressionContext queryExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityQueryExpressionBuilder innerSQLEntityQueryExpressionBuilder = expressionBuilderFactory.createAnonymousQueryExpressionBuilder(sql, queryExpressionContext);
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.FROM, innerSQLEntityQueryExpressionBuilder);
//        //todo
//       innerSqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);


        EntityQueryExpressionBuilder entityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
        return new EasyClientQueryable<>(clazz, entityQueryExpressionBuilder);
    }

    @Override
    public <T> ClientQueryable<T> cloneQueryable(ClientQueryable<T> source) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = source.getSQLEntityExpressionBuilder();
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = sqlEntityExpressionBuilder.cloneEntityExpressionBuilder();
        return new EasyClientQueryable<>(source.queryClass(), entityQueryExpressionBuilder);
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
        EntityQueryExpressionBuilder queryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
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
        EntityQueryExpressionBuilder innerSQLEntityQueryExpressionBuilder = expressionBuilderFactory.createAnonymousUnionQueryExpressionBuilder(entityQueryExpressionBuilders, queryExpressionContext, sqlUnion);


        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.FROM, innerSQLEntityQueryExpressionBuilder);
//        //todo
//       innerSqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);


        EntityQueryExpressionBuilder sqlEntityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
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
    public <T> ClientInsertable<T> createInsertable(Class<T> clazz, QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createEntityInsertExpressionBuilder(expressionContext);
        return createInsertable(clazz, entityInsertExpressionBuilder);
    }

    @Override
    public <T> ClientInsertable<T> createEmptyInsertable(QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createEntityInsertExpressionBuilder(expressionContext);
        return new EasyEmptyClientInsertable<>(entityInsertExpressionBuilder);
    }

    @Override
    public <T> ClientInsertable<T> createInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression) {
        return new EasyClientInsertable<>(clazz, entityInsertExpression);
    }

    @Override
    public <T> ClientEntityUpdatable<T> createEmptyEntityUpdatable() {
        return new EasyEmptyClientEntityUpdatable<>();
    }

    @Override
    public <T> ClientEntityUpdatable<T> createEntityUpdatable(T entity, QueryRuntimeContext runtimeContext) {
        return createEntityUpdatable(Collections.singletonList(entity), runtimeContext);
    }

    @Override
    public <T> ClientEntityUpdatable<T> createEntityUpdatable(Collection<T> entities, QueryRuntimeContext runtimeContext) {
        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityUpdateExpressionBuilder entityUpdateExpression = expressionBuilderFactory.createEntityUpdateExpressionBuilder(sqlExpressionContext, false);
        return new EasyClientEntityUpdatable<>(entities, entityUpdateExpression);
    }


    @Override
    public <T> ClientExpressionUpdatable<T> createExpressionUpdatable(Class<T> entityClass, QueryRuntimeContext runtimeContext) {
        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityUpdateExpressionBuilder entityUpdateExpressionBuilder = expressionBuilderFactory.createEntityUpdateExpressionBuilder(sqlExpressionContext, true);
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
        EntityDeleteExpressionBuilder entityDeleteExpressionBuilder = expressionBuilderFactory.createEntityDeleteExpressionBuilder(sqlExpressionContext, false);
        return new EasyClientEntityDeletable<>(entities, entityDeleteExpressionBuilder);
    }

    @Override
    public <T> ClientExpressionDeletable<T> createExpressionDeletable(Class<T> entityClass, QueryRuntimeContext runtimeContext) {
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
        EntityDeleteExpressionBuilder entityDeleteExpressionBuilder = expressionBuilderFactory.createEntityDeleteExpressionBuilder(expressionContext, true);
        return new EasyClientExpressionDeletable<>(entityClass, entityDeleteExpressionBuilder);
    }
}
