package com.easy.query.core.exception;

/**
 * create time 2023/5/25 14:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryMultiConnectionBusyException extends EasyQueryException{
    public EasyQueryMultiConnectionBusyException(String msg) {
        super(msg);
    }

    public EasyQueryMultiConnectionBusyException(Throwable e) {
        super(e);
    }

    public EasyQueryMultiConnectionBusyException(String msg, Throwable e) {
        super(msg, e);
    }
}
