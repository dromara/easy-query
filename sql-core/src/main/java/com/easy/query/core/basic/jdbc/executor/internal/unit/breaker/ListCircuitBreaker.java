package com.easy.query.core.basic.jdbc.executor.internal.unit.breaker;

import com.easy.query.core.basic.jdbc.executor.internal.result.impl.QueryExecuteResult;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.InMemoryStreamMergeResultSet;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;

/**
 * create time 2023/5/8 23:01
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ListCircuitBreaker extends AbstractCircuitBreaker{

    private static final CircuitBreaker instance=new ListCircuitBreaker();
    public static CircuitBreaker getInstance(){
        return instance;
    }
    @Override
    protected <TResult> boolean SequenceTerminated(StreamMergeContext streamMergeContext,Collection<TResult> results) {
        if(streamMergeContext.isPaginationQuery()){
            long rows = streamMergeContext.getRewriteRows();
            if(rows>0){
                long offset = streamMergeContext.getRewriteOffset();
                int reallyCount = EasyCollectionUtil.sum(results, element -> {
                    if(element instanceof QueryExecuteResult){
                        QueryExecuteResult queryExecuteResult = (QueryExecuteResult) element;
                        StreamResultSet streamResult = queryExecuteResult.getStreamResult();
                        if (streamResult instanceof InMemoryStreamMergeResultSet) {
                            return ((InMemoryStreamMergeResultSet)streamResult).getReallyCount();
                        }
                    }
                    return 0;
                });
                return (offset+rows)<=reallyCount;
            }
        }
        return false;
    }

    @Override
    protected <TResult> boolean RandomTerminated(StreamMergeContext streamMergeContext,Collection<TResult> results) {
        return false;
    }
}
