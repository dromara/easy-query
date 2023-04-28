package com.easy.query.core.sharding.merge.executor.merger;

import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.QueryExecuteResult;
import com.easy.query.core.sharding.merge.result.impl.EasyMultiOrderStreamMergeResult;
import com.easy.query.core.sharding.merge.result.impl.EasyPaginationStreamMergeResult;
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

        /**
         * 如果当前不是分片那么也需要聚合操作,哪怕是单个的因为分页表达式会重写
         */
        if(ArrayUtil.isSingle(parallelResults)&&!streamMergeContext.isSharding()){
            return ArrayUtil.firstOrNull(parallelResults);
        }
        List<StreamResult> streamResults = ArrayUtil.select(parallelResults, (o, i) -> o.getStreamResult());
        if(streamMergeContext.groupQueryMemoryMerge()){
            throw new UnsupportedOperationException();
        }
        if(streamMergeContext.isPaginationQuery()){
            EasyPaginationStreamMergeResult easyPaginationStreamMergeResult = new EasyPaginationStreamMergeResult(streamMergeContext, streamResults);
            return new QueryExecuteResult(easyPaginationStreamMergeResult);
        }
        if(streamMergeContext.hasGroupQuery()){
            throw new UnsupportedOperationException();
        }

        EasyMultiOrderStreamMergeResult multiOrderStreamMergeResult = new EasyMultiOrderStreamMergeResult(streamMergeContext, streamResults);
        return new QueryExecuteResult(multiOrderStreamMergeResult);
    }
}
