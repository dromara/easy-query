package com.easy.query.core.sharding.merge.executor.internal;

import com.easy.query.core.sharding.merge.StreamMergeContext;

import java.util.List;

/**
 * create time 2023/4/13 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ShardingMerger<TResult> {
    TResult streamMerge(StreamMergeContext streamMergeContext, List<TResult> parallelResults);
    void inMemoryMerge(StreamMergeContext streamMergeContext,List<TResult> beforeInMemoryResults, List<TResult> parallelResults);
}
