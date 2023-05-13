package com.easy.query.core.basic.jdbc.executor.internal.command.impl;

import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractQueryJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyQueryExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.sharding.manager.QueryCountResult;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.util.ShardingUtil;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/21 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultQueryJdbcCommand extends AbstractQueryJdbcCommand {
    public DefaultQueryJdbcCommand(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<QueryExecuteResult> createExecutor() {
        return new EasyQueryExecutor(streamMergeContext);
    }

    @Override
    protected Collection<ExecutionUnit> getDefaultSqlRouteUnits() {
        Collection<ExecutionUnit> defaultSqlRouteUnits = super.getDefaultSqlRouteUnits();
        if(streamMergeContext.isSharding()){
            if(streamMergeContext.hasBehavior(MergeBehaviorEnum.SEQUENCE_PAGINATION)){
                ShardingQueryCountManager shardingQueryCountManager = streamMergeContext.getRuntimeContext().getShardingQueryCountManager();
                List<QueryCountResult> countResult = shardingQueryCountManager.getCountResult();
                long offset = streamMergeContext.getOffset();
                long rows = streamMergeContext.getRows();
                SequenceParseResult sequenceParseResult = streamMergeContext.getSequenceParseResult();
                return ShardingUtil.getSequencePaginationExecutionUnits(defaultSqlRouteUnits, countResult,sequenceParseResult, offset, rows);
            }
            //反向排序
        }
        return defaultSqlRouteUnits;
    }
}
