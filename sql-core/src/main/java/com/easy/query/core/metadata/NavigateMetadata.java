package com.easy.query.core.metadata;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.List;

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
    private final List<NavigateOrderProp> orderProps;
    private final String[] selfProperties;
    /**
     * 导航属性关联字段
     */
    private final String[] targetProperties;
    private final Property<Object, ?> getter;
    private final PropertySetterCaller<Object> setter;
    private final Class<?> mappingClass;
    private final String[] selfMappingProperties;
    private final String[] targetMappingProperties;
    private final SQLExpression1<WherePredicate<?>> predicateFilterExpression;
    private final SQLExpression1<WherePredicate<?>> predicateManyToManyFilterExpression;
    private final long offset;
    private final long limit;

    public NavigateMetadata(NavigateOption navigateOption,
                            Property<Object, ?> getter,
                            PropertySetterCaller<Object> setter) {
        this.entityMetadata = navigateOption.getEntityMetadata();
        this.propertyName = navigateOption.getPropertyName();
        this.navigateOriginalPropertyType = navigateOption.getNavigateOriginalPropertyType();
        this.navigatePropertyType = navigateOption.getNavigatePropertyType();
        this.relationType = navigateOption.getRelationType();
        this.selfProperties = navigateOption.getSelfProperties();
        this.targetProperties = navigateOption.getTargetProperties();
        this.mappingClass = navigateOption.getMappingClass();
        this.selfMappingProperties = navigateOption.getSelfMappingProperties();
        this.targetMappingProperties = navigateOption.getTargetMappingProperties();
        this.predicateFilterExpression = navigateOption.getPredicateFilterExpression();
        this.predicateManyToManyFilterExpression = navigateOption.getPredicateManyToManyFilterExpression();
        this.basicType = navigateOption.isBasicType();
        this.orderProps = navigateOption.getOrderProps();
        this.offset = navigateOption.getOffset();
        this.limit = navigateOption.getLimit();
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

    public String[] getSelfProperties() {
        return selfProperties;
    }

    public String[] getSelfPropertiesOrPrimary() {

        if (EasyArrayUtil.isNotEmpty(selfProperties)) {
            return selfProperties;
        }
        return new String[]{entityMetadata.getSingleKeyProperty()};
    }

    public String[] getTargetProperties() {
        return targetProperties;
    }

    public String[] getTargetPropertiesOrPrimary(QueryRuntimeContext runtimeContext) {
        if (EasyArrayUtil.isNotEmpty(targetProperties)) {
            return targetProperties;
        }
        EntityMetadata targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigatePropertyType);
        return new String[]{targetEntityMetadata.getSingleKeyProperty()};
    }

    public Class<?> getMappingClass() {
        return mappingClass;
    }


    public String[] getSelfMappingProperties() {
        return selfMappingProperties;
    }


    public String[] getTargetMappingProperties() {
        return targetMappingProperties;
    }


    public Property<Object, ?> getGetter() {
        return getter;
    }

    public PropertySetterCaller<Object> getSetter() {
        return setter;
    }


    public ColumnMetadata[] getSelfRelationColumn() {
        String[] selfPropertyNames = getSelfPropertiesOrPrimary();
        ColumnMetadata[] columnMetadatas = new ColumnMetadata[selfPropertyNames.length];
        for (int i = 0; i < selfPropertyNames.length; i++) {

            ColumnMetadata column = entityMetadata.getColumnNotNull(selfPropertyNames[i]);
            columnMetadatas[i] = column;
        }
        return columnMetadatas;
    }

    public SQLExpression1<WherePredicate<?>> getPredicateFilterExpression() {
        return predicateFilterExpression;
    }

    public boolean hasPredicateFilterExpression() {
        return predicateFilterExpression != null;
    }

    public void predicateFilterApply(WherePredicate<?> wherePredicate) {
        if (predicateFilterExpression != null) {
            predicateFilterExpression.apply(wherePredicate);
        }
    }

    public SQLExpression1<WherePredicate<?>> getPredicateManyToManyFilterExpression() {
        return predicateManyToManyFilterExpression;
    }

    public boolean hasPredicateManyToManyFilterExpression() {
        return predicateManyToManyFilterExpression != null;
    }

    public void predicateManyToManyFilterApply(WherePredicate<?> wherePredicate) {
        if (predicateManyToManyFilterExpression != null) {
            predicateManyToManyFilterExpression.apply(wherePredicate);
        }
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
}
