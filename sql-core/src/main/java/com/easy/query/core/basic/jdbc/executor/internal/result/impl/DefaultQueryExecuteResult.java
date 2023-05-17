package com.easy.query.core.basic.jdbc.executor.internal.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.EmptyStreamResultSet;

/**
 * create time 2023/5/12 23:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultQueryExecuteResult implements QueryExecuteResult {
    public static final QueryExecuteResult EMPTY=new DefaultQueryExecuteResult(EmptyStreamResultSet.getInstance());
    private final StreamResultSet streamResultSet;

    public DefaultQueryExecuteResult(StreamResultSet streamResultSet){
        this.streamResultSet = streamResultSet;
    }

    @Override
    public StreamResultSet getStreamResultSet() {
        return streamResultSet;
    }

    @Override
    public void close() throws Exception {
        streamResultSet.close();
    }
}
