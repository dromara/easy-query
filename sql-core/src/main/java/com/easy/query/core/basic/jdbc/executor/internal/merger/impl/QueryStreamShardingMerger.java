package com.easy.query.core.basic.jdbc.executor.internal.merger.impl;

import com.easy.query.core.basic.jdbc.executor.internal.merger.abstraction.AbstractShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.result.impl.QueryExecuteResult;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.impl.EasyInMemoryGroupByOrderStreamMergeResultSet;
import com.easy.query.core.sharding.merge.result.impl.EasyInMemoryStreamMergeResultSet;
import com.easy.query.core.sharding.merge.result.impl.EasyGroupByOrderStreamMergeResultSet;
import com.easy.query.core.sharding.merge.result.impl.EasyMultiOrderStreamMergeResult;
import com.easy.query.core.sharding.merge.result.impl.EasyPaginationStreamMergeResultSet;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    protected StreamResultSet streamMerge0(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResults) throws SQLException {
        if(streamMergeContext.groupWithInMemoryMerge()){
            EasyInMemoryGroupByOrderStreamMergeResultSet easyInMemoryGroupByOrderStreamMergeResultSet = new EasyInMemoryGroupByOrderStreamMergeResultSet(streamMergeContext, streamResults);
            if(streamMergeContext.isPaginationQuery()){
                return new EasyPaginationStreamMergeResultSet(streamMergeContext, Collections.singletonList(easyInMemoryGroupByOrderStreamMergeResultSet));
            }
            return easyInMemoryGroupByOrderStreamMergeResultSet;
        }
        if(streamMergeContext.isPaginationQuery()){
            return new EasyPaginationStreamMergeResultSet(streamMergeContext, streamResults);
        }
        if(streamMergeContext.hasGroupQuery()){
            return new EasyGroupByOrderStreamMergeResultSet(streamMergeContext, streamResults);
        }

        return new EasyMultiOrderStreamMergeResult(streamMergeContext, streamResults);
    }

    @Override
    public QueryExecuteResult streamMerge(StreamMergeContext streamMergeContext, Collection<QueryExecuteResult> parallelResults) throws SQLException {

        /**
         * 如果当前不是分片那么也需要聚合操作,哪怕是单个的因为分页表达式会重写
         */
        if(EasyCollectionUtil.isSingle(parallelResults)&&!streamMergeContext.isSharding()){
            return EasyCollectionUtil.firstOrNull(parallelResults);
        }
        List<StreamResultSet> streamResults = EasyCollectionUtil.select(parallelResults, (o, i) -> o.getStreamResult());
        StreamResultSet streamResultSet = streamMerge0(streamMergeContext, streamResults);
        return new QueryExecuteResult(streamResultSet);
    }
    protected StreamResultSet streamInMemoryMerge(StreamMergeContext streamMergeContext, List<StreamResultSet> parallelResults) throws SQLException {
        if(streamMergeContext.groupWithInMemoryMerge()){
            return new EasyGroupByOrderStreamMergeResultSet(streamMergeContext,parallelResults);
        }
        if(streamMergeContext.isPaginationQuery()){

            return new EasyPaginationStreamMergeResultSet(streamMergeContext,parallelResults,streamMergeContext.getRewriteOffset(),streamMergeContext.getRewriteRows());
        }
        return streamMerge0(streamMergeContext,parallelResults);
    }

    @Override
    public void inMemoryMerge(StreamMergeContext streamMergeContext, Collection<QueryExecuteResult> beforeInMemoryResults, Collection<QueryExecuteResult> parallelResults) throws SQLException {
        int previewResultsSize = beforeInMemoryResults.size();
        if(previewResultsSize>1){
            throw new EasyQueryInvalidOperationException("in memory merge has more element in results");
        }
        if(EasyCollectionUtil.isEmpty(parallelResults)){
            return;
        }
        ArrayList<StreamResultSet> mergeList = new ArrayList<>(parallelResults.size()+previewResultsSize);
        if(previewResultsSize==1){
            mergeList.add(EasyCollectionUtil.firstOrNull(beforeInMemoryResults).getStreamResult());
        }
        for (QueryExecuteResult parallelResult : parallelResults) {
            mergeList.add(parallelResult.getStreamResult());
        }
        StreamResultSet combineStreamMergeResultSet = streamInMemoryMerge(streamMergeContext, mergeList);
        EasyInMemoryStreamMergeResultSet easyInMemoryStreamMergeResultSet = new EasyInMemoryStreamMergeResultSet(streamMergeContext, Collections.singletonList(combineStreamMergeResultSet));
        beforeInMemoryResults.clear();
        beforeInMemoryResults.add(new QueryExecuteResult(easyInMemoryStreamMergeResultSet));
    }
}
