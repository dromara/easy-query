package com.easy.query.core.sharding.merge.executor.merger;

import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;
import com.easy.query.core.sharding.merge.executor.internal.ShardingMerger;
import com.easy.query.core.sharding.merge.impl.MultiOrderStreamMergeResult;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/20 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamShardingMerger implements ShardingMerger<StreamResult> {
    private static final DefaultStreamShardingMerger instance=new DefaultStreamShardingMerger();
    public static DefaultStreamShardingMerger getInstance(){
        return instance;
    }

    @Override
    public StreamResult streamMerge(StreamMergeContext streamMergeContext, Collection<StreamResult> parallelResults) {
        if (Objects.equals(CommandTypeEnum.QUERY,streamMergeContext.getCommandType())) {
            return new MultiOrderStreamMergeResult(streamMergeContext, parallelResults);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void inMemoryMerge(StreamMergeContext streamMergeContext, Collection<StreamResult> beforeInMemoryResults, Collection<StreamResult> parallelResults) {
        beforeInMemoryResults.addAll(parallelResults);
    }
}
