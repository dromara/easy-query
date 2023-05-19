package com.easy.query.core.expression.executor.parser.context;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;

/**
 * create time 2023/5/18 21:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryPredicateParseContext extends PredicatePrepareParseContext{
    @Override
    EntityQuerySQLExpression getEntityPredicateSQLExpression();

    @Override
    EntityQueryExpressionBuilder getEntityExpressionBuilder();
}
