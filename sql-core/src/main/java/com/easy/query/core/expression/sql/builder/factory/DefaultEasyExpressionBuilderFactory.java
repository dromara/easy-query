package com.easy.query.core.expression.sql.builder.factory;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EasyExpressionContext;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.MapUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousDefaultTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.DeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.InsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.InsertMapExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.QueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.DefaultTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.UpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.UpdateMapExpressionBuilder;

/**
 * create time 2023/4/2 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyExpressionBuilderFactory implements ExpressionBuilderFactory {
    @Override
    public ExpressionContext createExpressionContext(QueryRuntimeContext runtimeContext) {
        return new EasyExpressionContext(runtimeContext);
    }

    @Override
    public EntityTableExpressionBuilder createEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        return new DefaultTableExpressionBuilder(tableAvailable, multiTableType, runtimeContext);
    }

    @Override
    public EntityTableExpressionBuilder createAnonymousEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return new AnonymousDefaultTableExpressionBuilder(tableAvailable, multiTableType, entityQueryExpressionBuilder);
    }

    @Override
    public EntityQueryExpressionBuilder createEntityQueryExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> queryClass) {
        return new QueryExpressionBuilder(sqlExpressionContext, queryClass);
    }

    @Override
    public EntityInsertExpressionBuilder createEntityInsertExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> queryClass) {
        return new InsertExpressionBuilder(sqlExpressionContext, queryClass);
    }

    @Override
    public EntityUpdateExpressionBuilder createEntityUpdateExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> queryClass, boolean expression) {
        return new UpdateExpressionBuilder(sqlExpressionContext, queryClass, expression);
    }

    @Override
    public MapUpdateExpressionBuilder createMapUpdateExpressionBuilder(ExpressionContext sqlExpressionContext) {
        return new UpdateMapExpressionBuilder(sqlExpressionContext);
    }

    @Override
    public EntityDeleteExpressionBuilder createEntityDeleteExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> queryClass, boolean expression) {
        return new DeleteExpressionBuilder(sqlExpressionContext, queryClass, expression);
    }

    @Override
    public EntityInsertExpressionBuilder createMapInsertExpressionBuilder(ExpressionContext sqlExpressionContext) {
        return new InsertMapExpressionBuilder(sqlExpressionContext);
    }
}
