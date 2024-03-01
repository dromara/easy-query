package com.easy.query.core.metadata;

import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2024/3/1 16:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateOption {
    private final EntityMetadata entityMetadata;
    private final String propertyName;
    private final Class<?> navigateOriginalPropertyType;
    private final Class<?> navigatePropertyType;
    private final RelationTypeEnum relationType;
    private final String selfProperty;
    private final String targetProperty;
    private Class<?> mappingClass;
    private String selfMappingProperty;
    private String targetMappingProperty;
    private SQLExpression1<WherePredicate<?>> predicateFilterExpression;

    public NavigateOption(EntityMetadata entityMetadata,
                          String propertyName,
                          Class<?> navigateOriginalPropertyType,
                          Class<?> navigatePropertyType,
                          RelationTypeEnum relationType,
                          String selfProperty,
                          String targetProperty){

        this.entityMetadata = entityMetadata;
        this.propertyName = propertyName;
        this.navigateOriginalPropertyType = navigateOriginalPropertyType;
        this.navigatePropertyType = navigatePropertyType;
        this.relationType = relationType;
        this.selfProperty = selfProperty;
        this.targetProperty = targetProperty;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getNavigateOriginalPropertyType() {
        return navigateOriginalPropertyType;
    }

    public Class<?> getNavigatePropertyType() {
        return navigatePropertyType;
    }

    public RelationTypeEnum getRelationType() {
        return relationType;
    }

    public String getSelfProperty() {
        return selfProperty;
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public Class<?> getMappingClass() {
        return mappingClass;
    }

    public void setMappingClass(Class<?> mappingClass) {
        this.mappingClass = mappingClass;
    }

    public String getSelfMappingProperty() {
        return selfMappingProperty;
    }

    public void setSelfMappingProperty(String selfMappingProperty) {
        this.selfMappingProperty = selfMappingProperty;
    }

    public String getTargetMappingProperty() {
        return targetMappingProperty;
    }

    public void setTargetMappingProperty(String targetMappingProperty) {
        this.targetMappingProperty = targetMappingProperty;
    }

    public SQLExpression1<WherePredicate<?>> getPredicateFilterExpression() {
        return predicateFilterExpression;
    }

    public void setPredicateFilterExpression(SQLExpression1<WherePredicate<?>> predicateFilterExpression) {
        this.predicateFilterExpression = predicateFilterExpression;
    }
}
