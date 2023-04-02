package com.easy.query.core.expression.sql.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.EntityDeleteExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.EntityUpdateExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.def.EasyAnonymousQueryExpression;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * @FileName: EasySqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:43
 * @author xuejiaming
 */
public interface EasyExpressionFactory {
    ExpressionContext createExpressionContext(EasyQueryRuntimeContext runtimeContext, String alias);
    EntityTableExpression createEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType);
    EntityTableExpression createAnonymousEntityTableExpression(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EntityQueryExpression sqlEntityQueryExpression);
    EntityQueryExpression createEntityQueryExpression(ExpressionContext sqlExpressionContext);
    default EntityQueryExpression createAnonymousQueryExpression(String sql,ExpressionContext sqlExpressionContext){
        return new EasyAnonymousQueryExpression(sql,sqlExpressionContext);
    }
    EntityInsertExpression createEntityInsertExpression(ExpressionContext sqlExpressionContext);
    EntityUpdateExpression createEntityUpdateExpression(ExpressionContext sqlExpressionContext, boolean expression);
    EntityDeleteExpression createEntityDeleteExpression(ExpressionContext sqlExpressionContext, boolean expression);
}
