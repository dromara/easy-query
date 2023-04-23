package com.easy.query.core.expression.sql.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.impl.AnonymousQueryExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * @FileName: EasySqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:43
 * @author xuejiaming
 */
public interface EasyExpressionBuilderFactory {
    ExpressionContext createExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias);
    EntityTableExpressionBuilder createEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType);
    EntityTableExpressionBuilder createAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder sqlEntityQueryExpression);
    EntityQueryExpressionBuilder createEntityQueryExpression(ExpressionContext sqlExpressionContext);
    default EntityQueryExpressionBuilder createAnonymousQueryExpression(String sql, ExpressionContext sqlExpressionContext){
        return new AnonymousQueryExpressionBuilder(sql,sqlExpressionContext);
    }
    EntityInsertExpressionBuilder createEntityInsertExpression(ExpressionContext sqlExpressionContext);
    EntityUpdateExpressionBuilder createEntityUpdateExpression(ExpressionContext sqlExpressionContext, boolean expression);
    EntityDeleteExpressionBuilder createEntityDeleteExpression(ExpressionContext sqlExpressionContext, boolean expression);
}
