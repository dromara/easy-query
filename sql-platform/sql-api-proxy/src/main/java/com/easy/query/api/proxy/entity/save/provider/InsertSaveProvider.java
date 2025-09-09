package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveCheckModeEnum;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.TargetValueTypeEnum;
import com.easy.query.api.proxy.entity.save.command.EmptySaveCommand;
import com.easy.query.api.proxy.entity.save.command.InsertSaveCommand;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.enums.MappingClassSaveModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludePathTreeNode;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create time 2025/9/6 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertSaveProvider extends AbstractSaveProvider {

    public InsertSaveProvider(Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient, SaveCheckModeEnum saveCheckMode, List<Set<String>> savePathLimit) {
        super(entityClass, entities, easyQueryClient,saveCheckMode, savePathLimit);
    }

    @Override
    protected EntityMetadata checkNavigateContinueAndGetTargetEntityMetadata(NavigateMetadata navigateMetadata, Object entity, EntityMetadata entityMetadata, int deep) {

        if (!isSavePathLimitContains(navigateMetadata, deep)) {
            return null;
        }
        if(navigateMetadata.getRelationType()==RelationTypeEnum.ManyToMany&&navigateMetadata.getMappingClass()!=null){
            return entityMetadataManager.getEntityMetadata(navigateMetadata.getMappingClass());
        }else{
            return entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        }
    }

    @Override
    public SaveCommand createCommand() {

        if (EasyCollectionUtil.isNotEmpty(entities)) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
            for (Object entity : entities) {
                //添加重复引用去重
                this.saveCommandContext.addProcessEntity(entity);
                saveEntity(entity, entityMetadata, 0);
            }
            return new InsertSaveCommand(entityMetadata, entities, easyQueryClient, saveCommandContext);
        }

        return EmptySaveCommand.INSTANCE;
    }

    private void saveEntity(Object entity, EntityMetadata entityMetadata, int deep) {

        EntityState entityState = currentTrackContext.getTrackEntityState(entity);
        if (entityState != null) {
            throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getSimpleName(entity.getClass()) + " has been tracked");
        }
        SavableContext savableContext = this.saveCommandContext.getSavableContext(deep);

        List<NavigateMetadata> valueObjects = getNavigateSavableValueObjects(savableContext, entity, entityMetadata, entityMetadata.getNavigateMetadatas(), deep);
        for (NavigateMetadata navigateMetadata : valueObjects) {
            processNavigate(entity, entityMetadata, navigateMetadata, savableContext);
        }
    }

    private void processNavigate(Object entity, EntityMetadata selfEntityMetadata, NavigateMetadata navigateMetadata, SavableContext savableContext) {

        if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {
            throw new EasyQueryInvalidOperationException("save not support direct mapping");
        }
        EntityMetadata targetEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        SaveNode saveNode = savableContext.getSaveNode(navigateMetadata);


        Property<Object, ?> getter = navigateMetadata.getGetter();
        Object navigates = getter.apply(entity);
        if (navigates instanceof Collection<?>) {
            for (Object targetEntity : (Collection<?>) navigates) {
                processEntity(entity, targetEntity, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        } else {
            if (navigates != null) {
                processEntity(entity, navigates, selfEntityMetadata, targetEntityMetadata, navigateMetadata, saveNode);
            }
        }
    }

    private void processEntity(Object selfEntity, Object targetEntity, EntityMetadata selfEntityMetadata, EntityMetadata targetEntityMetadata, NavigateMetadata navigateMetadata, SaveNode saveNode) {

        if (this.saveCommandContext.isProcessEntity(targetEntity)) {
            return;
        }
        this.saveCommandContext.addProcessEntity(targetEntity);
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            //检查中间表并且创建新增操作
            if (navigateMetadata.getMappingClass() == null) {
                throw new EasyQueryInvalidOperationException("entity:["+EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass())+"]-["+EasyClassUtil.getSimpleName(navigateMetadata.getNavigatePropertyType())+"] many to many relation must have mapping class");
            }
            if (navigateMetadata.getMappingClassSaveMode() == MappingClassSaveModeEnum.THROW) {
                throw new EasyQueryInvalidOperationException("entity:["+EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass())+"]-["+EasyClassUtil.getSimpleName(navigateMetadata.getNavigatePropertyType())+"] many to many relation mapping class save mode is throw");
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
                setTargetValue(TargetValueTypeEnum.VALUE_OBJECT,selfEntity, t, selfEntityMetadata, navigateMetadata, targetEntityMetadata);
            }));
            EntityState trackEntityState = currentTrackContext.getTrackEntityState(targetEntity);
            if (trackEntityState == null) {
                saveEntity(targetEntity, targetEntityMetadata, saveNode.getIndex() + 1);
            }
        }
    }


}
