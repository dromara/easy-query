package com.easy.query.core.expression.sql.include;

import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateFlatMetadata;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.Collections;
import java.util.HashMap;
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
    private final NavigateMetadata navigateMetadata;
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
    private List<Object> mappingRows;
    private final PropertySetterCaller<Object> setter;
    private final Property<Object, ?> getter;
    private final List<NavigateFlatMetadata> navigateFlatMetadataList;
    private final EntityMetadata flatQueryEntityMetadata;
    private final String[] directMapping;
    private final boolean hasOrder;
    private final IncludeNavigateParams includeNavigateParams;
    private final Map<Object,Object> flatClassMap;

    public DefaultIncludeParserResult(EntityMetadata entityMetadata,
                                      NavigateMetadata navigateMetadata,
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
                                      List<Object> mappingRows,
                                      PropertySetterCaller<Object> setter,
                                      Property<Object, ?> getter,
                                      List<NavigateFlatMetadata> navigateFlatMetadataList,
                                      EntityMetadata flatQueryEntityMetadata,
                                      String[] directMapping,
                                      boolean hasOrder,
                                      IncludeNavigateParams includeNavigateParams,Map<Object,Object> flatClassMap){

        this.entityMetadata = entityMetadata;
        this.navigateMetadata = navigateMetadata;
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
        this.directMapping = directMapping;
        this.hasOrder = hasOrder;
        this.includeNavigateParams = includeNavigateParams;
        this.flatClassMap = flatClassMap;
    }
    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public NavigateMetadata getNavigateMetadata() {
        return navigateMetadata;
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
    public String[] getDirectMapping() {
        return directMapping;
    }

    @Override
    public List<RelationExtraEntity> getIncludeResult() {
        return includeResult;
    }

    @Override
    public List<Object> getMappingRows() {
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

    @Override
    public boolean isHasOrder() {
        return hasOrder;
    }

    @Override
    public IncludeNavigateParams getIncludeNavigateParams() {
        return includeNavigateParams;
    }

    @Override
    public Map<Object,Object> getFlatClassMap() {
        return flatClassMap;
    }
}
