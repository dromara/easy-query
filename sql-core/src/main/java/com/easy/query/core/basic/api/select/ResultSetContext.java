package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

/**
 * create time 2025/10/23 21:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class ResultSetContext {
    private final ExecutorContext context;
    private final ResultMetadata<?> resultMetadata;
    private final StreamResultSet streamResultSet;

    public ResultSetContext(ExecutorContext context, ResultMetadata<?> resultMetadata, StreamResultSet streamResultSet){
        this.context = context;
        this.resultMetadata = resultMetadata;
        this.streamResultSet = streamResultSet;
    }

    public ExecutorContext getContext() {
        return context;
    }

    public ResultMetadata<?> getResultMetadata() {
        return resultMetadata;
    }

    public StreamResultSet getStreamResultSet() {
        return streamResultSet;
    }
}
