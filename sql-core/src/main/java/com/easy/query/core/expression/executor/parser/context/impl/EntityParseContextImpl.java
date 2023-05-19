package com.easy.query.core.expression.executor.parser.context.impl;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.EntityParseContext;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

import java.util.List;

/**
 * create time 2023/5/18 23:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityParseContextImpl implements EntityParseContext {
    private final ExecutorContext executorContext;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final List<Object> entities;

    public EntityParseContextImpl(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities){

        this.executorContext = executorContext;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.entities = entities;
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
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }
}
