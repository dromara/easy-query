package com.easy.query.core.basic.jdbc.executor.internal.unit.impl;

import com.easy.query.core.basic.jdbc.executor.internal.merger.ShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.merger.impl.ReverseQueryStreamShardingMerger;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.sharding.context.StreamMergeContext;

/**
 * create time 2023/5/14 13:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyReverseQueryExecutor extends EasyQueryExecutor {
    public EasyReverseQueryExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    public ShardingMerger<QueryExecuteResult> getShardingMerger() {
        return ReverseQueryStreamShardingMerger.INSTANCE;
    }
}
