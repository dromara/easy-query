package org.easy.query.core.basic.api.def;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.EasySqlApiFactory;
import org.easy.query.core.abstraction.EasySqlExpressionFactory;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.api.insert.EasyEmptyInsertable;
import org.easy.query.core.basic.api.insert.EasyInsertable;
import org.easy.query.core.basic.api.insert.Insertable;
import org.easy.query.core.basic.api.select.EasyQueryable;
import org.easy.query.core.basic.api.select.EasyQueryable2;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.query.*;
import org.easy.query.core.util.EasyUtil;

/**
 * @FileName: DefaultEasySqlApiFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:27
 * @Created by xuejiaming
 */
public class DefaultEasySqlApiFactory implements EasySqlApiFactory {
    private final EasySqlExpressionFactory easySqlExpressionFactory;

    public DefaultEasySqlApiFactory(EasySqlExpressionFactory easySqlExpressionFactory){
        this.easySqlExpressionFactory = easySqlExpressionFactory;
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
        SqlEntityQueryExpression mySQLEntityExpression = easySqlExpressionFactory.createSqlEntityQueryExpression(queryExpressionContext);
        EntityMetadata entityMetadata = queryExpressionContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        SqlEntityTableExpression anonymousTable = easySqlExpressionFactory.createEasyAnonymousEntityTableExpression(entityMetadata, 0, queryExpressionContext.createTableAlias(), MultiTableTypeEnum.FROM, sqlEntityExpression);
        mySQLEntityExpression.addSqlEntityTableExpression(anonymousTable);
        return new EasyQueryable<>(clazz,mySQLEntityExpression);
    }

    @Override
    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);

        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new EasyQueryable2<>(t1Class,t2Class,sqlEntityExpression,selectTableInfoType);
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
}
