package com.easy.query.core.basic.jdbc.executor.query;

import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.executor.internal.DefaultStreamExecutor;
import com.easy.query.core.sharding.merge.executor.internal.Executor;

/**
 * create time 2023/4/16 23:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamIterable extends AbstractStreamIterable{
    public DefaultStreamIterable(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<StreamResult> createExecutor() {
        return new DefaultStreamExecutor(getStreamMergeContext());
    }
}
