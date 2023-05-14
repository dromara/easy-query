package com.easy.query.core.basic.jdbc.executor.internal.command.impl;

import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractQueryJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyReverseQueryExecutor;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyQueryExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.sharding.manager.QueryCountResult;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.ShardingUtil;

import java.util.ArrayList;
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
        if (streamMergeContext.isSharding()) {
            if (streamMergeContext.hasBehavior(MergeBehaviorEnum.SEQUENCE_PAGINATION)) {
                return new EasyQueryExecutor(streamMergeContext);
            }
            //反向排序
            if (streamMergeContext.hasBehavior(MergeBehaviorEnum.REVERSE_PAGINATION)) {
                return new EasyReverseQueryExecutor(streamMergeContext);
            }
        }
        return new EasyQueryExecutor(streamMergeContext);
    }

    @Override
    protected List<ExecutionUnit> getDefaultSqlRouteUnits() {
        List<ExecutionUnit> defaultSqlRouteUnits = super.getDefaultSqlRouteUnits();
        if (streamMergeContext.isSharding()) {
            if (streamMergeContext.hasBehavior(MergeBehaviorEnum.SEQUENCE_PAGINATION)) {
                ShardingQueryCountManager shardingQueryCountManager = streamMergeContext.getRuntimeContext().getShardingQueryCountManager();
                List<QueryCountResult> countResult = shardingQueryCountManager.getCountResult();
                long offset = streamMergeContext.getOffset();
                long rows = streamMergeContext.getRows();
                SequenceParseResult sequenceParseResult = streamMergeContext.getSequenceParseResult();
                return ShardingUtil.getSequencePaginationExecutionUnits(defaultSqlRouteUnits, countResult, sequenceParseResult, offset, rows);
            }
            if (streamMergeContext.hasBehavior(MergeBehaviorEnum.REVERSE_PAGINATION)){
                ShardingQueryCountManager shardingQueryCountManager = streamMergeContext.getRuntimeContext().getShardingQueryCountManager();
                List<QueryCountResult> countResult = shardingQueryCountManager.getCountResult();
                long total = EasyCollectionUtil.sumLong(countResult, QueryCountResult::getTotal);

                long offset = streamMergeContext.getOffset();
                long rows = streamMergeContext.getRows();
                long realSkip = total - rows - offset;
                streamMergeContext.useReverseMerge(true,realSkip);
                defaultSqlRouteUnits.forEach(o->{
                    EasyQuerySqlExpression easyEntitySqlExpression = (EasyQuerySqlExpression) o.getSqlRouteUnit().getEasyEntitySqlExpression();
                    easyEntitySqlExpression.setOffset(0);
                    easyEntitySqlExpression.setRows(realSkip+rows);
                    List<SqlSegment> sqlSegments = easyEntitySqlExpression.getOrder().cloneSqlBuilder().getSqlSegments();
                    easyEntitySqlExpression.getOrder().clear();
                    for (SqlSegment sqlSegment : sqlSegments) {
                        if(sqlSegment instanceof OrderByColumnSegment){
                            OrderByColumnSegment orderByColumnSegment = (OrderByColumnSegment) sqlSegment;
                            easyEntitySqlExpression.getOrder().append(new OrderColumnSegmentImpl(orderByColumnSegment.getTable(),orderByColumnSegment.getPropertyName(),streamMergeContext.getRuntimeContext(),!orderByColumnSegment.isAsc()));
                        }
                    }
                });
//                var noPaginationNoOrderQueryable = GetStreamMergeContext().GetOriginalQueryable().RemoveSkip().RemoveTake()
//                        .RemoveAnyOrderBy().As<IQueryable<TEntity>>();
//                var skip = GetStreamMergeContext().Skip.GetValueOrDefault();
//                var take = GetStreamMergeContext().Take.HasValue ? GetStreamMergeContext().Take.Value : (_total - skip);
//                if (take > int.MaxValue)
//                    throw new ShardingCoreException($"not support take more than {int.MaxValue}");
//                var realSkip = _total - take - skip;
//                GetStreamMergeContext().ReSetSkip((int)realSkip);
//                GetStreamMergeContext().ReverseOrder();
//                var reverseOrderQueryable = noPaginationNoOrderQueryable.Take((int)realSkip + (int)take)
//                        .OrderWithExpression(GetStreamMergeContext().Orders);
            }
        }
        return defaultSqlRouteUnits;
    }
}
