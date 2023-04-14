package com.easy.query.core.sharding.merge.executor.internal;

import com.easy.query.core.sharding.merge.abstraction.StreamResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * create time 2023/4/14 16:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryExecuteResult implements ExecuteResult{
    private final StreamResult streamResult;
    private final PreparedStatement preparedStatement;

    public QueryExecuteResult(StreamResult streamResult, PreparedStatement preparedStatement){
        this.streamResult = streamResult;

        this.preparedStatement = preparedStatement;
    }

    public StreamResult getStreamResult() {
        return streamResult;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    @Override
    public void close() throws Exception {
        streamResult.close();
        preparedStatement.close();
    }
}
