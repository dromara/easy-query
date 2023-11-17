package com.easy.query.core.exception;

import com.easy.query.core.common.ErrorCode;

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
    public EasyQueryInvalidOperationException(ErrorCode errorCode, String msg) {
        super(errorCode.errorMsg(msg));
    }

    public EasyQueryInvalidOperationException(Throwable e) {
        super(e);
    }

    public EasyQueryInvalidOperationException(String msg, Throwable e) {
        super(msg, e);
    }
}
