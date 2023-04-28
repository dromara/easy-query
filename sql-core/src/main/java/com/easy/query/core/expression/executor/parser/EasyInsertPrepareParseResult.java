package com.easy.query.core.expression.executor.parser;

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
    private final Set<Class<?>> shardingEntities;
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;

    public EasyInsertPrepareParseResult(Set<Class<?>> shardingEntities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement){

        this.shardingEntities = shardingEntities;
        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
    }
    @Override
    public List<Object> getEntities() {
        return entities;
    }

    @Override
    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
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
