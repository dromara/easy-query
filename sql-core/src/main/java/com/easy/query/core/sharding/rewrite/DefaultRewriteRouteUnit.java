package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.sharding.router.RouteUnit;

/**
 * create time 2023/5/15 08:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRewriteRouteUnit implements RewriteRouteUnit{

    private final RouteUnit routeUnit;

    public DefaultRewriteRouteUnit(RouteUnit routeUnit){

        this.routeUnit = routeUnit;
    }

    public RouteUnit getRouteUnit() {
        return routeUnit;
    }

    @Override
    public EntitySQLExpression rewrite(EntitySQLExpression entitySQLExpression) {
        return entitySQLExpression;
    }
}
