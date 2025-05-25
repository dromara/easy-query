package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.InsertEntityParseContext;
import com.easy.query.core.expression.executor.parser.descriptor.TableEntityParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/4/24 22:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyInsertPrepareParseResult implements InsertPrepareParseResult {
    private final ExecutorContext executorContext;
    private final TableEntityParseDescriptor tableEntityParseDescriptor;
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;

    public EasyInsertPrepareParseResult(InsertEntityParseContext insertEntityPrepareParseContext, TableEntityParseDescriptor tableEntityParseDescriptor) {
        this.executorContext = insertEntityPrepareParseContext.getExecutorContext();
        this.tableEntityParseDescriptor = tableEntityParseDescriptor;
        this.entityInsertExpressionBuilder = insertEntityPrepareParseContext.getEntityExpressionBuilder();
        this.entities = insertEntityPrepareParseContext.getEntities();
        this.fillAutoIncrement = insertEntityPrepareParseContext.getFillAutoIncrement();
    }

    @Override
    public List<Object> getEntities() {
        return entities;
    }

    @Override
    public boolean isSharding() {
        return true;
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
    public EntityInsertExpressionBuilder getEntityExpressionBuilder() {
        return entityInsertExpressionBuilder;
    }

    @Override
    public boolean isFillAutoIncrement() {
        return fillAutoIncrement;
    }
}
