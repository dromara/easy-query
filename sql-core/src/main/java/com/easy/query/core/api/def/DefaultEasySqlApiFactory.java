package com.easy.query.core.api.def;

import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.basic.api.delete.EntityDeletable;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.basic.api.jdbc.DefaultEasyJDBCExecutor;
import com.easy.query.core.basic.api.jdbc.EasyJDBCExecutor;
import com.easy.query.core.basic.api.select.impl.EasyQueryable;
import com.easy.query.core.basic.api.select.impl.EasyQueryable3;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasySQLExpressionFactory;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.query.*;
import com.easy.query.core.basic.api.delete.impl.EasyEmptyEntityDeletable;
import com.easy.query.core.basic.api.delete.impl.EasyEntityDeletable;
import com.easy.query.core.basic.api.delete.impl.EasyExpressionDeletable;
import com.easy.query.core.basic.api.insert.EasyEmptyInsertable;
import com.easy.query.core.basic.api.insert.EasyInsertable;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.basic.api.select.Queryable3;
import com.easy.query.core.basic.api.select.Queryable4;
import com.easy.query.core.basic.api.select.impl.EasyQueryable2;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.select.Queryable2;
import com.easy.query.core.basic.api.select.impl.EasyQueryable4;
import com.easy.query.core.basic.api.update.impl.EasyEmptyEntityUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyEntityUpdatable;
import com.easy.query.core.basic.api.update.impl.EasyExpressionUpdatable;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.util.EasyUtil;

import java.util.Collection;
import java.util.Collections;

/**
 * @FileName: DefaultEasySqlApiFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:27
 * @Created by xuejiaming
 */
public class DefaultEasySqlApiFactory implements EasySqlApiFactory {
    private final EasySQLExpressionFactory easySqlExpressionFactory;

    public DefaultEasySqlApiFactory(EasySQLExpressionFactory easySqlExpressionFactory){
        this.easySqlExpressionFactory = easySqlExpressionFactory;
    }

    @Override
    public EasyJDBCExecutor createJDBCExecutor(EasyQueryRuntimeContext runtimeContext) {
        return new DefaultEasyJDBCExecutor(runtimeContext);
    }

