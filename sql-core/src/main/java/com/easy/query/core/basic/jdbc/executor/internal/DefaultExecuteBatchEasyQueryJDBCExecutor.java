package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.basic.jdbc.executor.internal.abstraction.AbstractAffectedRowsEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.abstraction.AbstractEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.EasyExecuteBatchExecutor;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;

/**
 * create time 2023/4/21 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultExecuteBatchEasyQueryJDBCExecutor extends AbstractAffectedRowsEasyQueryJDBCExecutor {
    public DefaultExecuteBatchEasyQueryJDBCExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<ExecuteResult> createExecutor() {
        return new EasyExecuteBatchExecutor(streamMergeContext);
    }

}
