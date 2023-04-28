package com.easy.query.core.exception;

/**
 * create time 2023/4/28 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQuerySQLException extends EasyQueryException{
    public EasyQuerySQLException(String msg) {
        super(msg);
    }

    public EasyQuerySQLException(Throwable e) {
        super(e);
    }

    public EasyQuerySQLException(String msg, Throwable e) {
        super(msg, e);
    }
}