    @Override
    public <T> Queryable<T> createQueryable(Class<T> clazz, EasyQueryRuntimeContext runtimeContext,String alias) {
        SqlExpressionContext queryExpressionContext =easySqlExpressionFactory.createSqlExpressionContext(runtimeContext,alias);
        SqlEntityQueryExpression sqlEntityQueryExpression = easySqlExpressionFactory.createSqlEntityQueryExpression(queryExpressionContext);
        EntityMetadata entityMetadata =runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        int tableIndex = EasyUtil.getNextTableIndex(sqlEntityQueryExpression);;
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), MultiTableTypeEnum.FROM);
        sqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);
        return new EasyQueryable<>(clazz,sqlEntityQueryExpression);
    }

    @Override
    public <T> Queryable<T> cloneQueryable(Queryable<T> source) {
        SqlEntityQueryExpression sqlEntityExpression = source.getSqlEntityExpression();
        SqlEntityQueryExpression sqlEntityQueryExpression = sqlEntityExpression.cloneSqlQueryExpression();
        return new EasyQueryable<>(source.queryClass(),sqlEntityQueryExpression);
    }

    @Override
    public <T> Queryable<T> createQueryable(Class<T> clazz, SqlEntityQueryExpression sqlEntityExpression) {
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityQueryExpression sqlEntityQueryExpression = easySqlExpressionFactory.createSqlEntityQueryExpression(queryExpressionContext);
        EntityMetadata entityMetadata = queryExpressionContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        SqlEntityTableExpression anonymousTable = easySqlExpressionFactory.createSqlAnonymousEntityTableExpression(entityMetadata, 0, queryExpressionContext.createTableAlias(), MultiTableTypeEnum.FROM, sqlEntityExpression);
        sqlEntityQueryExpression.addSqlEntityTableExpression(anonymousTable);
        return new EasyQueryable<>(clazz,sqlEntityQueryExpression);
    }

    @Override
    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);

        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new EasyQueryable2<>(t1Class,t2Class,sqlEntityExpression);
    }

    /**
     * 只有当join外部的queryable的时候外部的queryable是独立的sql expression context
     * @param t1Class
     * @param joinQueryable
     * @param selectTableInfoType
     * @param sqlEntityExpression
     * @return
     * @param <T1> 左表对象
     * @param <T2> 右表对象
     */
    @Override
    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Queryable<T2> joinQueryable, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        Class<T2> t2Class = joinQueryable.queryClass();

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);
        SqlEntityQueryExpression joinQueryableSqlEntityExpression = joinQueryable.getSqlEntityExpression();

        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlAnonymousEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType,joinQueryableSqlEntityExpression);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new EasyQueryable2<>(t1Class,t2Class,sqlEntityExpression);
    }

    @Override
    public <T1, T2, T3> Queryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t3Class);
        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new EasyQueryable3<>(t1Class,t2Class,t3Class,sqlEntityExpression);
    }

    @Override
    public <T1, T2, T3> Queryable3<T1, T2, T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class, Queryable<T3> joinQueryable, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        Class<T3> t3Class = joinQueryable.queryClass();

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t3Class);
        SqlEntityQueryExpression joinQueryableSqlEntityExpression = joinQueryable.getSqlEntityExpression();

        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlAnonymousEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType,joinQueryableSqlEntityExpression);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new EasyQueryable3<>(t1Class,t2Class,t3Class,sqlEntityExpression);
    }

    @Override
    public <T1, T2, T3, T4> Queryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new EasyQueryable4<>(t1Class,t2Class,t3Class,t4Class,sqlEntityExpression);
    }

    @Override
    public <T1, T2, T3, T4> Queryable4<T1, T2, T3, T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Queryable<T4> joinQueryable, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        Class<T4> t4Class = joinQueryable.queryClass();

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t4Class);
        SqlEntityQueryExpression joinQueryableSqlEntityExpression = joinQueryable.getSqlEntityExpression();

        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlAnonymousEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType,joinQueryableSqlEntityExpression);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new EasyQueryable4<>(t1Class,t2Class,t3Class,t4Class,sqlEntityExpression);
    }

    @Override
    public <T> Insertable<T> createInsertable(Class<T> clazz,EasyQueryRuntimeContext runtimeContext,String alias) {
        SqlExpressionContext sqlExpressionContext = easySqlExpressionFactory.createSqlExpressionContext(runtimeContext, alias);
        SqlEntityInsertExpression sqlEntityInsertExpression = easySqlExpressionFactory.createSqlEntityInsertExpression(sqlExpressionContext);
        return createInsertable(clazz,sqlEntityInsertExpression);
    }

    @Override
    public <T> Insertable<T> createEmptyInsertable(EasyQueryRuntimeContext runtimeContext, String alias) {
        SqlExpressionContext sqlExpressionContext = easySqlExpressionFactory.createSqlExpressionContext(runtimeContext, alias);
        SqlEntityInsertExpression sqlEntityInsertExpression = easySqlExpressionFactory.createSqlEntityInsertExpression(sqlExpressionContext);
        return new EasyEmptyInsertable<>(sqlEntityInsertExpression);
    }

    @Override
    public <T> Insertable<T> createInsertable(Class<T> clazz, SqlEntityInsertExpression sqlEntityInsertExpression) {
        return new EasyInsertable<>(clazz,sqlEntityInsertExpression);
    }

    @Override
    public <T> EntityUpdatable<T> createEmptyEntityUpdatable() {
        return new EasyEmptyEntityUpdatable<>();
    }

    @Override
    public <T> EntityUpdatable<T> createEntityUpdatable(T entity, EasyQueryRuntimeContext runtimeContext, String alias) {
        return createEntityUpdatable(Collections.singletonList(entity),runtimeContext,alias);
    }
    @Override
    public <T> EntityUpdatable<T> createEntityUpdatable(Collection<T> entities, EasyQueryRuntimeContext runtimeContext, String alias) {
        SqlExpressionContext sqlExpressionContext = easySqlExpressionFactory.createSqlExpressionContext(runtimeContext, alias);
        SqlEntityUpdateExpression sqlEntityUpdateExpression = easySqlExpressionFactory.createSqlEntityUpdateExpression(sqlExpressionContext,false);
        return new EasyEntityUpdatable<>(entities,sqlEntityUpdateExpression);
    }


    @Override
    public <T> ExpressionUpdatable<T> createExpressionUpdatable(Class<T> entityClass, EasyQueryRuntimeContext runtimeContext, String alias) {
        SqlExpressionContext sqlExpressionContext = easySqlExpressionFactory.createSqlExpressionContext(runtimeContext, alias);
        SqlEntityUpdateExpression sqlEntityUpdateExpression = easySqlExpressionFactory.createSqlEntityUpdateExpression(sqlExpressionContext,true);
        return new EasyExpressionUpdatable<>(entityClass,sqlEntityUpdateExpression);
    }

    @Override
    public <T> EntityDeletable<T> createEmptyEntityDeletable() {
        return new EasyEmptyEntityDeletable<>();
    }

    @Override
    public <T> EntityDeletable<T> createEntityDeletable(T entity, EasyQueryRuntimeContext runtimeContext, String alias) {
        return createEntityDeletable(Collections.singletonList(entity),runtimeContext,alias);
    }

    @Override
    public <T> EntityDeletable<T> createEntityDeletable(Collection<T> entities, EasyQueryRuntimeContext runtimeContext, String alias) {
        SqlExpressionContext sqlExpressionContext = easySqlExpressionFactory.createSqlExpressionContext(runtimeContext, alias);
        SqlEntityDeleteExpression sqlEntityDeleteExpression = easySqlExpressionFactory.createSqlEntityDeleteExpression(sqlExpressionContext,false);
        return new EasyEntityDeletable<>(entities,sqlEntityDeleteExpression);
    }

    @Override
    public <T> ExpressionDeletable<T> createExpressionDeletable(Class<T> entityClass, EasyQueryRuntimeContext runtimeContext, String alias) {
        SqlExpressionContext sqlExpressionContext = easySqlExpressionFactory.createSqlExpressionContext(runtimeContext, alias);
        SqlEntityDeleteExpression sqlEntityDeleteExpression = easySqlExpressionFactory.createSqlEntityDeleteExpression(sqlExpressionContext,true);
        return new EasyExpressionDeletable<>(entityClass,sqlEntityDeleteExpression);
    }
}
