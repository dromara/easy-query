package com.easy.query.core.sharding.merge.executor.merger;

import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.QueryExecuteResult;
import com.easy.query.core.sharding.merge.result.impl.EasyMultiOrderStreamMergeResult;
import com.easy.query.core.util.ArrayUtil;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/20 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryStreamShardingMerger extends AbstractShardingMerger<QueryExecuteResult> {
    private static final QueryStreamShardingMerger instance=new QueryStreamShardingMerger();
    public static QueryStreamShardingMerger getInstance(){
        return instance;
    }

    @Override
    public QueryExecuteResult streamMerge(StreamMergeContext streamMergeContext, Collection<QueryExecuteResult> parallelResults) {
        List<StreamResult> streamResults = ArrayUtil.select(parallelResults, (o, i) -> o.getStreamResult());
        if(streamMergeContext.groupQueryMemoryMerge()){
            throw new UnsupportedOperationException();
        }
        if(streamMergeContext.isPaginationQuery()){
            throw new UnsupportedOperationException();
        }
        if(streamMergeContext.hasGroupQuery()){
            throw new UnsupportedOperationException();
        }

        EasyMultiOrderStreamMergeResult multiOrderStreamMergeResult = new EasyMultiOrderStreamMergeResult(streamMergeContext, streamResults);
        return new QueryExecuteResult(multiOrderStreamMergeResult);
    }
}
