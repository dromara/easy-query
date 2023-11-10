package com.easy.query.core.basic.extension.listener;

/**
 * create time 2023/11/10 23:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class JdbcListenAfterArg {
    private final JdbcListenBeforeArg beforeArg;
    private final int rows;
    private final Exception exception;
    private final long end;

    public JdbcListenAfterArg(JdbcListenBeforeArg beforeArg, int rows, Exception exception){

        this.beforeArg = beforeArg;
        this.rows = rows;
        this.exception = exception;
        this.end = System.currentTimeMillis();
    }

    public JdbcListenBeforeArg getBeforeArg() {
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
