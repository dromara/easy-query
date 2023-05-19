package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.EntityParseContext;
import com.easy.query.core.expression.executor.parser.descriptor.TableEntityParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/4/25 17:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityPrepareParseResult implements EntityPrepareParseResult {
    private final ExecutorContext executorContext;
    private final TableEntityParseDescriptor tableEntityParseDescriptor;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final List<Object> entities;
    private final boolean sharding;

    public EasyEntityPrepareParseResult(EntityParseContext entityPrepareParseContext, TableEntityParseDescriptor tableEntityParseDescriptor) {
        this.executorContext = entityPrepareParseContext.getExecutorContext();
        this.tableEntityParseDescriptor = tableEntityParseDescriptor;
        this.entityExpressionBuilder = entityPrepareParseContext.getEntityExpressionBuilder();
        this.entities = entityPrepareParseContext.getEntities();
        this.sharding = EasyCollectionUtil.isNotEmpty(tableEntityParseDescriptor.getTables());
    }

    @Override
    public List<Object> getEntities() {
        return entities;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public TableEntityParseDescriptor getTableParseDescriptor() {
        return tableEntityParseDescriptor;
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }
}
