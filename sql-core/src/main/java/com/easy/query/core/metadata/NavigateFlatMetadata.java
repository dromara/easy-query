package com.easy.query.core.metadata;

import com.easy.query.core.enums.RelationMappingTypeEnum;
import com.easy.query.core.expression.lambda.PropertySetterCaller;

/**
 * create time 2023/6/17 19:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateFlatMetadata {

    private final EntityMetadata entityMetadata;
    /**
     * 关联关系
     */
    private final RelationMappingTypeEnum relationMappingType;
    private final String[] mappingPath;
    private final Class<?> navigatePropertyType;
    private final boolean basicType;
    private final PropertySetterCaller<Object> beanSetter;

    public NavigateFlatMetadata(EntityMetadata entityMetadata,
                                RelationMappingTypeEnum relationMappingType,
                                String[] mappingPath,
                                Class<?> navigatePropertyType,
                                boolean basicType,
                                PropertySetterCaller<Object> beanSetter) {
        this.entityMetadata = entityMetadata;
        this.relationMappingType = relationMappingType;
        this.mappingPath = mappingPath;
        this.navigatePropertyType = navigatePropertyType;
        this.basicType=basicType;
        this.beanSetter = beanSetter;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public boolean isBasicType() {
        return basicType;
    }

    public RelationMappingTypeEnum getRelationMappingType() {
        return relationMappingType;
    }

    public String[] getMappingPath() {
        return mappingPath;
    }
    public MappingPathIterator getMappingPathIterator(){
        return new MappingPathIterator(mappingPath);
    }

    public PropertySetterCaller<Object> getBeanSetter() {
        return beanSetter;
    }

    public Class<?> getNavigatePropertyType() {
        return navigatePropertyType;
    }
}
