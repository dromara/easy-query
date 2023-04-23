package com.easy.query.core.sharding.route;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

/**
 * create time 2023/4/19 14:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class RouteContext {
    private final EasyEntitySqlExpression easyEntitySqlExpression;
    private final ShardingRouteResult entityRouteResult;

    public RouteContext(EasyEntitySqlExpression easyEntitySqlExpression, ShardingRouteResult entityRouteResult){

        this.easyEntitySqlExpression = easyEntitySqlExpression;
        this.entityRouteResult = entityRouteResult;
    }

    public EasyEntitySqlExpression getEntitySqlExpression() {
        return easyEntitySqlExpression;
    }

    public ShardingRouteResult getEntityRouteResult() {
        return entityRouteResult;
    }
}
