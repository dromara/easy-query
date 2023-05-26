package com.easy.query.core.exception;

import java.sql.SQLException;

/**
 * create time 2023/4/14 22:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryTimeoutSQLException extends SQLException {
    public EasyQueryTimeoutSQLException(String msg) {
        super(msg);
    }

    public EasyQueryTimeoutSQLException(Throwable e) {
        super(e);
    }

    public EasyQueryTimeoutSQLException(String msg, Throwable e) {
        super(msg, e);
    }
}
