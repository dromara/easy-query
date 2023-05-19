package com.easy.query.core.expression.executor.parser.context.impl;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.InsertEntityParseContext;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.List;

/**
 * create time 2023/5/18 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertEntityParseContextImpl implements InsertEntityParseContext {

    private final ExecutorContext executorContext;
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;

    public InsertEntityParseContextImpl(ExecutorContext executorContext, EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement){

        this.executorContext = executorContext;
        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
    }
    @Override
    public List<Object> getEntities() {
        return entities;
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public EntityInsertExpressionBuilder getEntityExpressionBuilder() {
        return entityInsertExpressionBuilder;
    }

    @Override
    public boolean getFillAutoIncrement() {
        return fillAutoIncrement;
    }
}
