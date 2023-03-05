package org.easy.query.mysql;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.EasyQueryableFactory;
import org.easy.query.core.abstraction.EasySqlExpressionFactory;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.query.*;
import org.easy.query.core.util.EasyUtil;
import org.easy.query.mysql.base.MySQLQueryExpression;
import org.easy.query.mysql.base.MySQLQueryable;
import org.easy.query.mysql.base.MySQLQueryable2;

/**
 * @FileName: MySQLEasyQueryableFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/3 13:32
 * @Created by xuejiaming
 */
public class MySQLEasyQueryableFactory implements EasyQueryableFactory {
    private final EasySqlExpressionFactory easySqlExpressionFactory;

    public MySQLEasyQueryableFactory(EasySqlExpressionFactory easySqlExpressionFactory){
        this.easySqlExpressionFactory = easySqlExpressionFactory;
    }
    @Override
    public <T> Queryable<T> createQueryable(Class<T> clazz, EasyQueryRuntimeContext runtimeContext,String alias) {
        SqlExpressionContext easySqExpressionContext =easySqlExpressionFactory.createSqlExpressionContext(runtimeContext,alias);
        SqlEntityQueryExpression sqlEntityQueryExpression = easySqlExpressionFactory.createSqlEntityQueryExpression(easySqExpressionContext);
        EntityMetadata entityMetadata =runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        int tableIndex = EasyUtil.getNextTableIndex(sqlEntityQueryExpression);;
        SqlEntityTableExpression sqlTable =easySqlExpressionFactory.createSqlEntityTableExpression(entityMetadata,  tableIndex,easySqExpressionContext.createTableAlias(), MultiTableTypeEnum.FROM);
        sqlEntityQueryExpression.addSqlEntityTableExpression(sqlTable);
        return new MySQLQueryable<>(clazz,sqlEntityQueryExpression);
    }

    @Override
    public <T> Queryable<T> cloneQueryable(Queryable<T> source) {
        SqlEntityQueryExpression sqlEntityExpression = source.getSqlEntityExpression();
        SqlEntityQueryExpression sqlEntityQueryExpression = sqlEntityExpression.cloneSqlQueryExpression();
        return new MySQLQueryable<>(source.queryClass(),sqlEntityQueryExpression);
    }

    @Override
    public <T> Queryable<T> createQueryable(Class<T> clazz, SqlEntityQueryExpression sqlEntityExpression) {
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        MySQLQueryExpression mySQLEntityExpression = new MySQLQueryExpression(queryExpressionContext);
        EntityMetadata entityMetadata = queryExpressionContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        EasyAnonymousEntityTableExpression anonymousTable = new EasyAnonymousEntityTableExpression(entityMetadata, 0, queryExpressionContext.createTableAlias(), MultiTableTypeEnum.FROM, sqlEntityExpression);
        mySQLEntityExpression.addSqlEntityTableExpression(anonymousTable);
        return new MySQLQueryable<>(clazz,mySQLEntityExpression);
    }
//
//    @Override
//    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, EasyQueryRuntimeContext runtimeContext) {
//        return new MySQLQueryable2<>(t1Class,t2Class,new SelectContext(runtimeContext),selectTableInfoType);
//    }

    @Override
    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression) {

        EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t2Class);

        int tableIndex =  EasyUtil.getNextTableIndex(sqlEntityExpression);
        SqlExpressionContext queryExpressionContext = sqlEntityExpression.getSqlExpressionContext();
        EasyEntityTableExpression sqlTable =new EasyEntityTableExpression(entityMetadata,  tableIndex,queryExpressionContext.createTableAlias(), selectTableInfoType);
        sqlEntityExpression.addSqlEntityTableExpression(sqlTable);

        return new MySQLQueryable2<>(t1Class,t2Class,sqlEntityExpression,selectTableInfoType);
    }
}
