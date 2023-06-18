package com.easy.query.core.metadata;

import com.easy.query.core.enums.RelationTypeEnum;

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

    public NavigateMetadata(EntityMetadata entityMetadata, String propertyName, Class<?> navigatePropertyType,RelationTypeEnum relationType,String relationKey) {
        this.entityMetadata = entityMetadata;
        this.propertyName = propertyName;
        this.navigatePropertyType = navigatePropertyType;
        this.relationType = relationType;
        this.relationKey = relationKey;
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
}
