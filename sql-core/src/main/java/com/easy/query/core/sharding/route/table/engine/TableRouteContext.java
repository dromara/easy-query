package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;

/**
 * create time 2023/4/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TableRouteContext {
    private final DataSourceRouteResult dataSourceRouteResult;
    private final PrepareParseResult prepareParseResult;

    public TableRouteContext(DataSourceRouteResult dataSourceRouteResult, PrepareParseResult prepareParseResult){

        this.dataSourceRouteResult = dataSourceRouteResult;
        this.prepareParseResult = prepareParseResult;
    }

    public DataSourceRouteResult getDataSourceRouteResult() {
        return dataSourceRouteResult;
    }

    public PrepareParseResult getPrepareParseResult() {
        return prepareParseResult;
    }
}
