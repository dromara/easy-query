package com.easy.query.core.expression.sql.include;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.IncludeNavigateParams;

import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/21 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeParseContext {
    private final IncludeNavigateParams includeNavigateParams;
    private String selfProperty;
    private String targetProperty;
    private String navigatePropertyName;
    private Class<?> navigateOriginalPropertyType;
    private Class<?> navigatePropertyType;
    private PropertySetterCaller<Object> navigatePropertySetter;

    private ClientQueryable<?> includeQueryable;
    private ClientQueryable<?> includeMappingQueryable;

    private List<Map<String, Object>> mappingRows;
    public IncludeParseContext(IncludeNavigateParams includeNavigateParams){

        this.includeNavigateParams = includeNavigateParams;
    }

    public String getSelfProperty() {
        return selfProperty;
    }

    public void setSelfProperty(String selfProperty) {
        this.selfProperty = selfProperty;
    }

    public String getNavigatePropertyName() {
        return navigatePropertyName;
    }

    public void setNavigatePropertyName(String navigatePropertyName) {
        this.navigatePropertyName = navigatePropertyName;
    }

    public ClientQueryable<?> getIncludeQueryable() {
        return includeQueryable;
    }

    public void setIncludeQueryable(ClientQueryable<?> includeQueryable) {
        this.includeQueryable = includeQueryable;
    }

    public ClientQueryable<?> getIncludeMappingQueryable() {
        return includeMappingQueryable;
    }

    public void setIncludeMappingQueryable(ClientQueryable<?> includeMappingQueryable) {
        this.includeMappingQueryable = includeMappingQueryable;
    }

    public List<Map<String, Object>> getMappingRows() {
        return mappingRows;
    }

    public void setMappingRows(List<Map<String, Object>> mappingRows) {
        this.mappingRows = mappingRows;
    }

    public IncludeNavigateParams getIncludeNavigateParams() {
        return includeNavigateParams;
    }

    public Class<?> getNavigateOriginalPropertyType() {
        return navigateOriginalPropertyType;
    }

    public void setNavigateOriginalPropertyType(Class<?> navigateOriginalPropertyType) {
        this.navigateOriginalPropertyType = navigateOriginalPropertyType;
    }

    public Class<?> getNavigatePropertyType() {
        return navigatePropertyType;
    }

    public void setNavigatePropertyType(Class<?> navigatePropertyType) {
        this.navigatePropertyType = navigatePropertyType;
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public void setTargetProperty(String targetProperty) {
        this.targetProperty = targetProperty;
    }

    public PropertySetterCaller<Object> getNavigatePropertySetter() {
        return navigatePropertySetter;
    }

    public void setNavigatePropertySetter(PropertySetterCaller<Object> navigatePropertySetter) {
        this.navigatePropertySetter = navigatePropertySetter;
    }
}
