package com.easy.query.core.basic.jdbc.executor.internal.unit;

import com.easy.query.core.sharding.merge.StreamMergeContext;

import java.util.Collection;

/**
 * create time 2023/4/13 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ShardingMerger<TResult> {
    TResult streamMerge(StreamMergeContext streamMergeContext, Collection<TResult> parallelResults);
    void inMemoryMerge(StreamMergeContext streamMergeContext,Collection<TResult> beforeInMemoryResults, Collection<TResult> parallelResults);
}
