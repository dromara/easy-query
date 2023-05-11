package com.easy.query.core.exception;

/**
 * create time 2023/5/11 13:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryTrackInvalidOperationException extends EasyQueryInvalidOperationException{
    public EasyQueryTrackInvalidOperationException(String msg) {
        super(msg);
    }

    public EasyQueryTrackInvalidOperationException(Throwable e) {
        super(e);
    }

    public EasyQueryTrackInvalidOperationException(String msg, Throwable e) {
        super(msg, e);
    }
}
