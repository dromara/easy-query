package com.easy.query.core.expression.executor.parser;

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

    public PrepareParseResult(Set<Class<?>> shardingEntities){

        this.shardingEntities = shardingEntities;
    }

    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }
}
