package com.easy.query.core.expression.sql.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.def.EasyAnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/4/2 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEasyExpressionFactory implements EasyExpressionFactory{
    @Override
    public ExpressionContext createExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias) {
        return new EasyExpressionContext(runtimeContext,alias);
    }

    @Override
    public EntityTableExpression createEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType) {
        return new EasyEntityTableExpression(entityMetadata,index,alias,multiTableType);
    }

    @Override
    public EntityTableExpression createAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EntityQueryExpression sqlEntityQueryExpression) {
        return new EasyAnonymousEntityTableExpression(entityMetadata,index,alias,multiTableType,sqlEntityQueryExpression);
    }
}
