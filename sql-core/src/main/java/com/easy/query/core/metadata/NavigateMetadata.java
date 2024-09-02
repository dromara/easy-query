package com.easy.query.core.metadata;

import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
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

    /**
     * 泛型的不是真正的type如果是泛型集合
     */
    private final Class<?> navigateOriginalPropertyType;
    /**
     * 主表的属性类型
     */
    private final Class<?> navigatePropertyType;
    /**
     * 关联关系
     */
    private final RelationTypeEnum relationType;
    private final boolean basicType;
    private final String selfProperty;
    /**
     * 导航属性关联字段
     */
    private final String targetProperty;
    private final Property<Object, ?> getter;
    private final PropertySetterCaller<Object> setter;
    private final Class<?> mappingClass;
    private final String selfMappingProperty;
    private final String targetMappingProperty;
    private final SQLExpression1<WherePredicate<?>> predicateFilterExpression;

    public NavigateMetadata(NavigateOption navigateOption,
                            Property<Object, ?> getter,
                            PropertySetterCaller<Object> setter) {
        this.entityMetadata = navigateOption.getEntityMetadata();
        this.propertyName = navigateOption.getPropertyName();
        this.navigateOriginalPropertyType = navigateOption.getNavigateOriginalPropertyType();
        this.navigatePropertyType = navigateOption.getNavigatePropertyType();
        this.relationType = navigateOption.getRelationType();
        this.selfProperty = navigateOption.getSelfProperty();
        this.targetProperty = navigateOption.getTargetProperty();
        this.mappingClass = navigateOption.getMappingClass();
        this.selfMappingProperty = navigateOption.getSelfMappingProperty();
        this.targetMappingProperty = navigateOption.getTargetMappingProperty();
        this.predicateFilterExpression = navigateOption.getPredicateFilterExpression();
        this.basicType=navigateOption.isBasicType();
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


    public String getSelfMappingProperty() {
        return selfMappingProperty;
    }


    public String getTargetMappingProperty() {
        return targetMappingProperty;
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

    public SQLExpression1<WherePredicate<?>> getPredicateFilterExpression() {
        return predicateFilterExpression;
    }
    public boolean hasPredicateFilterExpression() {
        return predicateFilterExpression!=null;
    }

    public void predicateFilterApply(WherePredicate<?> wherePredicate){
        if(predicateFilterExpression!=null){
            predicateFilterExpression.apply(wherePredicate);
        }
    }
//    public void predicateFilterApply2(WherePredicate<?> wherePredicate, EntitySQLTableOwner<?> selfTableOrNull){
//        if(predicateFilterExpression!=null){
//            predicateFilterExpression.apply(wherePredicate);
//        }
//    }

    public boolean isBasicType() {
        return basicType;
    }
}
