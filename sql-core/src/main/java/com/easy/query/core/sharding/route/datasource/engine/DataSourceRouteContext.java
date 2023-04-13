package com.easy.query.core.sharding.route.datasource.engine;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;

import java.util.Set;

/**
 * create time 2023/4/11 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DataSourceRouteContext {
    private final Set<Class<?>> shardingEntities;
    private final EntityExpression entityExpression;

    public DataSourceRouteContext(Set<Class<?>> shardingEntities, EntityExpression entityExpression){

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
