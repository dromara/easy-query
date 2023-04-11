package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.expression.sql.EntityQueryExpression;

/**
 * create time 2023/4/11 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class DataSourceRouteParams {
    private final EntityQueryExpression entityQueryExpression;
    private final Object shardingKeyValue;

    public DataSourceRouteParams(EntityQueryExpression entityQueryExpression, Object shardingKeyValue){

        this.entityQueryExpression = entityQueryExpression;
        this.shardingKeyValue = shardingKeyValue;
    }

    public EntityQueryExpression getEntityQueryExpression() {
        return entityQueryExpression;
    }

    public Object getShardingKeyValue() {
        return shardingKeyValue;
    }
}
