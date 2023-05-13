package com.easy.query.core.basic.jdbc.executor.internal.command.impl;

import com.easy.query.core.basic.jdbc.executor.internal.result.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractAffectedRowsJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyExecuteUpdateExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.sharding.context.StreamMergeContext;

/**
 * create time 2023/4/21 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultExecuteUpdateJdbcCommand extends AbstractAffectedRowsJdbcCommand {
    public DefaultExecuteUpdateJdbcCommand(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<AffectedRowsExecuteResult> createExecutor() {
        return new EasyExecuteUpdateExecutor(streamMergeContext);
    }

}
