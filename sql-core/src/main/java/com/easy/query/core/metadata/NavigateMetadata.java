package com.easy.query.core.metadata;

import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;

/**
 * create time 2023/6/17 19:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateMetadata {

    private final EntityMetadata entityMetadata;
    private final String propertyName;


    private final Class<?> navigatePropertyType;
    private final RelationTypeEnum relationType;
    private final String relationKey;
    private final Property<Object, ?> getter;
    private final PropertySetterCaller<Object> setter;

    public NavigateMetadata(EntityMetadata entityMetadata, String propertyName, Class<?> navigatePropertyType, RelationTypeEnum relationType, String relationKey, Property<Object, ?> getter, PropertySetterCaller<Object> setter) {
        this.entityMetadata = entityMetadata;
        this.propertyName = propertyName;
        this.navigatePropertyType = navigatePropertyType;
        this.relationType = relationType;
        this.relationKey = relationKey;
        this.getter = getter;
        this.setter = setter;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getNavigatePropertyType() {
        return navigatePropertyType;
    }

    public RelationTypeEnum getRelationType() {
        return relationType;
    }

    public String getRelationKey() {
        return relationKey;
    }

    public Property<Object, ?> getGetter() {
        return getter;
    }

    public PropertySetterCaller<Object> getSetter() {
        return setter;
    }
}
