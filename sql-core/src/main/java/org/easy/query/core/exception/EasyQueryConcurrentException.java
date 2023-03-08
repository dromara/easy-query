package org.easy.query.core.exception;

/**
 * @FileName: EasyQueryConcurrentException.java
 * @Description: 文件说明
 * @Date: 2023/3/7 12:38
 * @Created by xuejiaming
 */
public class EasyQueryConcurrentException extends EasyQueryException{
    private final String code;

    public EasyQueryConcurrentException(String msg) {
        this(msg, null, null);
    }

    public EasyQueryConcurrentException(String msg, String code) {
        this(msg, code, null);
    }

    public EasyQueryConcurrentException(Throwable e) {
        this(null, null, e);
    }

    public EasyQueryConcurrentException(String msg, String code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
