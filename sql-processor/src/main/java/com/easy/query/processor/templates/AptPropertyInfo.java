package com.easy.query.processor.templates;

import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/11/8 16:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptPropertyInfo{
    /**
     * 属性名
     */
    private final String propertyName;
    /**
     * 属性类型
     */
    private final PropertyColumn propertyColumn;
    /**
     * 注释内容
     */
    private final String comment;
    /**
     * 对象名
     */
    private final String entityName;
    private final boolean valueObject;
    private final String sqlColumn;
    private final String sqlColumnMethod;
    private final String proxyPropertyName;

    public AptPropertyInfo(String propertyName, PropertyColumn propertyColumn, String comment, String entityName,boolean valueObject,boolean includeProperty,String proxyPropertyName){

        this.propertyName = propertyName;
        this.propertyColumn = propertyColumn;
        this.comment = comment;
        this.entityName = entityName;
        this.valueObject = valueObject;
        this.sqlColumn = includeProperty?"SQLNavigateColumn":propertyColumn.getSqlColumnName();
        this.sqlColumnMethod = includeProperty?"getNavigate":propertyColumn.getSQLColumnMethod();
        this.proxyPropertyName = proxyPropertyName;
    }

    public String getProxyPropertyName() {
        if(EasyStringUtil.isNotBlank(proxyPropertyName)){
            return proxyPropertyName;
        }
        return propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getComment() {
        return comment;
    }

    public String getPropertyType() {
        return propertyColumn.getPropertyType();
    }

    public String getEntityName() {
        return entityName;
    }

    public boolean isValueObject() {
        return valueObject;
    }

    public String getSqlColumn() {
        return sqlColumn;
    }

    public String getSqlColumnMethod() {
        return sqlColumnMethod;
    }
}
