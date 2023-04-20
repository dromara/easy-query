package com.easy.query.core.sharding.route.table;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.parser.SqlParserResult;
import com.easy.query.core.sharding.route.Route;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/5 13:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRoute extends Route {
    Collection<TableRouteUnit> route(TableRouteRule tableRouteRule,DataSourceRouteResult dataSourceRouteResult, SqlParserResult sqlParserResult);
//    String shardingKeyToTail(Object shardingKey);
//
//    /**
//     * 根据查询条件路由返回物理表
//     * @param dataSourceRouteResult
//     * @param entityQueryExpression
//     * @param isQuery
//     * @return
//     */
//    List<TableRouteUnit> routeWithPredicate(DataSourceRouteResult dataSourceRouteResult, EntityQueryExpression entityQueryExpression, boolean isQuery);
//
//
//    /**
//     * 根据值进行路由
//     * @param dataSourceRouteResult
//     * @param shardingKey
//     * @return
//     */
//    TableRouteUnit RouteWithValue(DataSourceRouteResult dataSourceRouteResult, Object shardingKey);
//
//    /**
//     * 获取所有的目前数据库存在的尾巴,每次路由都会调用
//     * @return
//     */
//    List<String> getTails();
}
