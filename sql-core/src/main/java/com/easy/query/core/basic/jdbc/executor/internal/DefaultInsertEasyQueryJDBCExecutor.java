package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.basic.jdbc.executor.internal.abstraction.AbstractEasyQueryJDBCExecutor;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.EasyInsertExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;

/**
 * create time 2023/4/21 16:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultInsertEasyQueryJDBCExecutor extends AbstractEasyQueryJDBCExecutor {
    public DefaultInsertEasyQueryJDBCExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<ExecuteResult> createExecutor() {
        return new EasyInsertExecutor(streamMergeContext);
    }
}
