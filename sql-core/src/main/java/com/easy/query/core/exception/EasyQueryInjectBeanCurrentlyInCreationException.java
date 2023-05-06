package com.easy.query.core.exception;

/**
 * create time 2023/5/6 10:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryInjectBeanCurrentlyInCreationException extends EasyQueryException{
    public EasyQueryInjectBeanCurrentlyInCreationException(String msg) {
        super(msg);
    }

    public EasyQueryInjectBeanCurrentlyInCreationException(Throwable e) {
        super(e);
    }

    public EasyQueryInjectBeanCurrentlyInCreationException(String msg, Throwable e) {
        super(msg, e);
    }
}
