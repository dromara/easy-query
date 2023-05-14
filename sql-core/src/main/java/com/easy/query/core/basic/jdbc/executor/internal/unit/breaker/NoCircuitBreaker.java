package com.easy.query.core.basic.jdbc.executor.internal.unit.breaker;

import com.easy.query.core.sharding.context.StreamMergeContext;

import java.util.Collection;

/**
 * create time 2023/5/8 23:05
 * 文件说明
 *
 * @author xuejiaming
 */
public final class NoCircuitBreaker implements CircuitBreaker{
    public static final CircuitBreaker INSTANCE=new NoCircuitBreaker();
    @Override
    public <TResult> boolean terminated(StreamMergeContext streamMergeContext, Collection<TResult> results) {
        return false;
    }
}
