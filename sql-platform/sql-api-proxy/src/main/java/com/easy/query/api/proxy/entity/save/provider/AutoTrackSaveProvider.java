package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.MemoryAddressCompareValue;
import com.easy.query.api.proxy.entity.save.OwnershipPolicyEnum;
import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveModeEnum;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.TargetValueTypeEnum;
import com.easy.query.api.proxy.entity.save.command.EmptySaveCommand;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.api.proxy.entity.save.command.BasicSaveCommand;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
public class AutoTrackSaveProvider extends AbstractSaveProvider {

    public AutoTrackSaveProvider(TrackContext currentTrackContext, Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient, List<Set<String>> savePathLimit, SaveModeEnum saveMode, OwnershipPolicyEnum ownershipPolicy) {
        super(currentTrackContext, entityClass, entities, easyQueryClient, savePathLimit, saveMode, ownershipPolicy);
    }


    @Override
    public SaveCommand createCommand() {
        if (EasyCollectionUtil.isNotEmpty(entities)) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
            List<Object> inserts = new ArrayList<>();
            List<Object> updates = new ArrayList<>();
            for (Object entity : entities) {
                if (this.saveCommandContext.isProcessEntity(entity)) {
                    throw new EasyQueryInvalidOperationException("entity:" + entity + " has been added to the save command");
                }
                this.saveCommandContext.addProcessEntity(entity);
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(entity);
                if (trackEntityState == null) {
                    inserts.add(entity);
                } else {
                    updates.add(entity);
                }
                saveEntity(entity, entityMetadata, 0);
            }
            return new BasicSaveCommand(entityMetadata, inserts, updates, easyQueryClient, saveCommandContext, saveMode);
        }

