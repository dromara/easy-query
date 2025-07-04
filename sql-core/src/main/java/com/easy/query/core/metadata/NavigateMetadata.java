package com.easy.query.core.metadata;

import com.easy.query.core.common.DirectMappingIterator;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyMapUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
    private final String[] directMapping;
    private final SQLActionExpression1<WherePredicate<?>> predicateFilterExpression;
    private final SQLActionExpression1<WherePredicate<?>> predicateMappingClassFilterExpression;

    private final Map<String, NavigateMetadata> directMappingMetadataMap;
    private final EntityRelationPropertyProvider entityRelationPropertyProvider;

    private final long offset;
    private final long limit;
    private final boolean required;
    private final boolean subQueryToGroupJoin;
    private final PartitionOrderEnum partitionOrder;

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
        this.predicateMappingClassFilterExpression = navigateOption.getPredicateMappingClassFilterExpression();
        this.entityRelationPropertyProvider = navigateOption.getEntityRelationPropertyProvider();
        this.basicType = navigateOption.isBasicType();
        this.orderProps = navigateOption.getOrderProps();
        this.offset = navigateOption.getOffset();
        this.limit = navigateOption.getLimit();
        this.directMapping = navigateOption.getDirectMapping();
        this.getter = getter;
        this.setter = setter;
        this.required = navigateOption.isRequired();
        this.subQueryToGroupJoin = navigateOption.isSubQueryToGroupJoin();
        this.partitionOrder = navigateOption.getPartitionOrder();
        if (EasyArrayUtil.isNotEmpty(directMapping)) {
            this.directMappingMetadataMap = new ConcurrentHashMap<>(2);
        } else {
            this.directMappingMetadataMap = null;
        }
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
        if (EasyArrayUtil.isNotEmpty(directMapping)) {
            return entityMetadata.getNavigateNotNull(directMapping[0]).getSelfPropertiesOrPrimary();
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

    public String[] getDirectSelfPropertiesOrPrimary(QueryRuntimeContext runtimeContext) {
        checkDirectMapping();
        return getDirectFirstNavigateMetadata(runtimeContext).getSelfPropertiesOrPrimary();
    }

    public String[] getDirectMappingSelfPropertiesOrPrimary(QueryRuntimeContext runtimeContext) {
        checkDirectMapping();
        return getDirectFirstNavigateMetadata(runtimeContext).getTargetPropertiesOrPrimary(runtimeContext);
    }

    public String[] getDirectTargetPropertiesOrPrimary(QueryRuntimeContext runtimeContext) {
        checkDirectMapping();
        return getDirectEndNavigateMetadata(runtimeContext).getTargetPropertiesOrPrimary(runtimeContext);
    }

    public String[] getDirectMappingTargetPropertiesOrPrimary(QueryRuntimeContext runtimeContext) {
        checkDirectMapping();
        return getDirectEndNavigateMetadata(runtimeContext).getSelfPropertiesOrPrimary();
    }


    private NavigateMetadata getDirectFirstNavigateMetadata(QueryRuntimeContext runtimeContext) {
        return EasyMapUtil.computeIfAbsent(directMappingMetadataMap,"DIRECT_FIRST_NAVIGATE", key -> {
            return getDirectMappingFirstNavigateMetadata(new DirectMappingIterator(directMapping), runtimeContext, entityMetadata, null);
        });
    }

    private NavigateMetadata getDirectEndNavigateMetadata(QueryRuntimeContext runtimeContext) {
        return EasyMapUtil.computeIfAbsent(directMappingMetadataMap,"DIRECT_END_NAVIGATE", key -> {
            return getDirectMappingEndNavigateMetadata(new DirectMappingIterator(directMapping), runtimeContext, entityMetadata, null);
        });
    }

    private NavigateMetadata getDirectMappingFirstNavigateMetadata(DirectMappingIterator directMappingIterator, QueryRuntimeContext runtimeContext, EntityMetadata entityMetadata, NavigateMetadata navigateMetadata) {
        if (directMappingIterator.hasNext()) {
            String prop = directMappingIterator.next();
            NavigateMetadata propNavigateMetadata = entityMetadata.getNavigateNotNull(prop);
            if (EasyArrayUtil.isEmpty(propNavigateMetadata.getDirectMapping())) {
                return propNavigateMetadata;
            }
            EntityMetadata targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(propNavigateMetadata.getNavigatePropertyType());
            return getDirectMappingFirstNavigateMetadata(directMappingIterator, runtimeContext, targetEntityMetadata, propNavigateMetadata);
        }
        return Objects.requireNonNull(navigateMetadata, "cant getDirectMappingFirstNavigateMetadata");
    }

    private NavigateMetadata getDirectMappingEndNavigateMetadata(DirectMappingIterator directMappingIterator, QueryRuntimeContext runtimeContext, EntityMetadata entityMetadata, NavigateMetadata navigateMetadata) {
        if (directMappingIterator.hasNext()) {
            String prop = directMappingIterator.next();
            NavigateMetadata propNavigateMetadata = entityMetadata.getNavigateNotNull(prop);
            EntityMetadata targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(propNavigateMetadata.getNavigatePropertyType());
            return getDirectMappingEndNavigateMetadata(directMappingIterator, runtimeContext, targetEntityMetadata, propNavigateMetadata);
        }
        if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
            DirectMappingIterator endDirectMappingIterator = new DirectMappingIterator(navigateMetadata.getDirectMapping());
            EntityMetadata targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());
            return getDirectMappingEndNavigateMetadata(endDirectMappingIterator, runtimeContext, targetEntityMetadata, navigateMetadata);
        }
        return Objects.requireNonNull(navigateMetadata, "cant getDirectMappingEndNavigateMetadata");
    }

    public Class<?> getDirectMappingClass(QueryRuntimeContext runtimeContext) {
        checkDirectMapping();
        return getDirectFirstNavigateMetadata(runtimeContext).getNavigatePropertyType();
    }

    private void checkDirectMapping() {
        if (EasyArrayUtil.isEmpty(directMapping)) {
            throw new EasyQueryInvalidOperationException("directMapping is empty");
        }
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

    public SQLActionExpression1<WherePredicate<?>> getPredicateFilterExpression() {
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

    public SQLActionExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression() {
        return predicateMappingClassFilterExpression;
    }

    public boolean hasPredicateMappingClassFilterExpression() {
        return predicateMappingClassFilterExpression != null;
    }

    public void predicateMappingClassFilterApply(WherePredicate<?> wherePredicate) {
        if (predicateMappingClassFilterExpression != null) {
            predicateMappingClassFilterExpression.apply(wherePredicate);
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

    public String[] getDirectMapping() {
        return directMapping;
    }

    public EntityRelationPropertyProvider getEntityRelationPropertyProvider() {
        return entityRelationPropertyProvider;
    }

    /**
     * 用来描述是否存在 存在则使用inner join
     * @return
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * 默认不使用子查询而是将子查询转成group by
     * @return
     */
    public boolean isSubQueryToGroupJoin() {
        return subQueryToGroupJoin;
    }

    public PartitionOrderEnum getPartitionOrder() {
        return partitionOrder;
    }
}
