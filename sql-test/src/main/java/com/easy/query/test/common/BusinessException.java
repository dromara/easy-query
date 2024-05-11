package com.easy.query.test.common;

/**
 * create time 2024/5/9 23:24
 * 文件说明
 *
 * @author xuejiaming
 */

public class BusinessException extends RuntimeException {
    private final String code;
    private final Object data;

    public BusinessException(String message) {
        this("-1", message);
    }

    public BusinessException(String code, String message) {
        this(code, message, null);
    }

    public BusinessException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
