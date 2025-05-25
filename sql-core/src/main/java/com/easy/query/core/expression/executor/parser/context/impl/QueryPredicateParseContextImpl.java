package com.easy.query.core.expression.executor.parser.context.impl;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.QueryPredicateParseContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;

/**
 * create time 2023/5/18 21:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryPredicateParseContextImpl implements QueryPredicateParseContext {
    private final ExecutorContext executorContext;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final EntityQuerySQLExpression entityQuerySQLExpression;

    public QueryPredicateParseContextImpl(ExecutorContext executorContext, EntityQueryExpressionBuilder entityQueryExpressionBuilder,EntityQuerySQLExpression entityQuerySQLExpression) {

        this.executorContext = executorContext;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.entityQuerySQLExpression = entityQuerySQLExpression;
    }

    @Override
    public EntityQuerySQLExpression getEntityPredicateSQLExpression() {
        return entityQuerySQLExpression;
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public EntityQueryExpressionBuilder getEntityExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

}
