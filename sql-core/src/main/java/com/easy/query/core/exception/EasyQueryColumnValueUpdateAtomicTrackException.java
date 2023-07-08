package com.easy.query.core.exception;

/**
 * create time 2023/7/7 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryColumnValueUpdateAtomicTrackException extends EasyQueryException{
    public EasyQueryColumnValueUpdateAtomicTrackException(String msg) {
        super(msg);
    }

    public EasyQueryColumnValueUpdateAtomicTrackException(Throwable e) {
        super(e);
    }

    public EasyQueryColumnValueUpdateAtomicTrackException(String msg, Throwable e) {
        super(msg, e);
    }
}
