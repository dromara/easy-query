package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.sql.expression.EntitySQLExpression;

/**
 * create time 2023/4/26 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityToExpressionBuilder extends EntityExpressionBuilder {
    @Override
    EntityExpressionBuilder cloneEntityExpressionBuilder();

    EntitySQLExpression toExpression(Object entity);
}
