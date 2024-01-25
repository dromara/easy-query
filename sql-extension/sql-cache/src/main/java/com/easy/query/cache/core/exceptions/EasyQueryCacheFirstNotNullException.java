package com.easy.query.cache.core.exceptions;

/**
 * create time 2024/1/25 13:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryCacheFirstNotNullException extends RuntimeException {
    private static final long serialVersionUID = 4426844319659828849L;

    private final String code;

    public EasyQueryCacheFirstNotNullException(String msg) {
        this(msg, null, null);
    }

    public EasyQueryCacheFirstNotNullException(String msg, String code) {
        this(msg, code, null);
    }

    public EasyQueryCacheFirstNotNullException(Throwable e) {
        this(null, null, e);
    }

    public EasyQueryCacheFirstNotNullException(String msg, String code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
