package com.easy.query.core.sharding.rule;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.sharding.enums.ShardingOperatorEnum;


/**
 * create time 2023/4/19 09:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RouteRuleFilter {
    RouteFunction<String> routeFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName, boolean isMainShardingProperty,boolean withEntity);
}
