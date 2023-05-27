package com.easy.query.core.expression.sql.builder.factory;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.EntityTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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
public  class DefaultEasyExpressionBuilderFactory implements ExpressionBuilderFactory {
    @Override
    public ExpressionContext createExpressionContext(QueryRuntimeContext runtimeContext) {
        return new EasyExpressionContext(runtimeContext);
    }

    @Override
    public EntityTableExpressionBuilder createEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        return new TableExpressionBuilder(tableAvailable,multiTableType,runtimeContext);
    }

    @Override
    public EntityTableExpressionBuilder createAnonymousEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return new AnonymousTableExpressionBuilder(tableAvailable,multiTableType, entityQueryExpressionBuilder);
    }

    @Override
    public EntityQueryExpressionBuilder createEntityQueryExpressionBuilder(ExpressionContext sqlExpressionContext) {
        return new QueryExpressionBuilder(sqlExpressionContext);
    }

    @Override
    public EntityInsertExpressionBuilder createEntityInsertExpressionBuilder(ExpressionContext sqlExpressionContext) {
        return new InsertExpressionBuilder(sqlExpressionContext);
    }

    @Override
    public EntityUpdateExpressionBuilder createEntityUpdateExpressionBuilder(ExpressionContext sqlExpressionContext, boolean expression) {
        return new UpdateExpressionBuilder(sqlExpressionContext,expression);
    }

    @Override
    public EntityDeleteExpressionBuilder createEntityDeleteExpressionBuilder(ExpressionContext sqlExpressionContext, boolean expression) {
        return new DeleteExpressionBuilder(sqlExpressionContext,expression);
    }
}
