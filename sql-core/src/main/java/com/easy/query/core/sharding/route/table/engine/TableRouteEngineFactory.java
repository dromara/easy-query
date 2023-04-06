package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.sharding.route.ShardingRouteResult;
import com.easy.query.core.sharding.route.datasource.DataSourceRouteResult;

import java.util.Map;

/**
 * create time 2023/4/5 22:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouteEngineFactory {

    ShardingRouteResult route(DataSourceRouteResult dataSourceRouteResult, EntityQueryExpression entityQueryExpression, Map<Class<?>,EntityQueryExpression> queryEntities);
}
