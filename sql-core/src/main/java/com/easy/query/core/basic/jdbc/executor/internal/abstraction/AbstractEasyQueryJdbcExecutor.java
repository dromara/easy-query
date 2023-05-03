package com.easy.query.core.basic.jdbc.executor.internal.abstraction;

import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.executor.ShardingExecutor;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.Collection;

/**
 * create time 2023/4/21 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEasyQueryJdbcExecutor<T extends ExecuteResult> implements EasyQueryJdbcExecutor<T> {
    protected final StreamMergeContext streamMergeContext;

    public AbstractEasyQueryJdbcExecutor(StreamMergeContext streamMergeContext){

        this.streamMergeContext = streamMergeContext;
    }
    protected  abstract Executor<T> createExecutor();
    protected Collection<ExecutionUnit> getDefaultSSqlRouteUnits(){
        return streamMergeContext.getExecutionUnits();
    }
    @Override
    public T execute() throws SQLException {
        Collection<ExecutionUnit> executionUnits = getDefaultSSqlRouteUnits();
        if(EasyCollectionUtil.isEmpty(executionUnits)){
            return defaultResult();
        }
        Executor<T> executor = createExecutor();
        return ShardingExecutor.execute(streamMergeContext,executor,executionUnits);
    }
    protected abstract T defaultResult();

    @Override
    public void close() throws Exception {
        streamMergeContext.close();
    }
}
