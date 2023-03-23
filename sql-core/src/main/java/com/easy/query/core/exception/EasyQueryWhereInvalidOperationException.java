package com.easy.query.core.exception;

/**
 * @FileName: EasyQueryWhereInvalidOperationException.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:28
 * @Created by xuejiaming
 */
public class EasyQueryWhereInvalidOperationException extends EasyQueryException {
    public EasyQueryWhereInvalidOperationException(String msg) {
        super(msg);
    }

    public EasyQueryWhereInvalidOperationException(Throwable e) {
        super(e);
    }

    public EasyQueryWhereInvalidOperationException(String msg, Throwable e) {
        super(msg, e);
    }
}
