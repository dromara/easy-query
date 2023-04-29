package com.easy.query.core.exception;

/**
 * create time 2023/4/29 21:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryUnexpectedException extends EasyQueryException{
    public EasyQueryUnexpectedException(String msg) {
        super(msg);
    }

    public EasyQueryUnexpectedException(Throwable e) {
        super(e);
    }

    public EasyQueryUnexpectedException(String msg, Throwable e) {
        super(msg, e);
    }
}
