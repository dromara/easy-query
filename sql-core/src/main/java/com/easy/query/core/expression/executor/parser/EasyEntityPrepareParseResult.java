package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/25 17:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityPrepareParseResult implements EntityPrepareParseResult{
    private final ExecutorContext executorContext;
    private final Set<TableAvailable> shardingTables;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final List<Object> entities;
    private final boolean sharding;

    public EasyEntityPrepareParseResult(ExecutorContext executorContext, Set<TableAvailable> shardingTables, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities){
        this.executorContext = executorContext;

        this.shardingTables = shardingTables;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.entities = entities;
        this.sharding = EasyCollectionUtil.isNotEmpty(shardingTables);
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
    public Set<TableAvailable> getShardingTables() {
        return shardingTables;
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }
}
