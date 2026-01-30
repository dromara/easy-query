package com.easy.query.core.basic.extension.logicdel;

/**
 * create time 2023/3/28 09:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogicDeleteBuilder implements LogicDeleteMetadataBuilder {
    private final Class<?> entityClass;
    private final String propertyName;
    private final Class<?> propertyType;

    public LogicDeleteBuilder(Class<?> entityClass, String propertyName, Class<?> propertyType){
        this.entityClass = entityClass;

        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }
    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
