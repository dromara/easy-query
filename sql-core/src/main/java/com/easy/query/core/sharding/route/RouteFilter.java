package com.easy.query.core.sharding.route;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;


/**
 * create time 2023/4/19 09:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RouteFilter<T> {
    RouteFunction<T> routeFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName, boolean isMainShardingProperty, boolean withEntity);
}
