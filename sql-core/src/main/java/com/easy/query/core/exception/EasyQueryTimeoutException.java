package com.easy.query.core.exception;

/**
 * create time 2023/4/14 22:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryTimeoutException extends EasyQueryException{
    public EasyQueryTimeoutException(String msg) {
        super(msg);
    }

    public EasyQueryTimeoutException(Throwable e) {
        super(e);
    }

    public EasyQueryTimeoutException(String msg, Throwable e) {
        super(msg, e);
    }
}
