package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveModeEnum;
import com.easy.query.api.proxy.entity.save.TargetValueTypeEnum;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.enums.ValueTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2025/9/7 11:07
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSaveProvider implements SaveProvider {
    protected final Class<?> entityClass;
    protected final List<Object> entities;
    protected final EasyQueryClient easyQueryClient;
    protected final QueryRuntimeContext runtimeContext;
    protected final EntityMetadataManager entityMetadataManager;
    protected final TrackContext currentTrackContext;
    protected final SaveModeEnum saveMode;
    protected final List<Set<String>> savePathLimit;
    protected final SaveCommandContext saveCommandContext;

    public AbstractSaveProvider(TrackContext currentTrackContext, Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient, List<Set<String>> savePathLimit, SaveModeEnum saveMode) {
        this.entityClass = entityClass;
        this.entities = entities;
        this.easyQueryClient = easyQueryClient;
        this.runtimeContext = easyQueryClient.getRuntimeContext();
        this.currentTrackContext = currentTrackContext;
        this.saveMode = saveMode;
        this.entityMetadataManager = runtimeContext.getEntityMetadataManager();
        this.savePathLimit = savePathLimit;
        this.saveCommandContext = new SaveCommandContext(entityClass);
    }

    protected boolean isSavePathLimitContains(NavigateMetadata navigateMetadata, int deep) {
        if (savePathLimit.isEmpty()) {
            return true;
        }
        if (savePathLimit.size() > deep) {
            return savePathLimit.get(deep).contains(navigateMetadata.getPropertyName());
        }
        return false;
    }

    protected TargetValueTypeEnum getTargetValueType(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {
        if (navigateMetadata.getValueType() != ValueTypeEnum.IGNORE) {
            if (navigateMetadata.getValueType() == ValueTypeEnum.AUTO_CHECK) {
                return autoGetTargetValueType(selfMetadata, navigateMetadata);
            }
            if (navigateMetadata.getValueType() == ValueTypeEnum.AGGREGATE_ROOT) {
                return TargetValueTypeEnum.AGGREGATE_ROOT;
            }
            if (navigateMetadata.getValueType() == ValueTypeEnum.VALUE_OBJECT) {
                return TargetValueTypeEnum.VALUE_OBJECT;
            }
        }
        return TargetValueTypeEnum.RELATION_OTHER;
    }

    protected TargetValueTypeEnum autoGetTargetValueType(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            if (navigateMetadata.getMappingClass() != null) {
                boolean any = selfPropsIsKeys(selfMetadata, navigateMetadata);
                if (any) {
                    return TargetValueTypeEnum.VALUE_OBJECT;
                }
            }
            return TargetValueTypeEnum.RELATION_OTHER;
        }
        boolean selfIsKey = selfPropsIsKeys(selfMetadata, navigateMetadata);
        if (selfIsKey) {
            return TargetValueTypeEnum.VALUE_OBJECT;
        }
        boolean targetIsKey = targetPropsIsKeys(selfMetadata, navigateMetadata);
        if (targetIsKey) {
            return TargetValueTypeEnum.AGGREGATE_ROOT;
        }
        return TargetValueTypeEnum.RELATION_OTHER;
    }

    protected boolean selfPropsIsKeys(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {

        String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
        Collection<String> keyProperties = selfMetadata.getKeyProperties();
        if (selfPropertiesOrPrimary.length != keyProperties.size()) {
            return false;
        }
        return EasyArrayUtil.all(selfPropertiesOrPrimary, prop -> keyProperties.contains(prop));
    }

    protected boolean targetPropsIsKeys(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {

        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
        Collection<String> keyProperties = selfMetadata.getKeyProperties();
        if (targetPropertiesOrPrimary.length != keyProperties.size()) {
            return false;
        }
        return EasyArrayUtil.all(targetPropertiesOrPrimary, prop -> keyProperties.contains(prop));
    }

    protected void setTargetValue(TargetValueTypeEnum targetValueType, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, EntityMetadata targetEntityMetadata) {
        if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT) {
            String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
            for (int i = 0; i < selfPropertiesOrPrimary.length; i++) {
                String self = selfPropertiesOrPrimary[i];
                String target = targetPropertiesOrPrimary[i];
                ColumnMetadata selfColumn = selfEntityMetadata.getColumnNotNull(self);
                Object selfValue = selfColumn.getGetterCaller().apply(selfEntity);
                if (selfValue == null) {
                    throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getInstanceSimpleName(selfEntity) + "] property:[" + self + "] value can not be null, cant set relation value for entity:[" + EasyClassUtil.getInstanceSimpleName(targetEntity) + "]");
                }
                ColumnMetadata targetColumn = targetEntityMetadata.getColumnNotNull(target);
                Object currentValue = targetColumn.getGetterCaller().apply(targetEntity);
                if (currentValue == null) {
                    targetColumn.getSetterCaller().call(targetEntity, selfValue);
                } else {
                    if (!Objects.equals(currentValue, selfValue)) {
                        throw new EasyQueryInvalidOperationException("relation value not equals,entity:[" + EasyClassUtil.getInstanceSimpleName(targetEntity) + "],property:[" + target + "],value:[" + currentValue + "],should:[" + selfValue + "]");
                    }
                }
            }
        } else if (targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
            String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
            for (int i = 0; i < selfPropertiesOrPrimary.length; i++) {
                String self = selfPropertiesOrPrimary[i];
                String target = targetPropertiesOrPrimary[i];
                ColumnMetadata targetColumn = targetEntityMetadata.getColumnNotNull(target);
                Object targetValue = targetColumn.getGetterCaller().apply(targetEntity);
                if (targetValue == null) {
                    throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getInstanceSimpleName(targetEntity) + "] property:[" + target + "] value can not be null, cant set relation value for entity:[" + EasyClassUtil.getInstanceSimpleName(selfEntity) + "]");
                }
                ColumnMetadata selfColumn = selfEntityMetadata.getColumnNotNull(self);
                selfColumn.getSetterCaller().call(selfEntity, targetValue);

                Object currentValue = selfColumn.getGetterCaller().apply(selfEntity);
                if (currentValue == null) {
                    selfColumn.getSetterCaller().call(selfEntity, targetValue);
                } else {
                    if (!Objects.equals(currentValue, targetValue)) {
                        throw new EasyQueryInvalidOperationException("relation value not equals,entity:[" + EasyClassUtil.getInstanceSimpleName(selfEntity) + "],property:[" + self + "],value:[" + currentValue + "],should:[" + targetValue + "]");
                    }
                }
            }
        } else {
            throw new EasyQueryInvalidOperationException("save not support target value type:" + targetValueType);
        }
    }

    protected void setMappingEntity(Object selfEntity, Object targetEntity, Object mappingEntity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, EntityMetadata targetEntityMetadata, EntityMetadata mappingEntityMetadata) {
        String[] selfMappingProperties = navigateMetadata.getSelfMappingProperties();
        String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
        for (int i = 0; i < selfMappingProperties.length; i++) {
            String selfMappingProperty = selfMappingProperties[i];
            String self = selfPropertiesOrPrimary[i];
            ColumnMetadata selfColumn = selfEntityMetadata.getColumnNotNull(self);
            Object selfValue = selfColumn.getGetterCaller().apply(selfEntity);
            if (selfValue == null) {
                throw new EasyQueryInvalidOperationException("entityClass:[" + EasyClassUtil.getInstanceSimpleName(selfEntity) + "] property:[" + self + "] value can not be null,Please make sure the [" + EasyClassUtil.getInstanceSimpleName(selfEntity) + "] instance has been persisted to the database before use.");
            }
            ColumnMetadata columnMetadata = mappingEntityMetadata.getColumnNotNull(selfMappingProperty);
            columnMetadata.getSetterCaller().call(mappingEntity, selfValue);
        }
        String[] targetMappingProperties = navigateMetadata.getTargetMappingProperties();
        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
        for (int i = 0; i < targetMappingProperties.length; i++) {
            String targetMappingProperty = targetMappingProperties[i];
            String target = targetPropertiesOrPrimary[i];
            ColumnMetadata targetColumn = targetEntityMetadata.getColumnNotNull(target);
            Object targetValue = targetColumn.getGetterCaller().apply(targetEntity);
            if (targetValue == null) {
                throw new EasyQueryInvalidOperationException("entityClass:[" + EasyClassUtil.getInstanceSimpleName(targetEntity) + "] property:[" + target + "] value can not be null,Please make sure the [" + EasyClassUtil.getInstanceSimpleName(targetEntity) + "] instance has been persisted to the database before use.");
            }
            ColumnMetadata columnMetadata = mappingEntityMetadata.getColumnNotNull(targetMappingProperty);
            columnMetadata.getSetterCaller().call(mappingEntity, targetValue);
        }
    }

    protected EntityMetadata checkNavigateContinueAndGetTargetEntityMetadata(TargetValueTypeEnum targetValueType, NavigateMetadata navigateMetadata, int deep) {

        //存在路径的情况下要判断路径保存只能是值对象
        if (EasyCollectionUtil.isNotEmpty(savePathLimit)) {
            if (!isSavePathLimitContains(navigateMetadata, deep)) {
                return null;
            }
            if (targetValueType != TargetValueTypeEnum.VALUE_OBJECT) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] value type is:[" + targetValueType + "] save path limit only support value object");
            }
        }
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            return entityMetadataManager.getEntityMetadata(navigateMetadata.getMappingClass());
        } else {
            return entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        }
    }

    protected List<NavigateMetadata> getNavigateSavableValueObjects(SavableContext savableContext, Object entity, EntityMetadata entityMetadata, Collection<NavigateMetadata> navigateMetadataList, int deep) {

        List<NavigateMetadata> valueObjects = new ArrayList<>();
        List<NavigateMetadata> aggregateRoots = new ArrayList<>();
        HashSet<NavigateMetadata> navigateMetadataSet = new HashSet<>(entityMetadata.getNavigateMetadatas());
        for (NavigateMetadata navigateMetadata : navigateMetadataList) {

            navigateMetadataSet.remove(navigateMetadata);

            TargetValueTypeEnum targetValueType = getTargetValueType(entityMetadata, navigateMetadata);
            EntityMetadata targetEntityMetadata = checkNavigateContinueAndGetTargetEntityMetadata(targetValueType, navigateMetadata, deep);
            if (targetEntityMetadata == null) {
                continue;
            }
            //如果导航属性是值类型并且没有循环引用且没有被追踪才继续下去
            if (!this.saveCommandContext.circulateCheck(navigateMetadata.getNavigatePropertyType(), deep)) {

                //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
                if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT) {
                    savableContext.putSaveNodeMap(navigateMetadata, targetEntityMetadata);
                    valueObjects.add(navigateMetadata);
                }
                if (targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                    aggregateRoots.add(navigateMetadata);
                }
            }
        }

        //如果目标对象是聚合根则对聚合根的关联属性进行复制到当前对象上
        for (NavigateMetadata navigateMetadata : aggregateRoots) {
            Property<Object, ?> getter = navigateMetadata.getGetter();
            Object aggregateRoot = getter.apply(entity);
            if (aggregateRoot != null) {
                EntityMetadata aggregateRootMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
                setTargetValue(TargetValueTypeEnum.AGGREGATE_ROOT, entity, aggregateRoot, entityMetadata, navigateMetadata, aggregateRootMetadata);
            }
        }
        //检查额外导航属性
        //如果存在手动指定导航保存路径则不检查导航属性
        if (savePathLimit.isEmpty()) {
            for (NavigateMetadata navigateMetadata : navigateMetadataSet) {
                checkNavigatePathTrackedCheck(navigateMetadata, entity, entityMetadata);
            }
        }
        return valueObjects;
    }

    protected void checkNavigatePathTrackedCheck(NavigateMetadata navigate, Object entity, EntityMetadata selfEntityMetadata) {
        Property<Object, ?> getter = navigate.getGetter();
        Object navigateValue = getter.apply(entity);
        if (navigateValue == null) {//未查询
            return;
        }
        if (navigateValue instanceof Collection<?>) {
            Collection<?> navigateValues = (Collection<?>) navigateValue;
            if (EasyCollectionUtil.isEmpty(navigateValues)) {
                //如果是集合那么判断对象初始化的时候
                Object newEntity = selfEntityMetadata.getBeanConstructorCreator().get();
                Object newNavigateValue = getter.apply(newEntity);
                if (newNavigateValue != null) {
                    return;
                }
            }
        }
        throw new EasyQueryInvalidOperationException("The current navigation property [" + EasyClassUtil.getInstanceSimpleName(entity) + "." + navigate.getPropertyName() + "] is not being tracked.");
    }
}
