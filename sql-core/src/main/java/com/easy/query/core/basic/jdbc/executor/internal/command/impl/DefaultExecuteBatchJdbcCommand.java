package com.easy.query.core.basic.jdbc.executor.internal.command.impl;

import com.easy.query.core.basic.jdbc.executor.internal.result.impl.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractAffectedRowsJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyExecuteBatchExecutor;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;

/**
 * create time 2023/4/21 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultExecuteBatchJdbcCommand extends AbstractAffectedRowsJdbcCommand {
    public DefaultExecuteBatchJdbcCommand(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<AffectedRowsExecuteResult> createExecutor() {
        return new EasyExecuteBatchExecutor(streamMergeContext);
    }

}
