package com.easy.query.core.basic.jdbc.executor.query;

import com.easy.query.core.sharding.common.RouteUnit;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.executor.ShardingExecutor;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.internal.Executor;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/16 23:01
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractStreamIterable implements StreamIterable {
    private final StreamMergeContext streamMergeContext;

    public AbstractStreamIterable(StreamMergeContext streamMergeContext){

        this.streamMergeContext = streamMergeContext;
    }
    protected  abstract Executor<StreamResult> createExecutor();
    protected Collection<ExecutionUnit> getDefaultSSqlRouteUnits(){
        return streamMergeContext.getExecutionUnits();
    }

    public StreamMergeContext getStreamMergeContext() {
        return streamMergeContext;
    }

    @Override
    public StreamResult getStreamResult() {
        Executor<StreamResult> executor = createExecutor();
        Collection<ExecutionUnit> executionUnits = getDefaultSSqlRouteUnits();
        return ShardingExecutor.execute(streamMergeContext,executor,executionUnits.stream());
    }

    @Override
    public void close() throws Exception {
        streamMergeContext.close();
    }
}
