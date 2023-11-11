package com.easy.query.core.basic.extension.listener;

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

    public JdbcExecuteAfterArg(JdbcExecuteBeforeArg beforeArg, int rows, Exception exception){

        this.beforeArg = beforeArg;
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
}
