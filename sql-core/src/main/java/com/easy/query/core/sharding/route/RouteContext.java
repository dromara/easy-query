package com.easy.query.core.sharding.route;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

/**
 * create time 2023/4/19 14:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class RouteContext {
    private final ShardingRouteResult entityRouteResult;

    public RouteContext(ShardingRouteResult entityRouteResult){

        this.entityRouteResult = entityRouteResult;
    }

    public ShardingRouteResult getEntityRouteResult() {
        return entityRouteResult;
    }
}
