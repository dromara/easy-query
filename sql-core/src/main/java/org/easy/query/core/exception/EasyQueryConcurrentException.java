package org.easy.query.core.exception;

/**
 * @FileName: EasyQueryConcurrentException.java
 * @Description: 文件说明
 * @Date: 2023/3/7 12:38
 * @Created by xuejiaming
 */
public class EasyQueryConcurrentException extends EasyQueryException{
    public EasyQueryConcurrentException(int code) {
        super(code);
    }

    public EasyQueryConcurrentException(int code, Throwable e) {
        super(code, e);
    }

    public EasyQueryConcurrentException(int code, String msg, Throwable e) {
        super(code, msg, e);
    }

    public EasyQueryConcurrentException(String msg, Throwable e) {
        super(msg, e);
    }

    public EasyQueryConcurrentException(int code, String msg) {
        super(code, msg);
    }

    public EasyQueryConcurrentException(String msg) {
        super(msg);
    }

    public EasyQueryConcurrentException(Throwable e) {
        super(e);
    }
}
