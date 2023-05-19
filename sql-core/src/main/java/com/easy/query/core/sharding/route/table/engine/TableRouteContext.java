package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;

/**
 * create time 2023/4/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TableRouteContext {
    private final DataSourceRouteResult dataSourceRouteResult;
    private final TableParseDescriptor tableParseDescriptor;
    private final SequenceParseResult sequenceParseResult;

    public TableRouteContext(DataSourceRouteResult dataSourceRouteResult, TableParseDescriptor tableParseDescriptor, SequenceParseResult sequenceParseResult){

        this.dataSourceRouteResult = dataSourceRouteResult;
        this.tableParseDescriptor = tableParseDescriptor;
        this.sequenceParseResult = sequenceParseResult;
    }

    public SequenceParseResult getSequenceParseResult() {
        return sequenceParseResult;
    }

    public DataSourceRouteResult getDataSourceRouteResult() {
        return dataSourceRouteResult;
    }

    public TableParseDescriptor getTableParseDescriptor() {
        return tableParseDescriptor;
    }

}
