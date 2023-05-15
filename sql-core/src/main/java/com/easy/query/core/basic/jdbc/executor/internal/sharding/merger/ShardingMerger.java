package com.easy.query.core.basic.jdbc.executor.internal.sharding.merger;

import com.easy.query.core.sharding.context.StreamMergeContext;

import java.sql.SQLException;
import java.util.Collection;

/**
 * create time 2023/4/13 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ShardingMerger<TResult> {
    TResult streamMerge(StreamMergeContext streamMergeContext, Collection<TResult> parallelResults) throws SQLException;
    void inMemoryMerge(StreamMergeContext streamMergeContext,Collection<TResult> beforeInMemoryResults, Collection<TResult> parallelResults) throws SQLException;
}
