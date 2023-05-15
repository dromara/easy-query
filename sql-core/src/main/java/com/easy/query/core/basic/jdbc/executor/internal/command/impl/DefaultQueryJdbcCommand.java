package com.easy.query.core.basic.jdbc.executor.internal.command.impl;

import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractQueryJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyReverseQueryExecutor;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyQueryExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.sharding.manager.QueryCountResult;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.ShardingUtil;

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

}
