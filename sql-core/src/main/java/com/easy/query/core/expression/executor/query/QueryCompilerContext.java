package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.sharding.route.ShardingRouteResult;

import java.util.Set;

/**
 * create time 2023/4/11 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryCompilerContext {
    EntityExpression getEntityExpression();
    Set<Class<?>> getShardingEntities();
    boolean isShardingQuery();
    boolean isSingleShardingQuery();
    ShardingRouteResult GetEntityRouteResult();

    boolean isCrossTable();
    boolean isCrossDataSource();
}
