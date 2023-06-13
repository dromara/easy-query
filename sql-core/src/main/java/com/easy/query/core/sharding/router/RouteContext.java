package com.easy.query.core.sharding.router;

/**
 * create time 2023/4/19 14:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class RouteContext {
    private final ShardingRouteResult shardingRouteResult;

    public RouteContext(ShardingRouteResult shardingRouteResult){

        this.shardingRouteResult = shardingRouteResult;
    }

    public ShardingRouteResult getShardingRouteResult() {
        return shardingRouteResult;
    }
}
