package com.easy.query.core.expression.executor.parser.context.impl;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.PredicatePrepareParseContext;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;

/**
 * create time 2023/5/18 23:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateParseContextImpl implements PredicatePrepareParseContext {
    private final ExecutorContext executorContext;
    private final EntityPredicateExpressionBuilder entityPredicateExpressionBuilder;
    private final EntityPredicateSQLExpression entityPredicateSQLExpression;

    public PredicateParseContextImpl(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder,EntityPredicateSQLExpression entityPredicateSQLExpression){

        this.executorContext = executorContext;
        this.entityPredicateExpressionBuilder = entityPredicateExpressionBuilder;
        this.entityPredicateSQLExpression = entityPredicateSQLExpression;
    }
    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public EntityPredicateSQLExpression getEntityPredicateSQLExpression() {
        return entityPredicateSQLExpression;
    }

    @Override
    public EntityPredicateExpressionBuilder getEntityExpressionBuilder() {
        return entityPredicateExpressionBuilder;
    }

}
