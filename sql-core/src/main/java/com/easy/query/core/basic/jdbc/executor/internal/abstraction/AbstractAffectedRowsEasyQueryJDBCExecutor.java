package com.easy.query.core.basic.jdbc.executor.internal.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;
import com.easy.query.core.sharding.merge.StreamMergeContext;

/**
 * create time 2023/4/26 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractAffectedRowsEasyQueryJDBCExecutor extends AbstractEasyQueryJDBCExecutor{
    public AbstractAffectedRowsEasyQueryJDBCExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected ExecuteResult defaultResult() {
        return AffectedRowsExecuteResult.empty();
    }
}
