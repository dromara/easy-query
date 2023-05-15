package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.sharding.route.RouteUnit;

/**
 * create time 2023/5/15 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RewriteRouteUnit {
    RouteUnit getRouteUnit();
    EasyEntitySqlExpression rewrite(EasyEntitySqlExpression entitySqlExpression);
}
