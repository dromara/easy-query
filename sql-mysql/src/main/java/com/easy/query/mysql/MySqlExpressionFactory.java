package com.easy.query.mysql;

import com.easy.query.core.expression.sql.EntityDeleteExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.EntityUpdateExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.def.EasyAnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyAnonymousQueryExpression;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyExpressionContext;
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

    @Override
    public EntityQueryExpression createEntityQueryExpression(ExpressionContext sqlExpressionContext) {
        return new MySqlQueryExpression(sqlExpressionContext);
    }

    @Override
    public EntityInsertExpression createEntityInsertExpression(ExpressionContext sqlExpressionContext) {
        return new MySqlInsertExpression(sqlExpressionContext);
    }

    @Override
    public EntityUpdateExpression createEntityUpdateExpression(ExpressionContext sqlExpressionContext, boolean expression) {
        return new MySqlUpdateExpression(sqlExpressionContext,expression);
    }

    @Override
    public EntityDeleteExpression createEntityDeleteExpression(ExpressionContext sqlExpressionContext, boolean expression) {
        return new MySqlDeleteExpression(sqlExpressionContext,expression);
    }
}
