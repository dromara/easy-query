package com.easy.query.core.sharding.merge.executor.merger;

import com.easy.query.core.basic.jdbc.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.unit.ShardingMerger;
import com.easy.query.core.sharding.merge.impl.MultiOrderStreamMergeResult;
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
        List<StreamResult> streamResults = ArrayUtil.select(parallelResults, (o, i) -> ((QueryExecuteResult) o).getStreamResult());
        MultiOrderStreamMergeResult multiOrderStreamMergeResult = new MultiOrderStreamMergeResult(streamMergeContext, streamResults);
        return new QueryExecuteResult(multiOrderStreamMergeResult);
    }
}
