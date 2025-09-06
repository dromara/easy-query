package com.easy.query.api.proxy.entity.save.abstraction;

import com.easy.query.api.proxy.entity.save.EntitySavable;
import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.api.proxy.entity.save.provider.InsertSaveProvider;
import com.easy.query.api.proxy.entity.save.provider.UpdateSaveProvider;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * create time 2025/9/5 16:12
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntitySavable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntitySavable<TProxy, T> {
    protected final List<T> entities = new ArrayList<>();
    private final Class<T> entityClass;
    private final EasyQueryClient easyQueryClient;
    private final QueryRuntimeContext runtimeContext;
    private final TrackContext currentTrackContext;
    private final SaveCommandContext saveCommandContext;
    private final EntityMetadataManager entityMetadataManager;

    public AbstractEntitySavable(Class<T> entityClass, Collection<T> entities, EasyQueryClient easyQueryClient) {
        this.entityClass = entityClass;
        this.easyQueryClient = easyQueryClient;
        this.runtimeContext = easyQueryClient.getRuntimeContext();
        if (entities == null) {
            throw new EasyQueryException("不支持空对象的save");
        }
        this.entities.addAll(entities);
        this.entityMetadataManager = runtimeContext.getEntityMetadataManager();
        boolean threadInTransaction = runtimeContext.getConnectionManager().currentThreadInTransaction();
        if (!threadInTransaction) {
            throw new EasyQueryInvalidOperationException("current thread not in transaction");
        }
        this.currentTrackContext = Objects.requireNonNull(runtimeContext.getTrackManager().getCurrentTrackContext(), "currentTrackContext can not be null");
        this.saveCommandContext = new SaveCommandContext(entityClass);
    }

    @Override
    public List<T> getEntities() {
        return entities;
    }

    @Override
    public void executeCommand() {
        if (!entities.isEmpty()) {
            List<SaveCommand> saveCommands = new ArrayList<>();
            List<Object> insertEntities = new ArrayList<>();
            List<Object> updateEntities = new ArrayList<>();
            for (T entity : entities) {
                EntityState entityState = currentTrackContext.getTrackEntityState(entity);
                if(entityState==null){
                    insertEntities.add(entity);
                }else{
                    updateEntities.add(entity);
                }
                saveEntity(entity, 0);
            }
            if(EasyCollectionUtil.isNotEmpty(insertEntities)){
                saveCommands.add(new InsertSaveProvider(entityClass, insertEntities, easyQueryClient).createCommand());
            }
            if(EasyCollectionUtil.isNotEmpty(updateEntities)){
                saveCommands.add(new UpdateSaveProvider(entityClass, updateEntities, easyQueryClient).createCommand());
            }
            for (SaveCommand saveCommand : saveCommands) {
                saveCommand.execute();
            }
            List<SavableContext> savableContexts = this.saveCommandContext.getSavableContexts();
            for (int i = savableContexts.size() - 1; i >= 0; i--) {
                SavableContext savableContext = savableContexts.get(i);
                for (Map.Entry<NavigateMetadata, SaveNode> saveNodeEntry : savableContext.getSaveNodeMap().entrySet()) {
                    easyQueryClient.deletable(saveNodeEntry.getValue().getDeletes()).executeRows();
                }
            }
            //为什么不是insert？
            easyQueryClient.updatable(entities).executeRows();
            for (int i = 0; i < savableContexts.size(); i++) {
                SavableContext savableContext = savableContexts.get(i);
                for (Map.Entry<NavigateMetadata, SaveNode> saveNodeEntry : savableContext.getSaveNodeMap().entrySet()) {
                    easyQueryClient.insertable(saveNodeEntry.getValue().getInserts()).executeRows();
                    easyQueryClient.updatable(saveNodeEntry.getValue().getUpdates()).executeRows();
                }
            }
        }
    }

    //未追踪的数据建议采用insert or update的做法
    private void saveEntity(Object entity, int deep) {

        SavableContext savableContext = this.saveCommandContext.getSavableContext(deep);
        EntityState entityState = currentTrackContext.getTrackEntityState(entity);
        if (entityState == null) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity.getClass());
            for (NavigateMetadata navigateMetadata : entityMetadata.getNavigateMetadatas()) {
                processNavigate(entity, navigateMetadata, savableContext, () -> new HashSet<>());
            }
            return;
        }
        List<NavigateMetadata> includes = entityState.getIncludes();
        if (includes != null) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity.getClass());
            for (NavigateMetadata include : includes) {
                TargetValueType targetValueType = getTargetValueType(entityMetadata, include);

                //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
                if (targetValueType == TargetValueType.VALUE_OBJECT || targetValueType == TargetValueType.AGGREGATE_ROOT) {
                    processNavigate(targetValueType, entity, include, savableContext, () -> entityState.getTrackKeys(include));
                }
            }
        }
    }

    private void processNavigate(TargetValueType targetValueType, Object entity, NavigateMetadata navigateMetadata, SavableContext savableContext, Supplier<Set<String>> trackKeyCreate) {

        if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
            throw new EasyQueryInvalidOperationException("save not support direct mapping");
        }
        SaveNode saveNode = savableContext.createSaveNodeMap(navigateMetadata);

        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        Set<String> trackKeys = trackKeyCreate.get();
        if (trackKeys != null) {
            Map<String, Object> dbEntityMap = new HashMap<>();
            for (String trackKey : trackKeys) {
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), trackKey);
                Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + trackKey);
                dbEntityMap.put(trackKey, trackEntityState.getOriginalValue());
            }

            Property<Object, ?> getter = navigateMetadata.getGetter();
            Object navigates = getter.apply(entity);
            if (navigates instanceof Collection<?>) {
                for (Object targetEntity : (Collection<?>) navigates) {
                    processEntity(targetEntity, dbEntityMap, entityMetadata, navigateMetadata, saveNode);
                }
            } else {
                if (navigates != null) {
                    processEntity(navigates, dbEntityMap, entityMetadata, navigateMetadata, saveNode);
                }
            }
            saveNode.getDeletes().addAll(dbEntityMap.values());
        }
    }

    private void setTargetValue(TargetValueType targetValueType, Object selfEntity,Object entity,EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata,EntityMetadata targetEntityMetadata){
        if(targetValueType == TargetValueType.VALUE_OBJECT){
            String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
            for (int i = 0; i < selfPropertiesOrPrimary.length; i++) {
                String self = selfPropertiesOrPrimary[i];
                String target = targetPropertiesOrPrimary[i];
                ColumnMetadata targetColumn = targetEntityMetadata.getColumnNotNull(target);
                Object targetValue = targetColumn.getGetterCaller().apply(entity);
                if(targetValue == null){
                    throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getInstanceSimpleName(entity) + " property:" + target + " value can not be null");
                }
                ColumnMetadata selfColumn = selfEntityMetadata.getColumnNotNull(self);
                selfColumn.getSetterCaller().call(selfEntity, targetValue);
            }
        }
    }
    private void processEntity(Object entity, Map<String, Object> dbEntityMap, EntityMetadata entityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {
//获取新导航集合元素
        String newNavigateEntityKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
        if (newNavigateEntityKey == null || !dbEntityMap.containsKey(newNavigateEntityKey)) {
            saveNode.getInserts().add(entity);
        } else {
            dbEntityMap.remove(newNavigateEntityKey);
            EntityState trackEntityState = currentTrackContext.getTrackEntityState(navigateMetadata.getNavigatePropertyType(), newNavigateEntityKey);
            Objects.requireNonNull(trackEntityState, "trackEntityState cant be null,trackKey:" + newNavigateEntityKey);
            if (entity != trackEntityState.getCurrentValue()) {
                throw new EasyQueryInvalidOperationException("entity:" + entity + " is not same");
            }
            for (Map.Entry<String, ColumnMetadata> propColumn : entityMetadata.getProperty2ColumnMap().entrySet()) {
                EntityValueState entityValueState = trackEntityState.getEntityValueState(propColumn.getValue());
                if (entityValueState.isChanged()) {
                    saveNode.getUpdates().add(entity);
                    break;
                }
            }
        }
        saveEntity(entity, saveNode.getIndex() + 1);
    }

    private TargetValueType getTargetValueType(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            if (navigateMetadata.getMappingClass() != null) {
                boolean any = selfPropsIsKeys(selfMetadata, navigateMetadata);
                if (any) {
                    return TargetValueType.VALUE_OBJECT;
                }
            }
            return TargetValueType.RELATION_OTHER;
        }
        boolean selfIsKey = selfPropsIsKeys(selfMetadata, navigateMetadata);
        if (selfIsKey) {
            return TargetValueType.VALUE_OBJECT;
        }
        boolean targetIsKey = targetPropsIsKeys(selfMetadata, navigateMetadata);
        if (targetIsKey) {
            return TargetValueType.AGGREGATE_ROOT;
        }
        return TargetValueType.RELATION_OTHER;
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

    enum TargetValueType {
        /**
         * 值对象
         */
        VALUE_OBJECT,
        /**
         * 聚合根
         */
        AGGREGATE_ROOT,
        /**
         * 其他关联对象
         */
        RELATION_OTHER
    }
}
