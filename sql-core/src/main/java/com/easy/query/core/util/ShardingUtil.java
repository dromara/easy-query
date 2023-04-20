package com.easy.query.core.util;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.RoutePredicateDiscover;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.rule.RouteRuleFilter;

/**
 * create time 2023/4/19 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingUtil {

    public static RoutePredicateExpression getRoutePredicateExpression(SqlParserResult sqlParserResult, EntityMetadata entityMetadata,
                                                                       RouteRuleFilter routeRuleFilter, boolean shardingTableRoute){
        RoutePredicateDiscover routePredicateDiscover = new RoutePredicateDiscover(entityMetadata,routeRuleFilter,shardingTableRoute);
        return routePredicateDiscover.getRouteParseExpression(sqlParserResult);
    }
}
