package com.easy.query.core.basic.jdbc.executor.internal.command.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.result.AffectedRowsExecuteResult;
import com.easy.query.core.sharding.context.StreamMergeContext;

/**
 * create time 2023/4/26 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractAffectedRowsJdbcCommand extends AbstractJdbcCommand<AffectedRowsExecuteResult> {
    public AbstractAffectedRowsJdbcCommand(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected AffectedRowsExecuteResult defaultResult() {
        return AffectedRowsExecuteResult.empty();
    }
}
