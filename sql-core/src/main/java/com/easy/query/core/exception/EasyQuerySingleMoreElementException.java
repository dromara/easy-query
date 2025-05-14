package com.easy.query.core.exception;

/**
 * @FileName: EasyQueryNotFoundException.java
 * @Description: 文件说明
 * create time 2023/3/7 12:38
 * @author xuejiaming
 */
public class EasyQuerySingleMoreElementException extends EasyQueryException {
    public EasyQuerySingleMoreElementException(String msg) {
        super(msg);
    }

    public EasyQuerySingleMoreElementException(Throwable e) {
        super(e);
    }

    public EasyQuerySingleMoreElementException(String msg, Throwable e) {
        super(msg, e);
    }
}
