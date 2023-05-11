package com.easy.query.core.basic.jdbc.executor.internal.command.impl;

import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractQueryJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.result.impl.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractJdbcCommand;
import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyQueryExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;

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

}
