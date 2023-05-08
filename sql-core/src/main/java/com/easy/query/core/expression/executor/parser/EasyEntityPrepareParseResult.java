package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/25 17:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityPrepareParseResult implements EntityPrepareParseResult{
    private final Set<TableAvailable> shardingTables;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final List<Object> entities;

    public EasyEntityPrepareParseResult(Set<TableAvailable> shardingTables, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities){

        this.shardingTables = shardingTables;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.entities = entities;
    }
    @Override
    public List<Object> getEntities() {
        return entities;
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
