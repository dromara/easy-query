package com.easy.query.core.exception;

/**
 * create time 2025/3/7 09:43
 * 文件说明
 */
public class EasyQueryPropertyNotFoundException extends EasyQueryException {
    private Class<?> entityClass;
    private final String propertyName;

    public EasyQueryPropertyNotFoundException(Class<?> entityClass,String propertyName, String msg) {
        this(entityClass,propertyName,msg,null);
    }

    public EasyQueryPropertyNotFoundException(Class<?> entityClass,String propertyName, Throwable e) {
        this(entityClass,propertyName,null,e);
    }

    public EasyQueryPropertyNotFoundException(Class<?> entityClass,String propertyName, String msg, Throwable e) {
        super(msg, e);
        this.entityClass = entityClass;
        this.propertyName = propertyName;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
