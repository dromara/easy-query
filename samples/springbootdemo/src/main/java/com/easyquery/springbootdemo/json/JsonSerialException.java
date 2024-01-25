package com.easyquery.springbootdemo.json;

/**
 * create time 2023/4/6 17:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class JsonSerialException extends RuntimeException{
    public JsonSerialException() {
    }

    public JsonSerialException(String message) {
        super(message);
    }

    public JsonSerialException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSerialException(Throwable cause) {
        super(cause);
    }

    public JsonSerialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
