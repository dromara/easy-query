package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.DatabaseEntityValues;
import com.easy.query.api.proxy.entity.save.MemoryAddressCompareValue;
import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveBehavior;
import com.easy.query.api.proxy.entity.save.SaveBehaviorEnum;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.SaveNodeDbTypeEnum;
import com.easy.query.api.proxy.entity.save.SaveNodeTypeEnum;
import com.easy.query.api.proxy.entity.save.TargetValueTypeEnum;
import com.easy.query.api.proxy.entity.save.command.BasicSaveCommand;
import com.easy.query.api.proxy.entity.save.command.EmptySaveCommand;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.api.proxy.entity.save.diff.EntityDiffNode;
import com.easy.query.api.proxy.entity.save.diff.PropertyDiffNode;
import com.easy.query.api.proxy.entity.save.diff.PropertyKey;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.core.tuple.Tuple3;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyTrackUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
public class AutoTrackSaveProvider2 extends AbstractSaveProvider2 {

    public AutoTrackSaveProvider2(TrackContext currentTrackContext, Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient, List<Set<String>> savePathLimit, SaveBehavior saveBehavior, boolean removeRoot) {
        super(currentTrackContext, entityClass, entities, easyQueryClient, savePathLimit, saveBehavior, removeRoot);
    }


    @Override
    public SaveCommand createCommand() {
        if (EasyCollectionUtil.isNotEmpty(entities)) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
            //幂等构建NavigateDiffNode
            PropertyKey propertyKey = new PropertyKey(null, entityMetadata.getEntityClass(), null);
            PropertyDiffNode propertyDiffNode = new PropertyDiffNode(propertyKey, entityMetadata, null, 0);

            for (Object entity : entities) {
                if (this.saveCommandContext.isProcessEntity(entity)) {
                    throw new EasyQueryInvalidOperationException("entity:" + entity + " has been added to the save command");
                }
                this.saveCommandContext.addProcessEntity(entity);
                EntityState entityState = currentTrackContext.getTrackEntityState(entity);
                processSaveDiffNode(propertyDiffNode, null, entity, entityState, entityMetadata, null);

                System.out.println(propertyDiffNode);

            }

            return EmptySaveCommand.INSTANCE;
//            return new BasicSaveCommand(entityMetadata, inserts, updates, easyQueryClient, saveCommandContext, saveBehavior, removeRoot);
        }

