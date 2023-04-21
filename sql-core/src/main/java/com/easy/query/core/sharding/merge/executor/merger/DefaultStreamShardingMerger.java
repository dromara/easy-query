package com.easy.query.core.sharding.merge.executor.merger;

import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;
import com.easy.query.core.sharding.merge.executor.internal.ExecuteResult;
import com.easy.query.core.sharding.merge.executor.internal.QueryExecuteResult;
import com.easy.query.core.sharding.merge.executor.internal.ShardingMerger;
import com.easy.query.core.sharding.merge.impl.MultiOrderStreamMergeResult;
import com.easy.query.core.util.ArrayUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/20 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamShardingMerger implements ShardingMerger<ExecuteResult> {
    private static final DefaultStreamShardingMerger instance=new DefaultStreamShardingMerger();
    public static DefaultStreamShardingMerger getInstance(){
        return instance;
    }

    @Override
    public ExecuteResult streamMerge(StreamMergeContext streamMergeContext, Collection<ExecuteResult> parallelResults) {
        ExecuteResult executeResult = parallelResults.iterator().next();
        if(executeResult instanceof QueryExecuteResult){
            List<StreamResult> streamResults = ArrayUtil.select(parallelResults, (o, i) -> ((QueryExecuteResult) o).getStreamResult());
            MultiOrderStreamMergeResult multiOrderStreamMergeResult = new MultiOrderStreamMergeResult(streamMergeContext, streamResults);
            return new QueryExecuteResult(multiOrderStreamMergeResult);

        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void inMemoryMerge(StreamMergeContext streamMergeContext, Collection<ExecuteResult> beforeInMemoryResults, Collection<ExecuteResult> parallelResults) {
        beforeInMemoryResults.addAll(parallelResults);
    }
}
