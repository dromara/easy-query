package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.TargetValueTypeEnum;
import com.easy.query.api.proxy.entity.save.command.EmptySaveCommand;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.api.proxy.entity.save.command.UpdateSaveCommand;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.enums.MappingClassSaveModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2025/9/6 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateSaveProvider extends AbstractSaveProvider {
    private final SaveCommandContext saveCommandContext;
    private SaveCommandContext savePathCommandContext;

    public UpdateSaveProvider(Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient) {
        super(entityClass, entities, easyQueryClient);
        this.saveCommandContext = new SaveCommandContext(entityClass);
    }

    @Override
    public SaveCommand createCommand() {
        if (EasyCollectionUtil.isNotEmpty(entities)) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
            for (Object entity : entities) {
                //添加重复引用去重
                String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
                this.savePathCommandContext = new SaveCommandContext(entityClass);
                createIncludePath(entityClass, entityMetadata, trackKey, 0);

                this.saveCommandContext.addProcessEntity(entity);
                saveEntity(entity, entityMetadata, 0);
            }
            return new UpdateSaveCommand(entityMetadata, entities, easyQueryClient, saveCommandContext);
        }

        return EmptySaveCommand.INSTANCE;
    }

    private void createIncludePath(Class<?> entityClass, EntityMetadata entityMetadata, String trackKey, int deep) {
        EntityState entityState = currentTrackContext.getTrackEntityState(entityClass, trackKey);
        if (entityState != null) {
            SavableContext savableContext = this.savePathCommandContext.getSavableContext(deep);
            List<NavigateMetadata> includes = entityState.getIncludes();
            if (includes != null) {
                for (NavigateMetadata include : includes) {//navigate必须是被追踪查询了的否则忽略
                    if (EasyArrayUtil.isNotEmpty(include.getDirectMapping())) {
                        return;
                    }
                    //如果导航属性是值类型并且没有循环引用且没有被追踪才继续下去
                    if (!this.savePathCommandContext.circulateCheck(include.getNavigatePropertyType(), deep)) {
                        TargetValueTypeEnum targetValueType = getTargetValueType(entityMetadata, include);
                        //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
                        if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT || targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                            createNavigatePath(include, savableContext, entityState.getTrackKeys(include));
                        }
                    }
                }
            }
        }
    }

    private void createNavigatePath(NavigateMetadata navigateMetadata, SavableContext savableContext, Set<String> trackKeys) {

        EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        SaveNode saveNode = savableContext.putSaveNodeMap(navigateMetadata, targetEntityMetadata);
        for (String trackKey : trackKeys) {
            if (trackKey != null) {
                createIncludePath(targetEntityMetadata.getEntityClass(), targetEntityMetadata, trackKey, saveNode.getIndex() + 1);
            }
        }
    }


    private void saveEntity(Object entity, EntityMetadata entityMetadata, int deep) {

        EntityState entityState = currentTrackContext.getTrackEntityState(entity);
        SavableContext savableContext = this.saveCommandContext.getSavableContext(deep);
        SavableContext pathSavableContext = this.savePathCommandContext.getSavableContext(deep);
        if (entityState == null) {
            for (NavigateMetadata navigateMetadata : entityMetadata.getNavigateMetadatas()) {
                SaveNode saveNode = pathSavableContext.getSaveNode(navigateMetadata);
                if (saveNode == null) {
                    continue;
                }
                savableContext.putSaveNodeMap(navigateMetadata, saveNode.getEntityMetadata());
                //如果导航属性是值类型并且没有循环引用且没有被追踪才继续下去
                if (!this.saveCommandContext.circulateCheck(navigateMetadata.getNavigatePropertyType(), deep)) {
                    TargetValueTypeEnum targetValueType = getTargetValueType(entityMetadata, navigateMetadata);
                    //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
                    if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT || targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                        processNavigate(targetValueType, entity, entityMetadata, navigateMetadata, savableContext, new HashSet<>());
                    }
                }
            }
            return;
        }
        List<NavigateMetadata> includes = entityState.getIncludes();
        if (includes != null) {
            for (NavigateMetadata include : includes) {
                SaveNode saveNode = pathSavableContext.getSaveNode(include);
                if (saveNode == null) {
                    continue;
                }
                savableContext.putSaveNodeMap(include, saveNode.getEntityMetadata());
                //如果导航属性是值类型并且没有循环引用且没有被追踪才继续下去
                if (!this.saveCommandContext.circulateCheck(include.getNavigatePropertyType(), deep)) {

                    TargetValueTypeEnum targetValueType = getTargetValueType(entityMetadata, include);

                    //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
                    if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT || targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                        processNavigate(targetValueType, entity, entityMetadata, include, savableContext, entityState.getTrackKeys(include));
                    }
                }
            }
        }
    }

    private void processNavigate(TargetValueTypeEnum targetValueType, Object entity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, SavableContext savableContext, Set<String> trackKeys) {
        //navigate必须是被追踪查询了的否则忽略
        if (trackKeys != null) {
            if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
                throw new EasyQueryInvalidOperationException("save not support direct mapping");
            }
            EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
            SaveNode saveNode = savableContext.getSaveNode(navigateMetadata);
            Map<String, Object> dbEntityMap = new HashMap<>();
            for (String trackKey : trackKeys) {
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), trackKey);
                Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + trackKey);
                dbEntityMap.put(trackKey, trackEntityState.getOriginalValue());
            }

            Property<Object, ?> getter = navigateMetadata.getGetter();
            Object navigates = getter.apply(entity);
            if (navigates instanceof Collection<?>) {
                if (targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                    throw new EasyQueryInvalidOperationException("save collection not support aggregate root");
                }
                for (Object targetEntity : (Collection<?>) navigates) {
                    processEntity(targetValueType, dbEntityMap, entity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
                }
            } else {
                if (navigates != null) {
                    processEntity(targetValueType, dbEntityMap, entity, navigates, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
                }
            }
            for (Object value : dbEntityMap.values()) {
                saveNodeDelete(entity, value, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        }
    }


    private void processEntity(TargetValueTypeEnum targetValueType, Map<String, Object> dbEntityMap, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        if (this.saveCommandContext.isProcessEntity(targetEntity)) {
            return;
        }
        this.saveCommandContext.addProcessEntity(targetEntity);
        String newNavigateEntityKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, targetEntity);
        if (newNavigateEntityKey == null || !dbEntityMap.containsKey(newNavigateEntityKey)) {
            saveNodeInsert(targetValueType, selfEntity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
        } else {
            dbEntityMap.remove(newNavigateEntityKey);
            EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), newNavigateEntityKey);
            Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + newNavigateEntityKey);
            if (targetEntity != trackEntityState.getCurrentValue()) {//必须是被追踪对象不然初始化空集合的navigate会有问题视为被删除
                throw new EasyQueryInvalidOperationException("entity:" + targetEntity + " is not same with:" + trackEntityState.getCurrentValue());
            }
            saveNodeUpdate(trackEntityState, targetEntity, targetEntityMetadata, navigateMetadata, saveNode);
        }


        if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
            EntityState trackEntityState = currentTrackContext.getTrackEntityState(targetEntity);
            if (trackEntityState != null) {
                saveEntity(targetEntity, targetEntityMetadata, saveNode.getIndex() + 1);
            }
        }

    }

    private void saveNodeDelete(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {
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
                setMappingEntity(selfEntity, targetEntity, mappingEntity, selfEntityMetadata, navigateMetadata, targetEntityMetadata, mappingClassEntityMetadata);
                saveNode.getDeleteBys().add(mappingEntity);
            }
        } else {
            saveNode.getDeletes().add(targetEntity);
        }
    }

    private void saveNodeUpdate(EntityState trackEntityState, Object targetEntity, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("many to many relation must have mapping class");
            }
            if (navigateMetadata.getMappingClassSaveMode() == MappingClassSaveModeEnum.THROW) {
                throw new EasyQueryInvalidOperationException("many to many relation mapping class save mode is throw");
            }
            return;
        }
        for (Map.Entry<String, ColumnMetadata> propColumn : targetEntityMetadata.getProperty2ColumnMap().entrySet()) {
            EntityValueState entityValueState = trackEntityState.getEntityValueState(propColumn.getValue());
            if (entityValueState.isChanged()) {
                saveNode.getUpdates().add(targetEntity);
                break;
            }
        }
    }

    private void saveNodeInsert(TargetValueTypeEnum targetValueType, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {
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
        }
    }

}
