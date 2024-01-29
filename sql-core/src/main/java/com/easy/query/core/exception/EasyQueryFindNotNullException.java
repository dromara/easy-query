package com.easy.query.core.exception;

/**
 * create time 2024/1/29 08:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryFindNotNullException extends EasyQueryException {

    private final String code;

    public EasyQueryFindNotNullException(String msg) {
        this(msg, null, null);
    }

    public EasyQueryFindNotNullException(String msg, String code) {
        this(msg, code, null);
    }

    public EasyQueryFindNotNullException(Throwable e) {
        this(null, null, e);
    }

    public EasyQueryFindNotNullException(String msg, String code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
