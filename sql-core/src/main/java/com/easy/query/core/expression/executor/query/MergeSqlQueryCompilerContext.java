package com.easy.query.core.expression.executor.query;

import com.easy.query.core.sharding.route.ShardingRouteResult;

import java.util.Set;

/**
 * create time 2023/4/11 12:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class MergeSqlQueryCompilerContext implements MergeQueryCompilerContext {
    private final QueryCompilerContext queryCompilerContext;
    private final ShardingRouteResult shardingRouteResult;

    public MergeSqlQueryCompilerContext(QueryCompilerContext queryCompilerContext, ShardingRouteResult shardingRouteResult){
        this.queryCompilerContext = queryCompilerContext;

        this.shardingRouteResult = shardingRouteResult;
    }
    @Override
    public Set<Class<?>> getShardingEntities() {
        return queryCompilerContext.getShardingEntities();
    }

    @Override
    public boolean isShardingQuery() {
        return queryCompilerContext.isShardingQuery();
    }

    @Override
    public boolean isSingleShardingQuery() {
        return queryCompilerContext.isShardingQuery();
    }

    @Override
    public ShardingRouteResult GetShardingRouteResult() {
        return shardingRouteResult;
    }

    @Override
    public boolean isCrossTable() {
        return shardingRouteResult.isCrossTable();
    }

    @Override
    public boolean isCrossDataSource() {
        return shardingRouteResult.isCrossDataSource();
    }
}
