package com.easy.query.core.expression.executor.parser.context;

import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;

/**
 * create time 2023/5/18 21:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicatePrepareParseContext extends PrepareParseContext {
    EntityPredicateSQLExpression getEntityPredicateSQLExpression();

    @Override
    EntityPredicateExpressionBuilder getEntityExpressionBuilder();
}
