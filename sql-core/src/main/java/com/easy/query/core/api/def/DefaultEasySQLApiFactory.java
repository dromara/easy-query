//package com.easy.query.core.api.def;
//
//import com.easy.query.core.api.SQLApiFactory;
//import com.easy.query.core.basic.api.delete.EntityDeletable;
//import com.easy.query.core.basic.api.delete.ExpressionDeletable;
//import com.easy.query.core.basic.api.delete.impl.EasyEmptyEntityDeletable;
//import com.easy.query.core.basic.api.delete.impl.EasyEntityDeletable;
//import com.easy.query.core.basic.api.delete.impl.EasyExpressionDeletable;
//import com.easy.query.core.basic.api.insert.EasyEmptyInsertable;
//import com.easy.query.core.basic.api.insert.EasyInsertable;
//import com.easy.query.core.basic.api.insert.Insertable;
//import com.easy.query.core.basic.api.jdbc.EasyJdbcExecutor;
//import com.easy.query.core.basic.api.jdbc.JdbcExecutor;
//import com.easy.query.core.basic.api.select.Queryable;
//import com.easy.query.core.basic.api.select.Queryable2;
//import com.easy.query.core.basic.api.select.Queryable3;
//import com.easy.query.core.basic.api.select.Queryable4;
//import com.easy.query.core.basic.api.select.impl.EasyQueryable;
//import com.easy.query.core.basic.api.select.impl.EasyQueryable2;
//import com.easy.query.core.basic.api.select.impl.EasyQueryable3;
//import com.easy.query.core.basic.api.select.impl.EasyQueryable4;
//import com.easy.query.core.basic.api.update.EntityUpdatable;
//import com.easy.query.core.basic.api.update.ExpressionUpdatable;
//import com.easy.query.core.basic.api.update.impl.EasyEmptyEntityUpdatable;
//import com.easy.query.core.basic.api.update.impl.EasyEntityUpdatable;
//import com.easy.query.core.basic.api.update.impl.EasyExpressionUpdatable;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.enums.MultiTableTypeEnum;
//import com.easy.query.core.enums.SQLUnionEnum;
//import com.easy.query.core.exception.EasyQueryInvalidOperationException;
//import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.ExpressionContext;
//import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.util.EasyCollectionUtil;
//import com.easy.query.core.util.EasyUtil;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * @Description: 文件说明
// * @Date: 2023/3/6 08:27
// * @author xuejiaming
// */
//public class DefaultEasySQLApiFactory implements SQLApiFactory {
//    private final ExpressionBuilderFactory expressionBuilderFactory;
//
//    public DefaultEasySQLApiFactory(ExpressionBuilderFactory expressionBuilderFactory){
//        this.expressionBuilderFactory = expressionBuilderFactory;
//    }
//
//    @Override
//    public JdbcExecutor createJdbcExecutor(QueryRuntimeContext runtimeContext) {
//        return new EasyJdbcExecutor(runtimeContext);
//    }
//
//    @Override
//    public <T> Queryable<T> createQueryable(Class<T> clazz, QueryRuntimeContext runtimeContext) {
//        ExpressionContext queryExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityQueryExpressionBuilder entityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
//        EntityMetadata entityMetadata =runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
//        int tableIndex = EasyUtil.getNextTableIndex(entityQueryExpressionBuilder);;
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata,  tableIndex, MultiTableTypeEnum.FROM,runtimeContext);
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//        return new EasyQueryable<>(clazz,entityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T> Queryable<T> createQueryable(String sql, Class<T> clazz, QueryRuntimeContext runtimeContext) {
//
//        ExpressionContext queryExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityQueryExpressionBuilder innerSQLEntityQueryExpressionBuilder = expressionBuilderFactory.createAnonymousQueryExpressionBuilder(sql,queryExpressionContext);
//        EntityMetadata entityMetadata =runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
//        int tableIndex = EasyUtil.getNextTableIndex(innerSQLEntityQueryExpressionBuilder);
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata,  tableIndex, MultiTableTypeEnum.FROM,innerSQLEntityQueryExpressionBuilder);
////        //todo
////       innerSqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);
//
//
//        EntityQueryExpressionBuilder entityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//        return new EasyQueryable<>(clazz,entityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T> Queryable<T> cloneQueryable(Queryable<T> source) {
//        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = source.getSQLEntityExpressionBuilder();
//        EntityQueryExpressionBuilder entityQueryExpressionBuilder = sqlEntityExpressionBuilder.cloneEntityExpressionBuilder();
//        return new EasyQueryable<>(source.queryClass(),entityQueryExpressionBuilder);
//    }
//
//    /**
//     * 判断单表不需要匿名多表才需要
//     * @param clazz
//     * @param entityQueryExpressionBuilder
//     * @return
//     * @param <T>
//     */
//    @Override
//    public <T> Queryable<T> createQueryable(Class<T> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
//        EntityQueryExpressionBuilder queryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
//        EntityMetadata entityMetadata = queryExpressionContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
//        EntityTableExpressionBuilder anonymousTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata, 0, MultiTableTypeEnum.FROM, entityQueryExpressionBuilder);
//        queryExpressionBuilder.addSQLEntityTableExpression(anonymousTable);
//        return new EasyQueryable<>(clazz,queryExpressionBuilder);
//    }
//
//    @Override
//    public <T> Queryable<T> createUnionQueryable(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLUnionEnum sqlUnion, Collection<Queryable<T>> unionQueries) {
//        if(EasyCollectionUtil.isEmpty(unionQueries)){
//            throw new EasyQueryInvalidOperationException("cant create queryable with queryable union or union all");
//        }
//        List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders = new ArrayList<>(unionQueries.size());
//        Iterator<Queryable<T>> queryableIterator = unionQueries.iterator();
//        Queryable<T> firstQueryable = queryableIterator.next();
//        Class<T> queryClass = firstQueryable.queryClass();
//        QueryRuntimeContext runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
//        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(queryClass);
//        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
//
//
//        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = firstQueryable.getSQLEntityExpressionBuilder();
//        entityQueryExpressionBuilders.add(sqlEntityExpressionBuilder);
//        while (queryableIterator.hasNext()){
//            Queryable<T> unionQuery = queryableIterator.next();
//            EntityQueryExpressionBuilder unionSQLEntityExpressionBuilder = unionQuery.getSQLEntityExpressionBuilder();
//            entityQueryExpressionBuilders.add(unionSQLEntityExpressionBuilder);
//        }
//        EntityQueryExpressionBuilder innerSQLEntityQueryExpressionBuilder = expressionBuilderFactory.createAnonymousUnionQueryExpressionBuilder(entityQueryExpressionBuilders,queryExpressionContext,sqlUnion);
//
//
//        int tableIndex = EasyUtil.getNextTableIndex(innerSQLEntityQueryExpressionBuilder);
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata,  tableIndex, MultiTableTypeEnum.FROM,innerSQLEntityQueryExpressionBuilder);
////        //todo
////       innerSqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);
//
//
//        EntityQueryExpressionBuilder sqlEntityQueryExpressionBuilder = expressionBuilderFactory.createEntityQueryExpressionBuilder(queryExpressionContext);
//        sqlEntityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//        return new EasyQueryable<>(queryClass,sqlEntityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//
//        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);
//
//        int tableIndex =  EasyUtil.getNextTableIndex(entityQueryExpressionBuilder);
//        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata,  tableIndex, selectTableInfoType,queryExpressionContext.getRuntimeContext());
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//
//        return new EasyQueryable2<>(t1Class,t2Class,entityQueryExpressionBuilder);
//    }
//
//    /**
//     * 只有当join外部的queryable的时候外部的queryable是独立的sql expression context
//     * @param t1Class
//     * @param joinQueryable
//     * @param selectTableInfoType
//     * @param entityQueryExpressionBuilder
//     * @return
//     * @param <T1> 左表对象
//     * @param <T2> 右表对象
//     */
//    @Override
//    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Queryable<T2> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//
//        Class<T2> t2Class = joinQueryable.queryClass();
//
//        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);
//        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
//
//        int tableIndex =  EasyUtil.getNextTableIndex(entityQueryExpressionBuilder);
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata,  tableIndex, selectTableInfoType,joinQueryableSQLEntityExpressionBuilder);
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//
//        return new EasyQueryable2<>(t1Class,t2Class, entityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T1, T2, T3> Queryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//
//        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t3Class);
//        int tableIndex =  EasyUtil.getNextTableIndex(entityQueryExpressionBuilder);
//        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata,  tableIndex, selectTableInfoType,queryExpressionContext.getRuntimeContext());
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//
//        return new EasyQueryable3<>(t1Class,t2Class,t3Class, entityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T1, T2, T3> Queryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Queryable<T3> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//
//        Class<T3> t3Class = joinQueryable.queryClass();
//
//        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t3Class);
//        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
//
//        int tableIndex =  EasyUtil.getNextTableIndex(entityQueryExpressionBuilder);
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata,  tableIndex, selectTableInfoType,joinQueryableSQLEntityExpressionBuilder);
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//
//        return new EasyQueryable3<>(t1Class,t2Class,t3Class, entityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T1, T2, T3, T4> Queryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//
//        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
//        int tableIndex =  EasyUtil.getNextTableIndex(entityQueryExpressionBuilder);
//        ExpressionContext queryExpressionContext = entityQueryExpressionBuilder.getExpressionContext();
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createEntityTableExpressionBuilder(entityMetadata,  tableIndex, selectTableInfoType,queryExpressionContext.getRuntimeContext());
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//
//        return new EasyQueryable4<>(t1Class,t2Class,t3Class,t4Class, entityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T1, T2, T3, T4> Queryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Queryable<T4> joinQueryable, MultiTableTypeEnum selectTableInfoType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
//
//        Class<T4> t4Class = joinQueryable.queryClass();
//
//        EntityMetadata entityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
//        EntityQueryExpressionBuilder joinQueryableSQLEntityExpressionBuilder = joinQueryable.getSQLEntityExpressionBuilder();
//
//        int tableIndex =  EasyUtil.getNextTableIndex(entityQueryExpressionBuilder);
//        EntityTableExpressionBuilder sqlTable = expressionBuilderFactory.createAnonymousEntityTableExpressionBuilder(entityMetadata,  tableIndex, selectTableInfoType,joinQueryableSQLEntityExpressionBuilder);
//        entityQueryExpressionBuilder.addSQLEntityTableExpression(sqlTable);
//
//        return new EasyQueryable4<>(t1Class,t2Class,t3Class,t4Class, entityQueryExpressionBuilder);
//    }
//
//    @Override
//    public <T> Insertable<T> createInsertable(Class<T> clazz, QueryRuntimeContext runtimeContext) {
//        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createEntityInsertExpressionBuilder(expressionContext);
//        return createInsertable(clazz,entityInsertExpressionBuilder);
//    }
//
//    @Override
//    public <T> Insertable<T> createEmptyInsertable(QueryRuntimeContext runtimeContext) {
//        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityInsertExpressionBuilder entityInsertExpressionBuilder = expressionBuilderFactory.createEntityInsertExpressionBuilder(expressionContext);
//        return new EasyEmptyInsertable<>(entityInsertExpressionBuilder);
//    }
//
//    @Override
//    public <T> Insertable<T> createInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression) {
//        return new EasyInsertable<>(clazz, entityInsertExpression);
//    }
//
//    @Override
//    public <T> EntityUpdatable<T> createEmptyEntityUpdatable() {
//        return new EasyEmptyEntityUpdatable<>();
//    }
//
//    @Override
//    public <T> EntityUpdatable<T> createEntityUpdatable(T entity, QueryRuntimeContext runtimeContext) {
//        return createEntityUpdatable(Collections.singletonList(entity),runtimeContext);
//    }
//    @Override
//    public <T> EntityUpdatable<T> createEntityUpdatable(Collection<T> entities, QueryRuntimeContext runtimeContext) {
//        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityUpdateExpressionBuilder entityUpdateExpression = expressionBuilderFactory.createEntityUpdateExpressionBuilder(sqlExpressionContext,false);
//        return new EasyEntityUpdatable<>(entities,entityUpdateExpression);
//    }
//
//
//    @Override
//    public <T> ExpressionUpdatable<T> createExpressionUpdatable(Class<T> entityClass, QueryRuntimeContext runtimeContext) {
//        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityUpdateExpressionBuilder entityUpdateExpressionBuilder = expressionBuilderFactory.createEntityUpdateExpressionBuilder(sqlExpressionContext,true);
//        return new EasyExpressionUpdatable<>(entityClass,entityUpdateExpressionBuilder);
//    }
//
//    @Override
//    public <T> EntityDeletable<T> createEmptyEntityDeletable() {
//        return new EasyEmptyEntityDeletable<>();
//    }
//
//    @Override
//    public <T> EntityDeletable<T> createEntityDeletable(T entity, QueryRuntimeContext runtimeContext) {
//        return createEntityDeletable(Collections.singletonList(entity),runtimeContext);
//    }
//
//    @Override
//    public <T> EntityDeletable<T> createEntityDeletable(Collection<T> entities, QueryRuntimeContext runtimeContext) {
//        ExpressionContext sqlExpressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityDeleteExpressionBuilder entityDeleteExpressionBuilder = expressionBuilderFactory.createEntityDeleteExpressionBuilder(sqlExpressionContext,false);
//        return new EasyEntityDeletable<>(entities,entityDeleteExpressionBuilder);
//    }
//
//    @Override
//    public <T> ExpressionDeletable<T> createExpressionDeletable(Class<T> entityClass, QueryRuntimeContext runtimeContext) {
//        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext);
//        EntityDeleteExpressionBuilder entityDeleteExpressionBuilder = expressionBuilderFactory.createEntityDeleteExpressionBuilder(expressionContext,true);
//        return new EasyExpressionDeletable<>(entityClass,entityDeleteExpressionBuilder);
//    }
//}
