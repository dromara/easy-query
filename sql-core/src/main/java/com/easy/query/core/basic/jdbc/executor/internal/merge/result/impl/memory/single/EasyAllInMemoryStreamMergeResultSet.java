package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.single;

import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/5/10 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyAllInMemoryStreamMergeResultSet extends AbstractSingleInMemoryStreamMergeResultSet{

    public EasyAllInMemoryStreamMergeResultSet(StreamMergeContext streamMergeContext, List<StreamResultSet> streamResultSets) throws SQLException {
        super(streamMergeContext, streamResultSets);
    }

    @Override
    protected Object resultValue() {
        return 0L;
    }

    @Override
    protected Object defaultValue() {
        return 1L;
    }
}
