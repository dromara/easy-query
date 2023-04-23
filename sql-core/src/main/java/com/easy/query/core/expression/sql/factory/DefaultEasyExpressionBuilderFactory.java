package com.easy.query.core.expression.sql.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.impl.AnonymousTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.DeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.InsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.QueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.TableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EasyExpressionContext;
import com.easy.query.core.expression.sql.builder.impl.UpdateExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/4/2 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public  class DefaultEasyExpressionBuilderFactory implements EasyExpressionBuilderFactory {
    @Override
    public ExpressionContext createExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias) {
        return new EasyExpressionContext(runtimeContext,alias);
    }

    @Override
    public EntityTableExpressionBuilder createEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType) {
        return new TableExpressionBuilder(entityMetadata,index,alias,multiTableType);
    }

    @Override
    public EntityTableExpressionBuilder createAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder sqlEntityQueryExpression) {
        return new AnonymousTableExpressionBuilder(entityMetadata,index,alias,multiTableType,sqlEntityQueryExpression);
    }
    @Override
    public EntityQueryExpressionBuilder createEntityQueryExpression(ExpressionContext sqlExpressionContext) {
        return new QueryExpressionBuilder(sqlExpressionContext);
    }

    @Override
    public EntityInsertExpressionBuilder createEntityInsertExpression(ExpressionContext sqlExpressionContext) {
        return new InsertExpressionBuilder(sqlExpressionContext);
    }

    @Override
    public EntityUpdateExpressionBuilder createEntityUpdateExpression(ExpressionContext sqlExpressionContext, boolean expression) {
        return new UpdateExpressionBuilder(sqlExpressionContext,expression);
    }

    @Override
    public EntityDeleteExpressionBuilder createEntityDeleteExpression(ExpressionContext sqlExpressionContext, boolean expression) {
        return new DeleteExpressionBuilder(sqlExpressionContext,expression);
    }
}
