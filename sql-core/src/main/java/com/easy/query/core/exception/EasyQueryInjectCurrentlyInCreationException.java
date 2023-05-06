package com.easy.query.core.exception;

/**
 * create time 2023/5/6 10:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryInjectCurrentlyInCreationException extends EasyQueryException{
    public EasyQueryInjectCurrentlyInCreationException(String msg) {
        super(msg);
    }

    public EasyQueryInjectCurrentlyInCreationException(Throwable e) {
        super(e);
    }

    public EasyQueryInjectCurrentlyInCreationException(String msg, Throwable e) {
        super(msg, e);
    }
}
