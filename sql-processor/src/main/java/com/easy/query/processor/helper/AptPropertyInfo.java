package com.easy.query.processor.helper;

import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.processor.FieldComment;

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
    private final FieldComment fieldComment;
    /**
     * 对象名
     */
    private final String entityName;
    private final boolean valueObject;
    private final boolean includeProperty;
    private final boolean includeManyProperty;
    private final String sqlColumn;
    private final String sqlColumnMethod;
    private final String proxyPropertyName;

    public AptPropertyInfo(String propertyName, PropertyColumn propertyColumn, FieldComment fieldComment, String entityName, boolean valueObject, boolean includeProperty, boolean includeManyProperty, String proxyPropertyName){

        this.propertyName = propertyName;
        this.propertyColumn = propertyColumn;
        this.fieldComment = fieldComment;
        this.entityName = entityName;
        this.valueObject = valueObject;
        this.includeProperty = includeProperty;
        this.includeManyProperty = includeManyProperty;
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
        return this.fieldComment.proxyComment;
    }
    public String getEntityComment() {
        return this.fieldComment.entityComment;
    }

    public String getPropertyType() {
        return propertyColumn.getPropertyType();
    }
    public boolean isAnyType(){
        return propertyColumn.isAnyType();
    }
    public String getPropertyTypeClass() {
        return propertyColumn.getPropertyTypeClass(includeProperty);
    }

    public String getPropertyShortType() {
        String propertyType = propertyColumn.getPropertyType();
        if(propertyType.contains(".")){
            return propertyType.substring(propertyType.lastIndexOf(".")+1);
        }
        return propertyType;
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

    public boolean isIncludeProperty() {
        return includeProperty;
    }

    public String getNavigateProxyName(){
        return propertyColumn.getNavigateProxyName();
    }

    public boolean isIncludeManyProperty() {
        return includeManyProperty;
    }
}