        return EmptySaveCommand.INSTANCE;
    }


    private void processSaveDiffNode(@NotNull PropertyDiffNode parentNode, Class<?> from, @Nullable Object after, @Nullable EntityState trackEntityState, EntityMetadata entityMetadata, NavigateMetadata entityNavigateMetadata) {
        if (after == null && trackEntityState == null) {
            return;
        }
        //幂等构建NavigateDiffNode
        PropertyKey propertyKey = new PropertyKey(from, entityMetadata.getEntityClass(), entityNavigateMetadata);
        PropertyDiffNode diffNode = new PropertyDiffNode(propertyKey, entityMetadata, entityNavigateMetadata, parentNode.getDeep() + 1);
        //获取当前的导航节点
        PropertyDiffNode propertyDiffNode = parentNode.addChild(diffNode);
        Object before = getTrackBeforeEntity(trackEntityState);
        EntityDiffNode entityDiffNode = new EntityDiffNode(before, after, propertyDiffNode);
        propertyDiffNode.getDiffNodes().add(entityDiffNode);

        for (NavigateMetadata navigateMetadata : entityMetadata.getNavigateMetadatas()) {

            if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
                continue;
            }
            EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
            if (entityDiffNode.isInsert()) {

                Property<Object, ?> getter = navigateMetadata.getGetter();
                Object navigates = getter.apply(entityDiffNode.getAfter());
                if (navigates != null) {
                    if (navigates instanceof Collection<?>) {
                        Collection<?> collection = (Collection<?>) navigates;
                        if (!(collection.isEmpty() && saveBehavior.hasBehavior(SaveBehaviorEnum.IGNORE_EMPTY))) {
                            for (Object targetEntity : collection) {
                                processSaveDiffNode(propertyDiffNode, entityMetadata.getEntityClass(), targetEntity, currentTrackContext.getTrackEntityState(targetEntity), targetEntityMetadata, navigateMetadata);
                            }
                        }
                    } else {
                        processSaveDiffNode(propertyDiffNode, entityMetadata.getEntityClass(), navigates, currentTrackContext.getTrackEntityState(navigates), targetEntityMetadata, navigateMetadata);
                    }
                }
            } else if (entityDiffNode.isUpdate()) {
                if (trackEntityState == null) {
                    throw new EasyQueryInvalidOperationException("trackEntityState is null");
                }

                Set<String> trackKeys = trackEntityState.getTrackKeys(navigateMetadata, new HashSet<>());
                if (EasyCollectionUtil.isNotEmpty(trackKeys)) {

                    DatabaseEntityValues databaseEntityValues = new DatabaseEntityValues(navigateMetadata, targetEntityMetadata, runtimeContext);
                    //本次查询出来的结果有这么多
                    for (String trackKey : trackKeys) {
                        EntityState entityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), trackKey);
                        Objects.requireNonNull(entityState, "trackEntityState cant be null,trackKey:" + trackKey);
                        databaseEntityValues.put(trackKey, entityState.getCurrentValue());
                    }

                    Property<Object, ?> getter = navigateMetadata.getGetter();
                    Object navigates = getter.apply(entityDiffNode.getAfter());
                    if (navigates instanceof Collection<?>) {
                        Collection<?> collection = (Collection<?>) navigates;
                        if (!(collection.isEmpty() && saveBehavior.hasBehavior(SaveBehaviorEnum.IGNORE_EMPTY))) {
                            for (Object targetEntity : collection) {
                                databaseEntityValues.checkSaveKeyRepeat(targetEntity);
                                mergeEntityIfUpdate(databaseEntityValues, targetEntity, targetEntityMetadata);
                                processSaveDiffNode(propertyDiffNode, entityMetadata.getEntityClass(), targetEntity, currentTrackContext.getTrackEntityState(targetEntity), targetEntityMetadata, navigateMetadata);

                            }
                        }
                    } else {
                        if (navigates != null) {
                            databaseEntityValues.checkSaveKeyRepeat(navigates);
                            mergeEntityIfUpdate(databaseEntityValues, navigates, targetEntityMetadata);
                            processSaveDiffNode(propertyDiffNode, entityMetadata.getEntityClass(), navigates, currentTrackContext.getTrackEntityState(navigates), targetEntityMetadata, navigateMetadata);
                        }
                    }
                    for (Object value : databaseEntityValues.values()) {
                        String trackKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, value);
                        EntityState entityState = currentTrackContext.getTrackEntityState(targetEntityMetadata.getEntityClass(), trackKey);
                        processSaveDiffNode(propertyDiffNode, entityMetadata.getEntityClass(), null, entityState, targetEntityMetadata, navigateMetadata);

                    }

                }
            } else if (entityDiffNode.isDelete()) {

                Property<Object, ?> getter = navigateMetadata.getGetter();
                Object navigates = getter.apply(entityDiffNode.getBefore());
                if (navigates != null) {
                    if (navigates instanceof Collection<?>) {
                        Collection<?> collection = (Collection<?>) navigates;
                        if (!(collection.isEmpty() && saveBehavior.hasBehavior(SaveBehaviorEnum.IGNORE_EMPTY))) {
                            for (Object targetEntity : collection) {
                                String trackKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, targetEntity);
                                EntityState entityState = currentTrackContext.getTrackEntityState(targetEntityMetadata.getEntityClass(), trackKey);
                                processSaveDiffNode(propertyDiffNode, entityMetadata.getEntityClass(), null, entityState, targetEntityMetadata, navigateMetadata);
                            }
                        }
                    } else {
                        String trackKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, navigates);
                        EntityState entityState = currentTrackContext.getTrackEntityState(targetEntityMetadata.getEntityClass(), trackKey);
                        processSaveDiffNode(propertyDiffNode, entityMetadata.getEntityClass(), null, entityState, targetEntityMetadata, navigateMetadata);
                    }
                }
            }
        }

    }

    private Object getTrackBeforeEntity(@Nullable EntityState entityState) {
        if (entityState != null) {
            return entityState.getOriginalValue();
        }
        return null;
    }


    private void cleanNavigates(Object entity, EntityMetadata entityMetadata) {
        if (removeRoot) {
            Collection<NavigateMetadata> navigateMetadatas = entityMetadata.getNavigateMetadatas();
            for (NavigateMetadata navigateMetadata : navigateMetadatas) {
                navigateMetadata.getSetter().call(entity, null);
            }
        }
    }


    private void saveSelf(Object entity, EntityState entityState, EntityMetadata entityMetadata, int deep) {

        SavableContext savableContext = this.saveCommandContext.getCreateSavableContext(deep);
//        SavableContext savablePathContext = this.savePathCommandContext.getSavableContext(deep);
        //当前追踪的对象是否被追踪 如果被追踪那么应该以追踪上下文的导航值对象解析 如果未被追踪 应该以实体导航属性来获取值对象
        Collection<NavigateMetadata> navigateMetadataList = entityState == null ? entityMetadata.getNavigateMetadatas() : entityState.getIncludes();
        List<NavigateMetadata> valueObjects = getNavigateSavableValueObjects(entityState, savableContext, entity, entityMetadata, navigateMetadataList, deep);
        for (NavigateMetadata navigateMetadata : valueObjects) {
            if (entityState == null) {
                valueObjectInsert(entity, entityMetadata, navigateMetadata, savableContext);
            } else {
                Set<String> trackKeys = entityState.getTrackKeys(navigateMetadata);
                valueObjectUpdate(entity, entityMetadata, navigateMetadata, savableContext, trackKeys == null ? new HashSet<>() : trackKeys);
            }
        }
    }

    private void deleteSelf(Object entity, EntityMetadata entityMetadata, int deep) {

        EntityState entityState = currentTrackContext.getTrackEntityState(entity);
        if (entityState != null) {

            SavableContext savableContext = this.saveCommandContext.getCreateSavableContext(deep);
            Collection<NavigateMetadata> navigateMetadataList = entityState.getIncludes();
            List<NavigateMetadata> valueObjects = getNavigateSavableValueObjects(entityState, savableContext, entity, entityMetadata, navigateMetadataList, deep);
            for (NavigateMetadata navigateMetadata : valueObjects) {
                Set<String> trackKeys = entityState.getTrackKeys(navigateMetadata);
                //改成valueObjectDelete
                valueObjectDelete(entity, entityMetadata, navigateMetadata, savableContext, trackKeys == null ? new HashSet<>() : trackKeys);
            }
        }
    }

    private void valueObjectDelete(Object entity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, SavableContext savableContext, Set<String> trackKeys) {

        if (trackKeys != null) {
            if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
                return;
            }
            EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
            SaveNode saveNode = savableContext.getSaveNode(navigateMetadata);
            if (saveNode == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + EasyClassUtil.getSimpleName(navigateMetadata.getNavigatePropertyType()) + "] save node is null");
            }
            for (String trackKey : trackKeys) {
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), trackKey);
                Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + trackKey);
                saveNodeDelete(entity, trackEntityState.getCurrentValue(), selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode, false);
                deleteSelf(trackEntityState.getCurrentValue(), targetEntityMetadata, saveNode.getIndex() + 1);
            }
        }
    }


    private void valueObjectInsert(Object entity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, SavableContext savableContext) {

        if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
            throw new EasyQueryInvalidOperationException("save not support direct mapping");
        }
        EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        SaveNode saveNode = savableContext.getSaveNode(navigateMetadata);


        DatabaseEntityValues databaseEntityValues = new DatabaseEntityValues(navigateMetadata, targetEntityMetadata, runtimeContext);
        Property<Object, ?> getter = navigateMetadata.getGetter();
        Object navigates = getter.apply(entity);
        if (navigates instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) navigates;
            if (collection.isEmpty() && saveBehavior.hasBehavior(SaveBehaviorEnum.IGNORE_EMPTY)) {
                return;
            }
            for (Object targetEntity : collection) {
                databaseEntityValues.checkSaveKeyRepeat(targetEntity);
                valueObjectEntityInsert(entity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        } else {
            if (navigates != null) {
                databaseEntityValues.checkSaveKeyRepeat(navigates);
                valueObjectEntityInsert(entity, navigates, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        }
    }

    private void valueObjectEntityInsert(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        saveNodeInsert(selfEntity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);

        if (navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany && navigateMetadata.getCascade() == CascadeTypeEnum.DELETE) {

            EntityState entityState = currentTrackContext.getTrackEntityState(targetEntity);
            saveSelf(targetEntity, entityState, targetEntityMetadata, saveNode.getIndex() + 1);
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
            DatabaseEntityValues databaseEntityValues = new DatabaseEntityValues(navigateMetadata, targetEntityMetadata, runtimeContext);
            //本次查询出来的结果有这么多
            for (String trackKey : trackKeys) {
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), trackKey);
                Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + trackKey);
                databaseEntityValues.put(trackKey, trackEntityState.getCurrentValue());
            }

            Property<Object, ?> getter = navigateMetadata.getGetter();
            Object navigates = getter.apply(entity);
            if (navigates instanceof Collection<?>) {
                Collection<?> collection = (Collection<?>) navigates;
                if (collection.isEmpty() && saveBehavior.hasBehavior(SaveBehaviorEnum.IGNORE_EMPTY)) {
                    return;
                }
                for (Object targetEntity : collection) {
                    databaseEntityValues.checkSaveKeyRepeat(targetEntity);
                    valueObjectEntityInsertUpdate(databaseEntityValues, entity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
                }
            } else {
                if (navigates != null) {
                    databaseEntityValues.checkSaveKeyRepeat(navigates);
                    valueObjectEntityInsertUpdate(databaseEntityValues, entity, navigates, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
                } else {
                    if (saveBehavior.hasBehavior(SaveBehaviorEnum.IGNORE_NULL)) {
                        return;
                    }
                }
            }
            for (Object value : databaseEntityValues.values()) {
                saveNodeDelete(entity, value, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode, true);
            }
        }
    }


    private void valueObjectEntityInsertUpdate(DatabaseEntityValues databaseEntityValues, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        EntityState entityState = null;
        String newNavigateEntityKey = databaseEntityValues.getTrackKey(targetEntity);
        if (newNavigateEntityKey == null || !databaseEntityValues.containsKey(newNavigateEntityKey)) {
            saveNodeInsert(selfEntity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            entityState = currentTrackContext.getTrackEntityState(targetEntity);
        } else {
            databaseEntityValues.remove(newNavigateEntityKey);
            EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), newNavigateEntityKey);
            Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + newNavigateEntityKey);
            if (targetEntity != trackEntityState.getCurrentValue()) {//必须是被追踪对象不然初始化空集合的navigate会有问题视为被删除
                mergeEntityWithTrackEntity(targetEntity, trackEntityState.getCurrentValue(), targetEntityMetadata);
            }
            saveNodeUpdate(trackEntityState, selfEntity, trackEntityState.getCurrentValue(), selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            entityState = currentTrackContext.getTrackEntityState(trackEntityState.getCurrentValue());
        }


        if (navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany && navigateMetadata.getCascade() == CascadeTypeEnum.DELETE) {
            saveSelf(targetEntity, entityState, targetEntityMetadata, saveNode.getIndex() + 1);
        }

    }

    private void mergeEntityIfUpdate(DatabaseEntityValues databaseEntityValues, Object targetEntity, EntityMetadata targetEntityMetadata) {

        String newNavigateEntityKey = databaseEntityValues.getTrackKey(targetEntity);
        if (newNavigateEntityKey != null && databaseEntityValues.containsKey(newNavigateEntityKey)) {
            databaseEntityValues.remove(newNavigateEntityKey);
            EntityState trackEntityState = currentTrackContext.getTrackEntityState(targetEntityMetadata.getEntityClass(), newNavigateEntityKey);
            Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + newNavigateEntityKey);
            if (targetEntity != trackEntityState.getCurrentValue()) {//必须是被追踪对象不然初始化空集合的navigate会有问题视为被删除
                mergeEntityWithTrackEntity(targetEntity, trackEntityState.getCurrentValue(), targetEntityMetadata);
            }
        }
    }

    private void mergeEntityWithTrackEntity(Object source, Object currentEntity, EntityMetadata entityMetadata) {

        for (Map.Entry<String, ColumnMetadata> propColumn : entityMetadata.getProperty2ColumnMap().entrySet()) {
            String key = propColumn.getKey();
            ColumnMetadata columnMetadata = propColumn.getValue();
            Object sourceValue = columnMetadata.getGetterCaller().apply(source);
            boolean keyProperty = entityMetadata.isKeyProperty(key);
            if (keyProperty) {
                if (sourceValue == null) {
                    Object value = columnMetadata.getGetterCaller().apply(currentEntity);
                    columnMetadata.getSetterCaller().call(source, value);
                }
            } else {
                columnMetadata.getSetterCaller().call(currentEntity, sourceValue);
            }
        }
    }

    private void saveNodeDelete(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode, boolean first) {

        if (navigateMetadata.getCascade() == CascadeTypeEnum.NO_ACTION) {
            return;
        }
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation must have mapping class");
            }
            //自动处理中间表
            EntityMetadata mappingClassEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getMappingClass());
            Object mappingEntity = mappingClassEntityMetadata.getBeanConstructorCreator().get();
            setMappingEntity(selfEntity, targetEntity, mappingEntity, selfEntityMetadata, navigateMetadata, targetEntityMetadata, mappingClassEntityMetadata);

            saveNode.getDeleteBys().add(mappingEntity);
        } else {

            if (navigateMetadata.getCascade() == CascadeTypeEnum.AUTO || navigateMetadata.getCascade() == CascadeTypeEnum.SET_NULL) {
                //检查目标是否也是全主键

                boolean prosHasKey = targetAnyPropsIsKey(selfEntityMetadata, navigateMetadata);
                if (prosHasKey) {
                    throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(selfEntityMetadata.getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] targetProperty has key props,cascade cant use set null");
                }
                saveNode.putDeleteItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
                    setTargetNullValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, targetEntity, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
                }, SaveNodeDbTypeEnum.UPDATE);

            }
            if (navigateMetadata.getCascade() == CascadeTypeEnum.DELETE) {
                MemoryAddressCompareValue deleteEntity = new MemoryAddressCompareValue(targetEntity);
                saveNode.putDeleteItem(deleteEntity, null, null, SaveNodeDbTypeEnum.DELETE);
                if (first) {
                    DeleteValueObject deleteValueObject = new DeleteValueObject(targetEntity, targetEntityMetadata, saveNode);
                    deleteValueObjectMap.computeIfAbsent(deleteEntity, k -> deleteValueObject);
                }
            }
        }
    }


    private void saveNodeUpdate(EntityState trackEntityState, Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        if (navigateMetadata.getCascade() == CascadeTypeEnum.NO_ACTION) {
            return;
        }
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation must have mapping class");
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
            saveNode.putUpdateItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
                setTargetValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
            });
        } else {
            saveNode.putIgnoreUpdateItem(new MemoryAddressCompareValue(targetEntity), selfEntity, null);
        }
    }

    private void saveNodeInsert(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        if (navigateMetadata.getCascade() == CascadeTypeEnum.NO_ACTION) {
            return;
        }
        //多对多
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass()) + "." + navigateMetadata.getPropertyName() + "] many to many relation must have mapping class");
            }
            if (navigateMetadata.getCascade() == CascadeTypeEnum.DELETE) {
                //自动处理中间表
                EntityMetadata mappingClassEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getMappingClass());
                Object mappingEntity = mappingClassEntityMetadata.getBeanConstructorCreator().get();
                saveNode.putInsertItem(new MemoryAddressCompareValue(mappingEntity), selfEntity, t -> {
                    setMappingEntity(selfEntity, targetEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata, mappingClassEntityMetadata);
                });
            }
        } else {

            if (navigateMetadata.getCascade() == CascadeTypeEnum.AUTO || navigateMetadata.getCascade() == CascadeTypeEnum.SET_NULL) {
                String trackKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, targetEntity);
                if (trackKey == null) {
                    throw new EasyQueryInvalidOperationException("entity:[" + selfEntityMetadata.getEntityClass() + "." + navigateMetadata.getPropertyName() + "] track key is null,entity:[" + targetEntity + "]");
                }
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(targetEntityMetadata.getEntityClass(), trackKey);
                if (trackEntityState == null) {
                    throw new EasyQueryInvalidOperationException("entity:[" + selfEntityMetadata.getEntityClass() + "." + navigateMetadata.getPropertyName() + "] track key:[" + trackKey + "] not found in track context");
                }
                saveNode.putUpdateItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
                    setTargetValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
                });

