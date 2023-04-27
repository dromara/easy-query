package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.basic.jdbc.executor.internal.abstraction.AbstractAffectedRowsEasyQueryJdbcExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.EasyExecuteUpdateExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.sharding.merge.StreamMergeContext;

/**
 * create time 2023/4/21 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultExecuteUpdateEasyQueryJdbcExecutor extends AbstractAffectedRowsEasyQueryJdbcExecutor {
    public DefaultExecuteUpdateEasyQueryJdbcExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<AffectedRowsExecuteResult> createExecutor() {
        return new EasyExecuteUpdateExecutor(streamMergeContext);
    }

}
