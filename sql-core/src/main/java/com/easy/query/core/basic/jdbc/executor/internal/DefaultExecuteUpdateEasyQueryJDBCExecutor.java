package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.basic.jdbc.executor.internal.abstraction.AbstractAffectedRowsEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.abstraction.AbstractEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.EasyExecuteUpdateExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.sharding.merge.StreamMergeContext;

/**
 * create time 2023/4/21 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultExecuteUpdateEasyQueryJDBCExecutor extends AbstractAffectedRowsEasyQueryJDBCExecutor {
    public DefaultExecuteUpdateEasyQueryJDBCExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<AffectedRowsExecuteResult> createExecutor() {
        return new EasyExecuteUpdateExecutor(streamMergeContext);
    }

}
