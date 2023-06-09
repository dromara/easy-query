package com.easy.query.core.exception;

/**
 * create time 2023/6/9 09:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryNoPrimaryKeyException extends EasyQueryException {
    public EasyQueryNoPrimaryKeyException(String msg) {
        super(msg);
    }

    public EasyQueryNoPrimaryKeyException(Throwable e) {
        super(e);
    }

    public EasyQueryNoPrimaryKeyException(String msg, Throwable e) {
        super(msg, e);
    }
}
