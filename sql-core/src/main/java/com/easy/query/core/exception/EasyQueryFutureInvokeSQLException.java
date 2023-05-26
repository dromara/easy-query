package com.easy.query.core.exception;

import java.sql.SQLException;

/**
 * create time 2023/5/26 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryFutureInvokeSQLException extends SQLException {
    public EasyQueryFutureInvokeSQLException(String msg) {
        super(msg);
    }

    public EasyQueryFutureInvokeSQLException(Throwable e) {
        super(e);
    }

    public EasyQueryFutureInvokeSQLException(String msg, Throwable e) {
        super(msg, e);
    }
}
