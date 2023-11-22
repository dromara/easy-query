package com.easy.query.core.expression.sql.builder.impl.ignore;

import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityTrackProperty;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackDiffEntry;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EntityUpdateTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2023/5/31 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityUpdateSetProcessor {
    private final Object entity;
    private final QueryRuntimeContext runtimeContext;
    private final Set<String> entityPropertiesIgnore;
    private final SQLExecuteStrategyEnum expressionUpdateStrategy;
    private EntityTrackProperty entityTrackProperty;
    private EntityUpdateTypeEnum entityUpdateType = EntityUpdateTypeEnum.ENTITY_NULL;

    public EntityUpdateSetProcessor(Object entity, ExpressionContext expressionContext) {
        this.entity = entity;
        this.runtimeContext = expressionContext.getRuntimeContext();
        this.entityPropertiesIgnore = new HashSet<>();
        this.expressionUpdateStrategy = expressionContext.getSQLStrategy();
        initIgnoreProperties();
    }

    private void initIgnoreProperties() {
        if (entity != null) {
            //优先级是用户设置、追踪、默认配置
            if (SQLExecuteStrategyEnum.DEFAULT != expressionUpdateStrategy) {
                entityUpdateType = EntityUpdateTypeEnum.CUSTOM;
                getCustomIgnoreProperties(expressionUpdateStrategy, runtimeContext.getEntityMetadataManager());
            } else {
                TrackManager trackManager = runtimeContext.getTrackManager();
                //todo 判断是否追踪
                if (trackManager.currentThreadTracking()) {
                    TrackContext trackContext = trackManager.getCurrentTrackContext();
                    //如果当前对象是追踪的并且没有指定更新策略
                    EntityState trackEntityState = trackContext.getTrackEntityState(entity);
                    if (trackEntityState != null) {
                        entityUpdateType = EntityUpdateTypeEnum.TRACK;
                        this.entityTrackProperty = EasyTrackUtil.getTrackDiffProperty(runtimeContext.getEntityMetadataManager(), trackEntityState);
                        entityPropertiesIgnore.addAll(entityTrackProperty.getSameProperties());
                        return;
                    }
                }
                entityUpdateType = EntityUpdateTypeEnum.GLOBAL_CUSTOM;
                SQLExecuteStrategyEnum globalUpdateStrategy = runtimeContext.getQueryConfiguration().getEasyQueryOption().getUpdateStrategy();
                getCustomIgnoreProperties(globalUpdateStrategy, runtimeContext.getEntityMetadataManager());
            }
        }
    }

    private void getCustomIgnoreProperties(SQLExecuteStrategyEnum updateStrategy, EntityMetadataManager entityMetadataManager) {
        if (Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) || Objects.equals(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS, updateStrategy)) {
            Set<String> beanMatchProperties = EasyBeanUtil.getBeanMatchProperties(entityMetadataManager, entity, Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) ? Objects::isNull : Objects::nonNull);
            entityPropertiesIgnore.addAll(beanMatchProperties);
        }
    }

    public boolean shouldRemove(String property) {
        return entity != null && entityPropertiesIgnore.contains(property);
    }

    public TrackDiffEntry trackValue(String property) {
        if (entityTrackProperty != null) {
            return entityTrackProperty.getDiffProperties().get(property);
        }
        return null;
    }

    public EntityTrackProperty getEntityTrackProperty() {
        return entityTrackProperty;
    }

    public EntityUpdateTypeEnum getEntityUpdateType() {
        return entityUpdateType;
    }
}
