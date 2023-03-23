package com.easy.query.core.exception;

/**
 * @FileName: EasyQueryOrderByNoPropertyExcpetion.java
 * @Description: 文件说明
 * @Date: 2023/3/23 21:28
 * @Created by xuejiaming
 */
public class EasyQueryOrderByInvalidOperationException extends EasyQueryException {
    public EasyQueryOrderByInvalidOperationException(String propertyName, String msg) {
        this(propertyName,msg,null);
    }

    public EasyQueryOrderByInvalidOperationException(String propertyName, Throwable e) {
        this(propertyName,null,e);
    }

    public EasyQueryOrderByInvalidOperationException(String propertyName, String msg, Throwable e) {
        super(msg, e);
    }
}
