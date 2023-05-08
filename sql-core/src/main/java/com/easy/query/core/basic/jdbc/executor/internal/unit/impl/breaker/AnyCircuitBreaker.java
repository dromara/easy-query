package com.easy.query.core.basic.jdbc.executor.internal.unit.impl.breaker;

import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;

/**
 * create time 2023/5/8 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public final class AnyCircuitBreaker extends AbstractCircuitBreaker{
    public AnyCircuitBreaker(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected <TResult> boolean SequenceTerminated(Collection<TResult> results) {
        return EasyCollectionUtil.isNotEmpty(results);
    }

    @Override
    protected <TResult> boolean RandomTerminated(Collection<TResult> results) {
        return EasyCollectionUtil.isNotEmpty(results);
    }
}
