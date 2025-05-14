package com.easy.query.core.exception;


/**
 * @FileName: EasyQueryInvalidOperationException.java
 * @Description: 文件说明
 * create time 2023/3/21 13:19
 * @author xuejiaming
 */
public class EasyQueryInvalidFieldCheckException extends EasyQueryException{
    public EasyQueryInvalidFieldCheckException(String msg) {
        super(msg);
    }

    public EasyQueryInvalidFieldCheckException(Throwable e) {
        super(e);
    }

    public EasyQueryInvalidFieldCheckException(String msg, Throwable e) {
        super(msg, e);
    }
}
