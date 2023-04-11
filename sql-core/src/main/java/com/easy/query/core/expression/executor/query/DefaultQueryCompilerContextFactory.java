package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.sharding.route.datasource.DataSourceRouteResult;
import com.easy.query.core.sharding.route.table.engine.TableRouteEngine;
import com.easy.query.core.sharding.route.table.engine.TableRouteEngineFactory;

/**
 * create time 2023/4/11 12:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultQueryCompilerContextFactory implements QueryCompilerContextFactory{
    private final TableRouteEngine tableRouteEngine;

    public DefaultQueryCompilerContextFactory(TableRouteEngine tableRouteEngine){

        this.tableRouteEngine = tableRouteEngine;
    }
    @Override
    public QueryCompilerContext create(PrepareParseResult prepareParseResult) {
        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        if(!nativeSqlQueryCompilerContext.isShardingQuery()){
            return nativeSqlQueryCompilerContext;
        }
//        tableRouteEngine.route()

        return null;
    }
}
