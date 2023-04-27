package com.easy.query.core.basic.jdbc.executor.internal.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.sharding.merge.StreamMergeContext;

/**
 * create time 2023/4/26 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractAffectedRowsEasyQueryJdbcExecutor extends AbstractEasyQueryJdbcExecutor<AffectedRowsExecuteResult> {
    public AbstractAffectedRowsEasyQueryJdbcExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected AffectedRowsExecuteResult defaultResult() {
        return AffectedRowsExecuteResult.empty();
    }
}
