package com.easy.query.core.basic.extension.listener;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.expression.lambda.SQLActionExpression1;

/**
 * create time 2023/11/10 23:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class JdbcExecuteAfterArg {
    /**
     * 执行前的参数信息
     */
    private final JdbcExecuteBeforeArg beforeArg;
    private final StreamResultSet streamResultSet;
    /**
     * 受影响行数当为查询时为0
     */
    private final int rows;
    /**
     * 错误异常
     */
    private final Exception exception;
    /**
     * 执行结束时间毫秒
     */
    private final long end;

    public JdbcExecuteAfterArg(JdbcExecuteBeforeArg beforeArg, StreamResultSet streamResultSet, int rows, Exception exception) {

        this.beforeArg = beforeArg;
        this.streamResultSet = streamResultSet;
        this.rows = rows;
        this.exception = exception;
        this.end = System.currentTimeMillis();
    }

    public JdbcExecuteBeforeArg getBeforeArg() {
        return beforeArg;
    }

    public int getRows() {
        return rows;
    }

    public Exception getException() {
        return exception;
    }

    public long getEnd() {
        return end;
    }

    /**
     * 费查询模式为空
     * @return
     */
    public StreamResultSet getStreamResultSet() {
        return streamResultSet;
    }
}
