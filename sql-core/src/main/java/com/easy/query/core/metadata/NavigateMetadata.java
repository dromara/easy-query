package com.easy.query.core.metadata;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/6/17 19:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateMetadata {

    private final EntityMetadata entityMetadata;
    /**
     * 主表属性名称
     */
    private final String propertyName;

    private final Class<?> navigateOriginalPropertyType;
    /**
     * 主表的属性类型
     */
    private final Class<?> navigatePropertyType;
    /**
     * 关联关系
     */
    private final RelationTypeEnum relationType;
    private final String selfProperty;
    /**
     * 导航属性关联字段
     */
    private final String targetProperty;
    private final Property<Object, ?> getter;
    private final PropertySetterCaller<Object> setter;
    private Class<?> mappingClass;
    private String selfMappingProperty;
    private String targetMappingProperty;

    public NavigateMetadata(EntityMetadata entityMetadata,
                            String propertyName,
                            Class<?> navigateOriginalPropertyType,
                            Class<?> navigatePropertyType,
                            RelationTypeEnum relationType,
                            String selfProperty,
                            String targetProperty,
                            Property<Object, ?> getter,
                            PropertySetterCaller<Object> setter) {
        this.entityMetadata = entityMetadata;
        this.propertyName = propertyName;
        this.navigateOriginalPropertyType = navigateOriginalPropertyType;
        this.navigatePropertyType = navigatePropertyType;
        this.relationType = relationType;
        this.selfProperty = selfProperty;
        this.targetProperty = targetProperty;
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

    public Class<?> getNavigateOriginalPropertyType() {
        return navigateOriginalPropertyType;
    }

    public RelationTypeEnum getRelationType() {
        return relationType;
    }

    public String getSelfProperty() {
        return selfProperty;
    }

    public String getSelfPropertyOrPrimary(){

        if(EasyStringUtil.isNotBlank(selfProperty)){
            return selfProperty;
        }
        return entityMetadata.getSingleKeyProperty();
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public String getTargetPropertyOrPrimary(QueryRuntimeContext runtimeContext){
        if(EasyStringUtil.isNotBlank(targetProperty)){
            return targetProperty;
        }
        EntityMetadata targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigatePropertyType);
        return targetEntityMetadata.getSingleKeyProperty();
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

    public Property<Object, ?> getGetter() {
        return getter;
    }

    public PropertySetterCaller<Object> getSetter() {
        return setter;
    }


    public ColumnMetadata getSelfRelationColumn() {
        String selfPropertyName = getSelfPropertyOrPrimary();
        return entityMetadata.getColumnNotNull(selfPropertyName);
    }
}
