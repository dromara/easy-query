package com.easy.query.core.basic.jdbc.executor.internal.unit.breaker;

import com.easy.query.core.sharding.merge.context.StreamMergeContext;

import java.util.Collection;

/**
 * create time 2023/5/8 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CircuitBreaker {
   <TResult> boolean terminated(StreamMergeContext streamMergeContext, Collection<TResult> results);
}
