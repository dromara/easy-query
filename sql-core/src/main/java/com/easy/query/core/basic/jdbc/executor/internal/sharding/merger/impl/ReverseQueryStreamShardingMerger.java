package com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.impl;

import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.EasyInMemoryReverseStreamMergeResultSet;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/5/14 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class ReverseQueryStreamShardingMerger extends QueryStreamShardingMerger{
    public static final ReverseQueryStreamShardingMerger INSTANCE = new ReverseQueryStreamShardingMerger();
    @Override
    protected StreamResultSet streamMergeToSingle(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResults) throws SQLException {
        StreamResultSet streamResultSet = super.streamMergeToSingle(streamMergeContext, streamResults);
        return new EasyInMemoryReverseStreamMergeResultSet(streamMergeContext,streamResultSet);
    }
}