        return EmptySaveCommand.INSTANCE;
    }


    private void saveEntity(Object entity, EntityMetadata entityMetadata, int deep) {

        EntityState entityState = currentTrackContext.getTrackEntityState(entity);
        SavableContext savableContext = this.saveCommandContext.getCreateSavableContext(deep);
//        SavableContext savablePathContext = this.savePathCommandContext.getSavableContext(deep);
        //当前追踪的对象是否被追踪 如果被追踪那么应该以追踪上下文的导航值对象解析 如果未被追踪 应该以实体导航属性来获取值对象
        Collection<NavigateMetadata> navigateMetadataList = entityState == null ? entityMetadata.getNavigateMetadatas() : entityState.getIncludes();
        List<NavigateMetadata> valueObjects = getNavigateSavableValueObjects(entityState,savableContext, entity, entityMetadata, navigateMetadataList, deep);
        for (NavigateMetadata navigateMetadata : valueObjects) {
            if (entityState == null) {
                valueObjectInsert(entity, entityMetadata, navigateMetadata, savableContext);
            } else {
                Set<String> trackKeys = entityState.getTrackKeys(navigateMetadata);
                valueObjectUpdate(entity, entityMetadata, navigateMetadata, savableContext, trackKeys == null ? new HashSet<>() : trackKeys);
            }
        }
    }


    private void valueObjectInsert(Object entity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, SavableContext savableContext) {

        if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
            throw new EasyQueryInvalidOperationException("save not support direct mapping");
        }
        EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        SaveNode saveNode = savableContext.getSaveNode(navigateMetadata);


        Property<Object, ?> getter = navigateMetadata.getGetter();
        Object navigates = getter.apply(entity);
        if (navigates instanceof Collection<?>) {
            for (Object targetEntity : (Collection<?>) navigates) {
                valueObjectEntityInsert(entity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        } else {
            if (navigates != null) {
                valueObjectEntityInsert(entity, navigates, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        }
    }

    private void valueObjectEntityInsert(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        saveNodeInsert(selfEntity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);

        if (navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
            saveEntity(targetEntity, targetEntityMetadata, saveNode.getIndex() + 1);
        }

    }

    /**
     * 处理值对象
     *
     * @param entity
     * @param selfEntityMetadata
     * @param navigateMetadata
     * @param savableContext
     * @param trackKeys
     */
    private void valueObjectUpdate(Object entity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, SavableContext savableContext, Set<String> trackKeys) {

        if (trackKeys != null) {
            if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
                throw new EasyQueryInvalidOperationException("save not support direct mapping");
            }
            EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
            SaveNode saveNode = savableContext.getSaveNode(navigateMetadata);
            if (saveNode == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + EasyClassUtil.getSimpleName(navigateMetadata.getNavigatePropertyType()) + "] save node is null");
            }
            Map<String, Object> dbEntityMap = new LinkedHashMap<>();
            for (String trackKey : trackKeys) {
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), trackKey);
                Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + trackKey);
                dbEntityMap.put(trackKey, trackEntityState.getCurrentValue());
            }

            Property<Object, ?> getter = navigateMetadata.getGetter();
            Object navigates = getter.apply(entity);
            if (navigates instanceof Collection<?>) {
                for (Object targetEntity : (Collection<?>) navigates) {
                    valueObjectEntityInsertUpdate(dbEntityMap, entity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
                }
            } else {
                if (navigates != null) {
                    valueObjectEntityInsertUpdate(dbEntityMap, entity, navigates, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
                }
            }
            for (Object value : dbEntityMap.values()) {
                saveNodeDelete(entity, value, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        }
    }


    private void valueObjectEntityInsertUpdate(Map<String, Object> dbEntityMap, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        String newNavigateEntityKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, targetEntity);
        if (newNavigateEntityKey == null || !dbEntityMap.containsKey(newNavigateEntityKey)) {
            saveNodeInsert(selfEntity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
        } else {
            dbEntityMap.remove(newNavigateEntityKey);
            EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), newNavigateEntityKey);
            Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + newNavigateEntityKey);
            if (targetEntity != trackEntityState.getCurrentValue()) {//必须是被追踪对象不然初始化空集合的navigate会有问题视为被删除
                throw new EasyQueryInvalidOperationException("entity:" + targetEntity + " is not same with:" + trackEntityState.getCurrentValue());
            }
            saveNodeUpdate(trackEntityState, selfEntity, targetEntity, targetEntityMetadata, navigateMetadata, saveNode);
        }


        if (navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
            saveEntity(targetEntity, targetEntityMetadata, saveNode.getIndex() + 1);
        }

    }

    private void saveNodeDelete(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        if (navigateMetadata.getCascade() == CascadeTypeEnum.NO_ACTION) {
            return;
        }
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation must have mapping class");
            }
            if (navigateMetadata.getCascade() == CascadeTypeEnum.AUTO) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation mapping class cascade is not set");
            }
            //自动处理中间表
            EntityMetadata mappingClassEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getMappingClass());
            Object mappingEntity = mappingClassEntityMetadata.getBeanConstructorCreator().get();
            setMappingEntity(selfEntity, targetEntity, mappingEntity, selfEntityMetadata, navigateMetadata, targetEntityMetadata, mappingClassEntityMetadata);

            saveNode.getDeleteBys().add(mappingEntity);
        } else {
            if (navigateMetadata.getCascade() == CascadeTypeEnum.AUTO || navigateMetadata.getCascade() == CascadeTypeEnum.SET_NULL) {
                saveNode.putUpdateItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
                    setTargetNullValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, targetEntity, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
                });
            }
            if (navigateMetadata.getCascade() == CascadeTypeEnum.DELETE) {
                saveNode.putDeleteItem(new MemoryAddressCompareValue(targetEntity));
            }
        }
    }

    private void saveNodeUpdate(EntityState trackEntityState, Object selfEntity, Object targetEntity, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        if (navigateMetadata.getCascade() == CascadeTypeEnum.NO_ACTION) {
            return;
        }
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation must have mapping class");
            }
            if (navigateMetadata.getCascade() == CascadeTypeEnum.AUTO) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation mapping class cascade is not set");
            }
            return;
        }

        boolean hasChanged = false;
        for (Map.Entry<String, ColumnMetadata> propColumn : targetEntityMetadata.getProperty2ColumnMap().entrySet()) {
            EntityValueState entityValueState = trackEntityState.getEntityValueState(propColumn.getValue());
            if (entityValueState.isChanged()) {
                hasChanged = true;
                break;
            }
        }
        if (hasChanged) {
            saveNode.putUpdateItem(new MemoryAddressCompareValue(targetEntity), selfEntity, null);
        } else {
            saveNode.putIgnoreUpdateItem(new MemoryAddressCompareValue(targetEntity), selfEntity, null);
        }
    }

    private void saveNodeInsert(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        if (navigateMetadata.getCascade() == CascadeTypeEnum.NO_ACTION) {
            return;
        }
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation must have mapping class");
            }
            if (navigateMetadata.getCascade() == CascadeTypeEnum.AUTO) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation mapping class cascade is not set");
            }
            //自动处理中间表
            EntityMetadata mappingClassEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getMappingClass());
            Object mappingEntity = mappingClassEntityMetadata.getBeanConstructorCreator().get();
            saveNode.putInsertItem(new MemoryAddressCompareValue(mappingEntity), selfEntity, t -> {
                setMappingEntity(selfEntity, targetEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata, mappingClassEntityMetadata);
            });
        } else {
            saveNode.putInsertItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
                setTargetValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
            });
        }
    }
}
