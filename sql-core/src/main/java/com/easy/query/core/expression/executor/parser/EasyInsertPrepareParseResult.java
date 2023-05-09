package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/24 22:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyInsertPrepareParseResult implements InsertPrepareParseResult{
    private final ExecutorContext executorContext;
    private final Set<TableAvailable> shardingTables;
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;

    public EasyInsertPrepareParseResult(ExecutorContext executorContext, Set<TableAvailable> shardingTables, EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement){
        this.executorContext = executorContext;

        this.shardingTables = shardingTables;
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
    public Set<TableAvailable> getShardingTables() {
        return shardingTables;
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
