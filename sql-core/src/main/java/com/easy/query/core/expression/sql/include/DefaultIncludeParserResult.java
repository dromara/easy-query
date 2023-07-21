package com.easy.query.core.expression.sql.include;

import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/21 15:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultIncludeParserResult implements IncludeParserResult{
    private final EntityMetadata entityMetadata;
    private final RelationTypeEnum relationType;
    private final String navigatePropertyName;
    private final Class<?> navigateOriginalPropertyType;
    private final Class<?> navigatePropertyType;
    private final String selfProperty;
    private final String targetProperty;
    private final List<Object> includeResult;

    private Class<?> mappingClass;
    private String selfMappingProperty;
    private String targetMappingProperty;
    private List<Map<String, Object>> mappingRows;
    private final PropertySetterCaller<Object> setter;

    public DefaultIncludeParserResult(EntityMetadata entityMetadata,
                                      RelationTypeEnum relationType,
                                      String navigatePropertyName,
                                      Class<?> navigateOriginalPropertyType,
                                      Class<?> navigatePropertyType,
                                      String selfProperty,
                                      String targetProperty,
                                      Class<?> mappingClass,
                                      String selfMappingProperty,
                                      String targetMappingProperty,
                                      List<Object> includeResult,
                                      List<Map<String, Object>> mappingRows,
                                      PropertySetterCaller<Object> setter){

        this.entityMetadata = entityMetadata;
        this.relationType = relationType;
        this.navigatePropertyName = navigatePropertyName;
        this.navigateOriginalPropertyType = navigateOriginalPropertyType;
        this.navigatePropertyType = navigatePropertyType;
        this.selfProperty = selfProperty;
        this.targetProperty = targetProperty;
        this.mappingClass = mappingClass;
        this.selfMappingProperty = selfMappingProperty;
        this.targetMappingProperty = targetMappingProperty;
        this.includeResult = includeResult;
        this.mappingRows = mappingRows;
        this.setter = setter;
    }
    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public RelationTypeEnum getRelationType() {
        return relationType;
    }

    @Override
    public String getNavigatePropertyName() {
        return navigatePropertyName;
    }

    @Override
    public Class<?> getNavigateOriginalPropertyType() {
        return navigateOriginalPropertyType;
    }

    @Override
    public Class<?> getNavigatePropertyType() {
        return navigatePropertyType;
    }

    @Override
    public String getSelfProperty() {
        return selfProperty;
    }

    @Override
    public String getTargetProperty() {
        return targetProperty;
    }

    @Override
    public Class<?> getMappingClass() {
        return mappingClass;
    }

    @Override
    public String getSelfMappingProperty() {
        return selfMappingProperty;
    }

    @Override
    public String getTargetMappingProperty() {
        return targetMappingProperty;
    }

    @Override
    public List<Object> getIncludeResult() {
        return includeResult;
    }

    @Override
    public List<Map<String, Object>> getMappingRows() {
        return mappingRows;
    }

    @Override
    public PropertySetterCaller<Object> getSetter() {
        return setter;
    }
}
