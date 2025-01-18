package com.easy.query.core.expression.sql.include;

import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateFlatMetadata;

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
    private final List<RelationExtraEntity> relationExtraEntities;
    private final RelationTypeEnum relationType;
    private final String navigatePropertyName;
    private final Class<?> navigateOriginalPropertyType;
    private final Class<?> navigatePropertyType;
    private final String[] selfProperties;
    private final String[] targetProperties;
    private final List<RelationExtraEntity> includeResult;

    private Class<?> mappingClass;
    private String[] selfMappingProperties;
    private String[] targetMappingProperties;
    private List<Map<String, Object>> mappingRows;
    private final PropertySetterCaller<Object> setter;
    private final Property<Object, ?> getter;
    private final List<NavigateFlatMetadata> navigateFlatMetadataList;
    private final EntityMetadata flatQueryEntityMetadata;

    public DefaultIncludeParserResult(EntityMetadata entityMetadata,
                                      List<RelationExtraEntity> relationExtraEntities,
                                      RelationTypeEnum relationType,
                                      String navigatePropertyName,
                                      Class<?> navigateOriginalPropertyType,
                                      Class<?> navigatePropertyType,
                                      String[] selfProperties,
                                      String[] targetProperties,
                                      Class<?> mappingClass,
                                      String[] selfMappingProperties,
                                      String[] targetMappingProperties,
                                      List<RelationExtraEntity> includeResult,
                                      List<Map<String, Object>> mappingRows,
                                      PropertySetterCaller<Object> setter,
                                      Property<Object, ?> getter,
                                      List<NavigateFlatMetadata> navigateFlatMetadataList,
                                      EntityMetadata flatQueryEntityMetadata){

        this.entityMetadata = entityMetadata;
        this.relationExtraEntities = relationExtraEntities;
        this.relationType = relationType;
        this.navigatePropertyName = navigatePropertyName;
        this.navigateOriginalPropertyType = navigateOriginalPropertyType;
        this.navigatePropertyType = navigatePropertyType;
        this.selfProperties = selfProperties;
        this.targetProperties = targetProperties;
        this.mappingClass = mappingClass;
        this.selfMappingProperties = selfMappingProperties;
        this.targetMappingProperties = targetMappingProperties;
        this.includeResult = includeResult;
        this.mappingRows = mappingRows;
        this.setter = setter;
        this.getter = getter;
        this.navigateFlatMetadataList = navigateFlatMetadataList;
        this.flatQueryEntityMetadata = flatQueryEntityMetadata;
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
    public String[] getSelfProperties() {
        return selfProperties;
    }

    @Override
    public String[] getTargetProperties() {
        return targetProperties;
    }

    @Override
    public Class<?> getMappingClass() {
        return mappingClass;
    }

    @Override
    public String[] getSelfMappingProperties() {
        return selfMappingProperties;
    }

    @Override
    public String[] getTargetMappingProperties() {
        return targetMappingProperties;
    }

    @Override
    public List<RelationExtraEntity> getIncludeResult() {
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

    @Override
    public Property<Object,?> getGetter() {
        return getter;
    }

    /**
     * 封装的额外对象
     * @return
     */
    public List<RelationExtraEntity> getRelationExtraEntities() {
        return relationExtraEntities;
    }

    @Override
    public List<NavigateFlatMetadata> getNavigateFlatMetadataList() {
        return navigateFlatMetadataList;
    }

    @Override
    public EntityMetadata getFlatQueryEntityMetadata() {
        return flatQueryEntityMetadata;
    }
}
