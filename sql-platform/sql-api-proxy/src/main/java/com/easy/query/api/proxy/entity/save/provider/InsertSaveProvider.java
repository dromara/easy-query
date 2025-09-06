package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.TargetValueTypeEnum;
import com.easy.query.api.proxy.entity.save.abstraction.AbstractEntitySavable;
import com.easy.query.api.proxy.entity.save.command.EmptySaveCommand;
import com.easy.query.api.proxy.entity.save.command.InsertSaveCommand;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MappingClassSaveModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * create time 2025/9/6 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertSaveProvider implements SaveProvider {
    private final Class<?> entityClass;
    private final List<Object> entities;
    private final EasyQueryClient easyQueryClient;
    private final QueryRuntimeContext runtimeContext;
    private final EntityMetadataManager entityMetadataManager;
    private final TrackContext currentTrackContext;
    private final SaveCommandContext saveCommandContext;

    public InsertSaveProvider(Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient) {
        this.entityClass = entityClass;
        this.entities = entities;
        this.easyQueryClient = easyQueryClient;
        this.runtimeContext = easyQueryClient.getRuntimeContext();
        TrackContext currentTrackContext = runtimeContext.getTrackManager().getCurrentTrackContext();
        if (currentTrackContext == null) {
            throw new EasyQueryInvalidOperationException("currentTrackContext can not be null");
        }
        this.currentTrackContext = currentTrackContext;
        this.entityMetadataManager = runtimeContext.getEntityMetadataManager();
        this.saveCommandContext = new SaveCommandContext(entityClass);
    }

    @Override
    public SaveCommand createCommand() {

        if (EasyCollectionUtil.isNotEmpty(entities)) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
            for (Object entity : entities) {
                //insert 拦截器
                saveEntity(entity, entityMetadata, 0);
            }
            return new InsertSaveCommand(entityMetadata, entities, easyQueryClient, saveCommandContext);
        }

        return EmptySaveCommand.INSTANCE;
    }

    //未追踪的数据建议采用insert or update的做法
    private void saveEntity(Object entity, EntityMetadata entityMetadata, int deep) {

        EntityState entityState = currentTrackContext.getTrackEntityState(entity);
        if (entityState != null) {
            throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getSimpleName(entity.getClass()) + " has been tracked");
        }
        SavableContext savableContext = this.saveCommandContext.getSavableContext(deep);
        for (NavigateMetadata navigateMetadata : entityMetadata.getNavigateMetadatas()) {
            TargetValueTypeEnum targetValueType = getTargetValueType(entityMetadata, navigateMetadata);
            //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
            if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT || targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                processNavigate(targetValueType, entity, entityMetadata, navigateMetadata, savableContext);
            }
        }
