package com.easy.query.mysql;

import com.easy.query.core.query.*;
import com.easy.query.mysql.expression.MySqlInsertExpression;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasyExpressionFactory;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.mysql.expression.MySqlDeleteExpression;
import com.easy.query.mysql.expression.MySqlQueryExpression;
import com.easy.query.mysql.expression.MySqlUpdateExpression;

/**
 * @FileName: MySQLSqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:55
 * @Created by xuejiaming
 */
public class MySqlExpressionFactory implements EasyExpressionFactory {
    @Override
    public SqlExpressionContext createSqlExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias) {
        return new EasySqlExpressionContext(runtimeContext,alias);
    }

    @Override
    public SqlEntityTableExpression createSqlEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType) {
        return new EasyEntityTableExpression(entityMetadata,index,alias,multiTableType);
    }

    @Override
    public SqlEntityTableExpression createSqlAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, SqlEntityQueryExpression sqlEntityQueryExpression) {
        return new EasyAnonymousEntityTableExpression(entityMetadata,index,alias,multiTableType,sqlEntityQueryExpression);
    }

    @Override
    public SqlEntityQueryExpression createSqlEntityQueryExpression(SqlExpressionContext sqlExpressionContext) {
        return new MySqlQueryExpression(sqlExpressionContext);
    }

    @Override
    public SqlEntityInsertExpression createSqlEntityInsertExpression(SqlExpressionContext sqlExpressionContext) {
        return new MySqlInsertExpression(sqlExpressionContext);
    }

    @Override
    public SqlEntityUpdateExpression createSqlEntityUpdateExpression(SqlExpressionContext sqlExpressionContext,boolean expression) {
        return new MySqlUpdateExpression(sqlExpressionContext,expression);
    }

    @Override
    public SqlEntityDeleteExpression createSqlEntityDeleteExpression(SqlExpressionContext sqlExpressionContext,boolean expression) {
        return new MySqlDeleteExpression(sqlExpressionContext,expression);
    }
}
