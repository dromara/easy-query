package com.easy.query.core.sharding.merge.executor.merger;

import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.ShardingMerger;

import java.sql.SQLException;
import java.util.Collection;

/**
 * create time 2023/4/20 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingMerger<T extends ExecuteResult> implements ShardingMerger<T> {

    @Override
    public void inMemoryMerge(StreamMergeContext streamMergeContext, Collection<T> beforeInMemoryResults, Collection<T> parallelResults) throws SQLException {
        beforeInMemoryResults.addAll(parallelResults);
    }
}
