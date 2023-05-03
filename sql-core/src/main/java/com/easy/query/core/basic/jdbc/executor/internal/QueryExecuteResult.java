package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.sharding.merge.abstraction.StreamResultSet;
import com.easy.query.core.sharding.merge.result.impl.EmptyStreamResult;

import java.sql.PreparedStatement;

/**
 * create time 2023/4/14 16:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryExecuteResult implements ExecuteResult{
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
