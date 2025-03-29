package com.easy.query.core.metadata;

import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.util.EasyClassUtil;

import java.util.List;

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
    private final String[] selfProperties;
    private final String[] targetProperties;
    private final boolean basicType;
    private final List<NavigateOrderProp> orderProps;
    private final long offset;
    private final long limit;
    private final String[] directMapping;
    private Class<?> mappingClass;
    private String[] selfMappingProperties;
    private String[] targetMappingProperties;
    private SQLExpression1<WherePredicate<?>> predicateFilterExpression;
    private SQLExpression1<WherePredicate<?>> predicateMappingClassFilterExpression;
    private EntityRelationPropertyProvider toManySubquerySQLStrategy;

    public NavigateOption(EntityMetadata entityMetadata,
                          String propertyName,
                          Class<?> navigateOriginalPropertyType,
                          Class<?> navigatePropertyType,
                          RelationTypeEnum relationType,
                          String[] selfProperties,
                          String[] targetProperties, List<NavigateOrderProp> orderProps, long offset, long limit, String[] directMapping) {

        this.entityMetadata = entityMetadata;
        this.propertyName = propertyName;
        this.navigateOriginalPropertyType = navigateOriginalPropertyType;
        this.navigatePropertyType = navigatePropertyType;
        this.relationType = relationType;
        this.selfProperties = selfProperties;
        this.targetProperties = targetProperties;
        this.basicType = EasyClassUtil.isBasicType(navigatePropertyType);
        this.orderProps = orderProps;
        this.offset = offset;
        this.limit = limit;
        this.directMapping = directMapping;
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

    public String[] getSelfProperties() {
        return selfProperties;
    }

    public String[] getTargetProperties() {
        return targetProperties;
    }

    public Class<?> getMappingClass() {
        return mappingClass;
    }

    public void setMappingClass(Class<?> mappingClass) {
        this.mappingClass = mappingClass;
    }

    public String[] getSelfMappingProperties() {
        return selfMappingProperties;
    }

    public void setSelfMappingProperties(String[] selfMappingProperties) {
        this.selfMappingProperties = selfMappingProperties;
    }

    public String[] getTargetMappingProperties() {
        return targetMappingProperties;
    }

    public void setTargetMappingProperties(String[] targetMappingProperties) {
        this.targetMappingProperties = targetMappingProperties;
    }

    public SQLExpression1<WherePredicate<?>> getPredicateFilterExpression() {
        return predicateFilterExpression;
    }

    public void setPredicateFilterExpression(SQLExpression1<WherePredicate<?>> predicateFilterExpression) {
        this.predicateFilterExpression = predicateFilterExpression;
    }

    public SQLExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression() {
        return predicateMappingClassFilterExpression;
    }

    public void setPredicateMappingClassFilterExpression(SQLExpression1<WherePredicate<?>> predicateMappingClassFilterExpression) {
        this.predicateMappingClassFilterExpression = predicateMappingClassFilterExpression;
    }

    public boolean isBasicType() {
        return basicType;
    }

    public List<NavigateOrderProp> getOrderProps() {
        return orderProps;
    }

    public long getOffset() {
        return offset;
    }

    public long getLimit() {
        return limit;
    }

    public String[] getDirectMapping() {
        return directMapping;
    }

    public EntityRelationPropertyProvider getToManySubquerySQLStrategy() {
        return toManySubquerySQLStrategy;
    }

    public void setToManySubquerySQLStrategy(EntityRelationPropertyProvider toManySubquerySQLStrategy) {
        this.toManySubquerySQLStrategy = toManySubquerySQLStrategy;
    }
}
