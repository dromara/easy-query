package com.easy.query.processor.templates;

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
     * 注释内容
     */
    private final String comment;
    /**
     * 属性类型
     */
    private final String propertyType;
    /**
     * 对象名
     */
    private final String entityName;
    private final boolean valueObject;
    private final String sqlColumn;
    private final String sqlColumnMethod;

    public AptPropertyInfo(String propertyName, String propertyType, String comment, String entityName,boolean valueObject,boolean includeProperty){

        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.comment = comment;
        this.entityName = entityName;
        this.valueObject = valueObject;
        this.sqlColumn = includeProperty?"SQLNavigateColumn":"SQLColumn";
        this.sqlColumnMethod = includeProperty?"getNavigate":"get";
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getComment() {
        return comment;
    }

    public String getPropertyType() {
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
}