//        List<NavigateMetadata> includes = entityState.getIncludes();
//        if (includes != null) {
//            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity.getClass());
//            for (NavigateMetadata include : includes) {
//                TargetValueTypeEnum targetValueType = getTargetValueType(entityMetadata, include);
//
//                //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
//                if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT || targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
//                    processNavigate(targetValueType, entity, include, savableContext, () -> entityState.getTrackKeys(include));
//                }
//            }
//        }
    }

    private void processNavigate(TargetValueTypeEnum targetValueType, Object entity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, SavableContext savableContext) {

        if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
            throw new EasyQueryInvalidOperationException("save not support direct mapping");
        }
        EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        SaveNode saveNode = savableContext.createSaveNodeMap(navigateMetadata, targetEntityMetadata);


        Property<Object, ?> getter = navigateMetadata.getGetter();
        Object navigates = getter.apply(entity);
        if (navigates instanceof Collection<?>) {
            if (targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                throw new EasyQueryInvalidOperationException("save collection not support aggregate root");
            }
            for (Object targetEntity : (Collection<?>) navigates) {
                processEntity(targetValueType, entity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        } else {
            if (navigates != null) {
                processEntity(targetValueType, entity, navigates, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        }
    }

    private void processEntity(TargetValueTypeEnum targetValueType, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("many to many relation must have mapping class");
            }
            if (navigateMetadata.getMappingClassSaveMode() == MappingClassSaveModeEnum.THROW) {
                throw new EasyQueryInvalidOperationException("many to many relation mapping class save mode is throw");
            }
            //自动处理中间表
            if (navigateMetadata.getMappingClassSaveMode() == MappingClassSaveModeEnum.AUTO) {
                EntityMetadata mappingClassEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getMappingClass());
                Object mappingEntity = mappingClassEntityMetadata.getBeanConstructorCreator().get();
                saveNode.getInserts().add(new SaveNode.InsertItem(mappingEntity, t -> {
                    setMappingEntity(selfEntity, targetEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata, mappingClassEntityMetadata);
                }));

            }


        } else {
            saveNode.getInserts().add(new SaveNode.InsertItem(targetEntity, t -> {
                setTargetValue(targetValueType, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
            }));
            if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT) {
                //如果导航属性是值类型并且没有循环引用且没有被追踪才继续下去
                if (!this.saveCommandContext.circulateCheck(navigateMetadata.getNavigatePropertyType())) {
                    EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType());
                    if (trackEntityState == null) {
                        saveEntity(targetEntity, targetEntityMetadata, saveNode.getIndex() + 1);
                    }
                }
            }
        }
    }

    private void setMappingEntity(Object selfEntity, Object targetEntity, Object mappingEntity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, EntityMetadata targetEntityMetadata, EntityMetadata mappingEntityMetadata) {
        String[] selfMappingProperties = navigateMetadata.getSelfMappingProperties();
        String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
        for (int i = 0; i < selfMappingProperties.length; i++) {
            String selfMappingProperty = selfMappingProperties[i];
            String self = selfPropertiesOrPrimary[i];
            ColumnMetadata selfColumn = selfEntityMetadata.getColumnNotNull(self);
            Object selfValue = selfColumn.getGetterCaller().apply(selfEntity);
            if (selfValue == null) {
                throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getInstanceSimpleName(selfEntity) + " property:[" + self + "] value can not be null");
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
                throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getInstanceSimpleName(targetEntity) + " property:[" + target + "] value can not be null");
            }
            ColumnMetadata columnMetadata = mappingEntityMetadata.getColumnNotNull(targetMappingProperty);
            columnMetadata.getSetterCaller().call(mappingEntity, targetValue);
        }
    }

    private void setTargetValue(TargetValueTypeEnum targetValueType, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, EntityMetadata targetEntityMetadata) {
        if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT) {
            String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
            for (int i = 0; i < selfPropertiesOrPrimary.length; i++) {
                String self = selfPropertiesOrPrimary[i];
                String target = targetPropertiesOrPrimary[i];
                ColumnMetadata selfColumn = selfEntityMetadata.getColumnNotNull(self);
                Object selfValue = selfColumn.getGetterCaller().apply(selfEntity);
                if (selfValue == null) {
                    throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getInstanceSimpleName(selfEntity) + " property:[" + self + "] value can not be null");
                }
                ColumnMetadata targetColumn = selfEntityMetadata.getColumnNotNull(target);
                targetColumn.getSetterCaller().call(targetEntity, selfValue);
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
                    throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getInstanceSimpleName(targetEntity) + " property:[" + target + "] value can not be null");
                }
                ColumnMetadata selfColumn = selfEntityMetadata.getColumnNotNull(self);
                selfColumn.getSetterCaller().call(selfEntity, targetValue);
            }
        } else {
            throw new EasyQueryInvalidOperationException("save not support target value type:" + targetValueType);
        }
    }

    private TargetValueTypeEnum getTargetValueType(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {
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

    private boolean selfPropsIsKeys(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {

        String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
        Collection<String> keyProperties = selfMetadata.getKeyProperties();
        return EasyArrayUtil.any(selfPropertiesOrPrimary, prop -> keyProperties.contains(prop));
    }

    private boolean targetPropsIsKeys(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {

        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
        Collection<String> keyProperties = selfMetadata.getKeyProperties();
        return EasyArrayUtil.any(targetPropertiesOrPrimary, prop -> keyProperties.contains(prop));
    }
}
