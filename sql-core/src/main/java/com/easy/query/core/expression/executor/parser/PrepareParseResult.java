package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;

import java.util.Set;

/**
 * create time 2023/4/9 22:18
 * 文件说明
 *
 * @author xuejiaming
 */
public final class PrepareParseResult {
    private final Set<Class<?>> shardingEntities;
    private final EntityExpression entityExpression;

    public PrepareParseResult(Set<Class<?>> shardingEntities, EntityExpression entityExpression){

        this.shardingEntities = shardingEntities;
        this.entityExpression = entityExpression;
    }

    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }

    public EntityExpression getEntityExpression() {
        return entityExpression;
    }

}
