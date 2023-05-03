package com.easy.query.core.basic.jdbc.executor.internal.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.result.ExecuteResult;
import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.sharding.merge.result.impl.EmptyStreamResult;

/**
 * create time 2023/4/14 16:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryExecuteResult implements ExecuteResult {
    private static final QueryExecuteResult instance=new QueryExecuteResult(EmptyStreamResult.getInstance());
    public static QueryExecuteResult empty(){
        return instance;
    }
    private final StreamResultSet streamResult;

    public QueryExecuteResult(StreamResultSet streamResult){
        this.streamResult = streamResult;
    }

    public StreamResultSet getStreamResult() {
        return streamResult;
    }

    @Override
    public void close() throws Exception {
        streamResult.close();
    }
}
