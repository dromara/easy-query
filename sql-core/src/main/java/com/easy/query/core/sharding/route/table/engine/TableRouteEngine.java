package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.sharding.route.ShardingRouteResult;

/**
 * create time 2023/4/5 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouteEngine {
    /**
     * 表路由获取分片路由结果
     * @param tableRouteContext
     * @return
     */
    ShardingRouteResult route(TableRouteContext tableRouteContext);
}
