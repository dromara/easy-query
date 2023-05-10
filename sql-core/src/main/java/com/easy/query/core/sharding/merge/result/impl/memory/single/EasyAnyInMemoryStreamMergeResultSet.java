package com.easy.query.core.sharding.merge.result.impl.memory.single;

import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.StreamResultSet;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/5/10 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyAnyInMemoryStreamMergeResultSet extends AbstractSingleInMemoryStreamMergeResultSet{
    public EasyAnyInMemoryStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        super(streamMergeContext, streamResultSets);
    }

    @Override
    protected Object resultValue() {
        return 1L;
    }

    @Override
    protected Object defaultValue() {
        return 0L;
    }
}
