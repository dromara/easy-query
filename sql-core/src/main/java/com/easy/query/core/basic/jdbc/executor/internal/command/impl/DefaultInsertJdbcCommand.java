package com.easy.query.core.basic.jdbc.executor.internal.command.impl;

import com.easy.query.core.basic.jdbc.executor.internal.result.impl.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.command.abstraction.AbstractAffectedRowsJdbcCommand;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.impl.EasyInsertExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;

/**
 * create time 2023/4/21 16:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultInsertJdbcCommand extends AbstractAffectedRowsJdbcCommand {
    public DefaultInsertJdbcCommand(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<AffectedRowsExecuteResult> createExecutor() {
        return new EasyInsertExecutor(streamMergeContext);
    }

}
