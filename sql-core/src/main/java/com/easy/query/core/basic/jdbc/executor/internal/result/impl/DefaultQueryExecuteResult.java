package com.easy.query.core.basic.jdbc.executor.internal.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.impl.EmptyStreamResult;

/**
 * create time 2023/5/12 23:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultQueryExecuteResult implements QueryExecuteResult {
    public static final QueryExecuteResult EMPTY=new DefaultQueryExecuteResult(EmptyStreamResult.getInstance());
    private final StreamResultSet streamResult;

    public DefaultQueryExecuteResult(StreamResultSet streamResult){
        this.streamResult = streamResult;
    }

    @Override
    public StreamResultSet getStreamResult() {
        return streamResult;
    }

    @Override
    public void close() throws Exception {
        streamResult.close();
    }
}
