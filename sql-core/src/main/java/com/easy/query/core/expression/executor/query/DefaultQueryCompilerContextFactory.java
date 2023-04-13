package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.sharding.route.ShardingRouteResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteContext;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteEngine;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.engine.TableRouteContext;
import com.easy.query.core.sharding.route.table.engine.TableRouteEngine;

/**
 * create time 2023/4/11 12:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultQueryCompilerContextFactory implements QueryCompilerContextFactory{
    private final DataSourceRouteEngine dataSourceRouteEngine;
    private final TableRouteEngine tableRouteEngine;

    public DefaultQueryCompilerContextFactory(DataSourceRouteEngine dataSourceRouteEngine, TableRouteEngine tableRouteEngine){
        this.dataSourceRouteEngine = dataSourceRouteEngine;

        this.tableRouteEngine = tableRouteEngine;
    }
    @Override
    public QueryCompilerContext create(PrepareParseResult prepareParseResult) {
        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        if(!nativeSqlQueryCompilerContext.isShardingQuery()){
            return nativeSqlQueryCompilerContext;
        }
        //获取分库节点
        DataSourceRouteResult dataSourceRouteResult = dataSourceRouteEngine.route(new DataSourceRouteContext(prepareParseResult.getShardingEntities(), prepareParseResult.getEntityExpression()));

        //获取分片后的结果
        ShardingRouteResult shardingRouteResult = tableRouteEngine.route(new TableRouteContext(dataSourceRouteResult, prepareParseResult.getEntityExpression(), prepareParseResult.getShardingEntities()));
//        tableRouteEngine.route()

        return new MergeSqlQueryCompilerContext(nativeSqlQueryCompilerContext,shardingRouteResult);
    }
}
