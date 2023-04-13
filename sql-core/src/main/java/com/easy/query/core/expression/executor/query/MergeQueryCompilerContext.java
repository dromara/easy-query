package com.easy.query.core.expression.executor.query;

import com.easy.query.core.sharding.route.ShardingRouteResult;

/**
 * create time 2023/4/11 12:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MergeQueryCompilerContext extends QueryCompilerContext{
    ShardingRouteResult GetShardingRouteResult();

    boolean isCrossTable();
    boolean isCrossDataSource();
}
