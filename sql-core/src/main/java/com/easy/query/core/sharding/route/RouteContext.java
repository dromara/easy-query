package com.easy.query.core.sharding.route;

import com.easy.query.core.expression.sql.EntityExpression;

/**
 * create time 2023/4/19 14:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class RouteContext {
    private final EntityExpression entityExpression;
    private final ShardingRouteResult entityRouteResult;

    public RouteContext(EntityExpression entityExpression, ShardingRouteResult entityRouteResult){

        this.entityExpression = entityExpression;
        this.entityRouteResult = entityRouteResult;
    }

    public EntityExpression getEntityExpression() {
        return entityExpression;
    }

    public ShardingRouteResult getEntityRouteResult() {
        return entityRouteResult;
    }
}