//                String trackKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, targetEntity);
//                EntityState trackEntityState = currentTrackContext.getTrackEntityState(targetEntityMetadata.getEntityClass(), trackKey);
//                if (trackEntityState != null) {
//                    saveNode.putUpdateItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
//                        setTargetValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
//                    });
//                }else{
//                    saveNode.putInsertItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
//                        setTargetValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
//                    });
//                }
            } else if (navigateMetadata.getCascade() == CascadeTypeEnum.DELETE) {

                String trackKey = EasyTrackUtil.getTrackKey(targetEntityMetadata, targetEntity);
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(targetEntityMetadata.getEntityClass(), trackKey);
                if (trackEntityState != null) {
                    //换了父级id那么插入应该用
                    Object currentValue = trackEntityState.getCurrentValue();
                    if (targetEntity != currentValue) {//必须是被追踪对象不然初始化空集合的navigate会有问题视为被删除
                        mergeEntityWithTrackEntity(targetEntity, currentValue, targetEntityMetadata);
                    }
                    saveNode.putInsertItem(new MemoryAddressCompareValue(currentValue), selfEntity, t -> {
                        setTargetValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
                    });
                } else {
                    saveNode.putInsertItem(new MemoryAddressCompareValue(targetEntity), selfEntity, t -> {
                        setTargetValue(TargetValueTypeEnum.VALUE_OBJECT, selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
                    });
                }

            } else {
                throw new EasyQueryInvalidOperationException("value object un support operate cascade:" + navigateMetadata.getCascade());
            }
        }
    }
}
