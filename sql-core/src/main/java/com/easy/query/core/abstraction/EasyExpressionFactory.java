package com.easy.query.core.abstraction;

import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.SqlEntityDeleteExpression;
import com.easy.query.core.expression.sql.SqlEntityInsertExpression;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityUpdateExpression;
import com.easy.query.core.expression.sql.SqlExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * @FileName: EasySqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:43
 * @author xuejiaming
 */
public interface EasyExpressionFactory {
    SqlExpressionContext createSqlExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias);
    SqlEntityTableExpression createSqlEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType);
    SqlEntityTableExpression createSqlAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, SqlEntityQueryExpression sqlEntityQueryExpression);
    SqlEntityQueryExpression createSqlEntityQueryExpression(SqlExpressionContext sqlExpressionContext);
    SqlEntityInsertExpression createSqlEntityInsertExpression(SqlExpressionContext sqlExpressionContext);
    SqlEntityUpdateExpression createSqlEntityUpdateExpression(SqlExpressionContext sqlExpressionContext, boolean expression);
    SqlEntityDeleteExpression createSqlEntityDeleteExpression(SqlExpressionContext sqlExpressionContext, boolean expression);
}
