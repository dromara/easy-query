package com.easy.query.core.exception;

/**
 * create time 2023/9/19 08:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryTableNotInSQLContextException extends EasyQueryException{
    public EasyQueryTableNotInSQLContextException(String msg) {
        super(msg);
    }

    public EasyQueryTableNotInSQLContextException(Throwable e) {
        super(e);
    }

    public EasyQueryTableNotInSQLContextException(String msg, Throwable e) {
        super(msg, e);
    }
}
