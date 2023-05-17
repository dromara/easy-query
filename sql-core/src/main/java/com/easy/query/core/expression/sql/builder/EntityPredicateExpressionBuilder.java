package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;

/**
 * create time 2023/4/26 11:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityPredicateExpressionBuilder extends EntityExpressionBuilder{
    @Override
    EntityPredicateExpressionBuilder cloneEntityExpressionBuilder();

    @Override
    EntityPredicateSQLExpression toExpression();
}
