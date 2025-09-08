package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.TargetValueTypeEnum;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    public AbstractSaveProvider(Class<?> entityClass, List<Object> entities, EasyQueryClient easyQueryClient) {
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
    }

    protected TargetValueTypeEnum getTargetValueType(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {
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
        return EasyArrayUtil.any(selfPropertiesOrPrimary, prop -> keyProperties.contains(prop));
    }

    protected boolean targetPropsIsKeys(EntityMetadata selfMetadata, NavigateMetadata navigateMetadata) {

        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
        Collection<String> keyProperties = selfMetadata.getKeyProperties();
        return EasyArrayUtil.any(targetPropertiesOrPrimary, prop -> keyProperties.contains(prop));
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
                    throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getInstanceSimpleName(selfEntity) + " property:[" + self + "] value can not be null");
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
                    throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getInstanceSimpleName(targetEntity) + " property:[" + target + "] value can not be null");
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
                throw new EasyQueryInvalidOperationException("entityClass:[" + EasyClassUtil.getInstanceSimpleName(selfEntity) + "] property:[" + self + "] value can not be null,Please make sure the ["+EasyClassUtil.getInstanceSimpleName(selfEntity)+"] instance has been persisted to the database before use.");
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
                throw new EasyQueryInvalidOperationException("entityClass:[" + EasyClassUtil.getInstanceSimpleName(targetEntity) + "] property:[" + target + "] value can not be null,Please make sure the ["+EasyClassUtil.getInstanceSimpleName(targetEntity)+"] instance has been persisted to the database before use.");
            }
            ColumnMetadata columnMetadata = mappingEntityMetadata.getColumnNotNull(targetMappingProperty);
            columnMetadata.getSetterCaller().call(mappingEntity, targetValue);
        }
    }
}
