package com.easy.query.core.sharding.route;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.rule.RouteRuleFilter;

/**
 * create time 2023/4/19 08:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class RoutePredicateDiscover {
    private final EntityMetadata entityMetadata;
    private final RouteRuleFilter routeRuleFilter;
    private final boolean shardingTableRoute;

    public RoutePredicateDiscover(EntityMetadata entityMetadata, RouteRuleFilter routeRuleFilter, boolean shardingTableRoute) {

        this.entityMetadata = entityMetadata;
        this.routeRuleFilter = routeRuleFilter;
        this.shardingTableRoute = shardingTableRoute;
    }

    public RoutePredicateExpression getRouteParseExpression(SqlParserResult sqlParserResult) {
        return null;
    }
}
