package com.easy.query.core.exception;


/**
 * @FileName: EasyQueryInvalidOperationException.java
 * @Description: 文件说明
 * @Date: 2023/3/21 13:19
 * @author xuejiaming
 */
public class EasyQueryInvalidOperationException extends EasyQueryException{
    public EasyQueryInvalidOperationException(String msg) {
        super(msg);
    }

    public EasyQueryInvalidOperationException(Throwable e) {
        super(e);
    }

    public EasyQueryInvalidOperationException(String msg, Throwable e) {
        super(msg, e);
    }
}
