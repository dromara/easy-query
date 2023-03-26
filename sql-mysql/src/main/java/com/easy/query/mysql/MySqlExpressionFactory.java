package com.easy.query.mysql;

import com.easy.query.core.expression.sql.SqlEntityDeleteExpression;
import com.easy.query.core.expression.sql.SqlEntityInsertExpression;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityUpdateExpression;
import com.easy.query.core.expression.sql.SqlExpressionContext;
import com.easy.query.core.expression.sql.def.EasyAnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.def.EasySqlExpressionContext;
import com.easy.query.mysql.expression.MySqlInsertExpression;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasyExpressionFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.mysql.expression.MySqlDeleteExpression;
import com.easy.query.mysql.expression.MySqlQueryExpression;
import com.easy.query.mysql.expression.MySqlUpdateExpression;

/**
 * @FileName: MySQLSqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:55
 * @author xuejiaming
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
    public SqlEntityUpdateExpression createSqlEntityUpdateExpression(SqlExpressionContext sqlExpressionContext, boolean expression) {
        return new MySqlUpdateExpression(sqlExpressionContext,expression);
    }

    @Override
    public SqlEntityDeleteExpression createSqlEntityDeleteExpression(SqlExpressionContext sqlExpressionContext, boolean expression) {
        return new MySqlDeleteExpression(sqlExpressionContext,expression);
    }
}
