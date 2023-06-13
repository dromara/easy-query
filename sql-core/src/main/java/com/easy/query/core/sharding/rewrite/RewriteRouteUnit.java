package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.sharding.router.RouteUnit;

/**
 * create time 2023/5/15 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RewriteRouteUnit {
    RouteUnit getRouteUnit();
    EntitySQLExpression rewrite(EntitySQLExpression entitySQLExpression);
}
