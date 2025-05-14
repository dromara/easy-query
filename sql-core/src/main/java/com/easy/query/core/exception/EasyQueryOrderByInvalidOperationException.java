package com.easy.query.core.exception;

/**
 * @FileName: EasyQueryOrderByNoPropertyExcpetion.java
 * @Description: 文件说明
 * create time 2023/3/23 21:28
 * @author xuejiaming
 */
public class EasyQueryOrderByInvalidOperationException extends EasyQueryException {
    private final String propertyName;

    public EasyQueryOrderByInvalidOperationException(String propertyName, String msg) {
        this(propertyName,msg,null);
    }

    public EasyQueryOrderByInvalidOperationException(String propertyName, Throwable e) {
        this(propertyName,null,e);
    }

    public EasyQueryOrderByInvalidOperationException(String propertyName, String msg, Throwable e) {
        super(msg, e);
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
