package com.easy.query.core.sharding.route;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteEngine;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.engine.TableRouteContext;
import com.easy.query.core.sharding.route.table.engine.TableRouteEngine;

/**
 * create time 2023/4/20 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRouteContextFactory implements RouteContextFactory{
    private final DataSourceRouteEngine dataSourceRouteEngine;
    private final TableRouteEngine tableRouteEngine;

    public DefaultRouteContextFactory(DataSourceRouteEngine dataSourceRouteEngine, TableRouteEngine tableRouteEngine){

        this.dataSourceRouteEngine = dataSourceRouteEngine;
        this.tableRouteEngine = tableRouteEngine;
    }
    @Override
    public RouteContext createRouteContext(PrepareParseResult prepareParseResult) {

        //获取分库节点
        DataSourceRouteResult dataSourceRouteResult = dataSourceRouteEngine.route(prepareParseResult);

        //获取分片后的结果
        ShardingRouteResult shardingRouteResult = tableRouteEngine.route(new TableRouteContext(dataSourceRouteResult, prepareParseResult));
//        tableRouteEngine.route()
        return new RouteContext(shardingRouteResult);
    }
}
