package com.easy.query.core.exception;

/**
 * create time 2023/6/9 09:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryMultiPrimaryKeyException extends EasyQueryException {
    public EasyQueryMultiPrimaryKeyException(String msg) {
        super(msg);
    }

    public EasyQueryMultiPrimaryKeyException(Throwable e) {
        super(e);
    }

    public EasyQueryMultiPrimaryKeyException(String msg, Throwable e) {
        super(msg, e);
    }
}
