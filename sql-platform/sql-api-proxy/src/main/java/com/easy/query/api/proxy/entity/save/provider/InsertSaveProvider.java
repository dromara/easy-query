package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.SavableContext;
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
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.List;

/**
 * create time 2025/9/6 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertSaveProvider extends AbstractSaveProvider {
    private final SaveCommandContext saveCommandContext;

    public InsertSaveProvider(Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient) {
        super(entityClass, entities, easyQueryClient);
        this.saveCommandContext = new SaveCommandContext(entityClass);
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
        for (NavigateMetadata navigateMetadata : entityMetadata.getNavigateMetadatas()) {

            //如果导航属性是值类型并且没有循环引用且没有被追踪才继续下去
            if (!this.saveCommandContext.circulateCheck(navigateMetadata.getNavigatePropertyType(), deep)) {
                TargetValueTypeEnum targetValueType = getTargetValueType(entityMetadata, navigateMetadata);
                //我的id就是我们的关联关系键 多对多除外 还需要赋值一遍吧我的id给他 多对多下 需要处理的值对象是关联表 如果无关联中间表则目标对象是一个独立对象
                if (targetValueType == TargetValueTypeEnum.VALUE_OBJECT || targetValueType == TargetValueTypeEnum.AGGREGATE_ROOT) {
                    processNavigate(targetValueType, entity, entityMetadata, navigateMetadata, savableContext);
                }
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
        SaveNode saveNode = savableContext.putSaveNodeMap(navigateMetadata, targetEntityMetadata);


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

        if (this.saveCommandContext.isProcessEntity(targetEntity)) {
            return;
        }
        this.saveCommandContext.addProcessEntity(targetEntity);
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
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(targetEntity);
                if (trackEntityState == null) {
                    saveEntity(targetEntity, targetEntityMetadata, saveNode.getIndex() + 1);
                }
            }
        }
    }


}
