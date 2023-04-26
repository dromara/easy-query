package com.easy.query.core.basic.jdbc.executor.internal.abstraction;

import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.ShardingExecutor;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.util.ArrayUtil;

import java.util.Collection;

/**
 * create time 2023/4/21 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEasyQueryJDBCExecutor implements EasyQueryJDBCExecutor{
    protected final StreamMergeContext streamMergeContext;

    public AbstractEasyQueryJDBCExecutor(StreamMergeContext streamMergeContext){

        this.streamMergeContext = streamMergeContext;
    }
    protected  abstract Executor<ExecuteResult> createExecutor();
    protected Collection<ExecutionUnit> getDefaultSSqlRouteUnits(){
        return streamMergeContext.getExecutionUnits();
    }
    @Override
    public ExecuteResult execute() {
        Collection<ExecutionUnit> executionUnits = getDefaultSSqlRouteUnits();
        if(ArrayUtil.isEmpty(executionUnits)){
            return defaultResult();
        }
        Executor<ExecuteResult> executor = createExecutor();
        return ShardingExecutor.execute(streamMergeContext,executor,executionUnits);
    }
    protected abstract ExecuteResult defaultResult();

    @Override
    public void close() throws Exception {
        streamMergeContext.close();
    }
}
