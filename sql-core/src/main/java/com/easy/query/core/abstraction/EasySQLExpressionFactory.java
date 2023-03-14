package com.easy.query.core.abstraction;

import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.query.*;
import com.easy.query.core.abstraction.metadata.EntityMetadata;

/**
 * @FileName: EasySqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:43
 * @Created by xuejiaming
 */
public interface EasySQLExpressionFactory {
    SqlExpressionContext createSqlExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias);
    SqlEntityTableExpression createSqlEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType);
    SqlEntityTableExpression createSqlAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, SqlEntityQueryExpression sqlEntityQueryExpression);
    SqlEntityQueryExpression createSqlEntityQueryExpression(SqlExpressionContext sqlExpressionContext);
    SqlEntityInsertExpression createSqlEntityInsertExpression(SqlExpressionContext sqlExpressionContext);
    SqlEntityUpdateExpression createSqlEntityUpdateExpression(SqlExpressionContext sqlExpressionContext, boolean expression);
    SqlEntityDeleteExpression createSqlEntityDeleteExpression(SqlExpressionContext sqlExpressionContext,boolean expression);
}
