package com.easy.query.core.basic.jdbc.executor.internal.unit.impl.breaker;

import com.easy.query.core.sharding.merge.context.StreamMergeContext;

import java.util.Collection;

/**
 * create time 2023/5/8 22:49
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractCircuitBreaker implements CircuitBreaker {
    protected volatile boolean terminated = false;
    protected final StreamMergeContext streamMergeContext;

    public AbstractCircuitBreaker(StreamMergeContext streamMergeContext) {

        this.streamMergeContext = streamMergeContext;
    }

    @Override
    public <TResult> boolean terminated(Collection<TResult> results) {
        if (terminated) {
            return true;
        }
        if (streamMergeContext.isSeqQuery()) {
            if (SequenceTerminated(results)) {
                terminatedBreak();
                return true;
            }
        } else {
            if (RandomTerminated(results)) {
                terminatedBreak();
                return true;
            }
        }
        return false;
    }

    protected abstract <TResult> boolean SequenceTerminated(Collection<TResult> results);

    protected abstract <TResult> boolean RandomTerminated(Collection<TResult> results);

    private void terminatedBreak() {
        terminated = true;
    }
}
