package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;

import java.sql.SQLException;

/**
 * create time 2023/7/31 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultJdbcStreamResultSet<T> implements JdbcStreamResult<T> {
    private final ExecutorContext executorContext;
    private final ResultMetadata<T> resultMetadata;
    private final JdbcCommand<QueryExecuteResult> command;

    public DefaultJdbcStreamResultSet(ExecutorContext executorContext, ResultMetadata<T> resultMetadata, JdbcCommand<QueryExecuteResult> command){
        this.executorContext = executorContext;
        this.resultMetadata = resultMetadata;
        this.command = command;
    }
    @Override
    public StreamIterable<T> getStreamIterable()  throws SQLException {
        return new DefaultStreamIterable<>(executorContext,resultMetadata,command.execute().getStreamResultSet());
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public void close() throws SQLException {
        command.close();
    }
}
