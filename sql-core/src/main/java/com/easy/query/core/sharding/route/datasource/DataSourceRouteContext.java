package com.easy.query.core.sharding.route.datasource;

import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.def.EasyQueryExpression;

import java.util.Set;

/**
 * create time 2023/4/11 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DataSourceRouteContext {
    private final Set<Class<?>> shardingEntities;
    private final EntityQueryExpression entityQueryExpression;

    public DataSourceRouteContext(Set<Class<?>> shardingEntities, EntityQueryExpression entityQueryExpression){

        this.shardingEntities = shardingEntities;
        this.entityQueryExpression = entityQueryExpression;
    }

    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }

    public EntityQueryExpression getEntityQueryExpression() {
        return entityQueryExpression;
    }
}
