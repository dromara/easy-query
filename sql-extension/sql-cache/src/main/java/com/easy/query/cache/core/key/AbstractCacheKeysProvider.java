package com.easy.query.cache.core.key;

import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.common.CacheDeleteEvent;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.common.DefaultCacheKey;
import com.easy.query.cache.core.util.EasyCacheUtil;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.trigger.TriggerEvent;
import com.easy.query.core.trigger.TriggerTypeEnum;
import com.easy.query.core.util.EasyStringUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create time 2025/7/6 16:58
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractCacheKeysProvider implements CacheKeysProvider {
    private final EasyQueryClient easyQueryClient;

    public AbstractCacheKeysProvider(EasyQueryClient easyQueryClient) {
        this.easyQueryClient = easyQueryClient;
    }

    @Override
    public List<CacheKey> getCacheKeys(LocalDateTime triggerTime, LocalDateTime receivedTime, String tableName, CacheMethodEnum clearMethod, String cacheKey) {
        List<CacheKey> cacheKeys = new ArrayList<>();
        List<EntityMetadata> entityMetadataList = easyQueryClient.getRuntimeContext().getEntityMetadataManager().getEntityMetadataList(tableName);
        if (entityMetadataList != null) {
            LocalDateTime received = receivedTime.plusSeconds(1);
            for (EntityMetadata entityMetadata : entityMetadataList) {
                if (EasyCacheUtil.isCacheEntity(entityMetadata.getEntityClass())) {
                    List<CacheKey> keys = getCacheKeys(triggerTime, received, entityMetadata, clearMethod, cacheKey);
                    cacheKeys.addAll(keys);
                }
            }
        }

        return cacheKeys;
    }

    protected abstract List<String> getCacheKeysByExpression(LocalDateTime triggerTime, LocalDateTime receivedTime, EntityMetadata entityMetadata, CacheMethodEnum clearMethod, CacheEntitySchema cacheEntitySchema);

    protected List<CacheKey> getExpressionCacheKeys(LocalDateTime triggerTime, LocalDateTime receivedTime, EntityMetadata entityMetadata, CacheMethodEnum clearMethod, CacheEntitySchema cacheEntitySchema) {

        List<String> expressionCacheKeys = getCacheKeysByExpression(triggerTime, receivedTime, entityMetadata, clearMethod, cacheEntitySchema);
        List<CacheKey> cacheKeys = new ArrayList<>(expressionCacheKeys.size());
        for (String entityCacheKey : expressionCacheKeys) {
            cacheKeys.add(new DefaultCacheKey(clearMethod, entityMetadata.getEntityClass(), entityCacheKey));
        }
        return cacheKeys;
    }

    @Override
    public List<CacheKey> getCacheKeys(LocalDateTime triggerTime, LocalDateTime receivedTime, EntityMetadata entityMetadata, CacheMethodEnum cacheMethod, String cacheKey) {

        List<CacheKey> cacheKeys = new ArrayList<>();
        Class<?> entityClass = entityMetadata.getEntityClass();
        CacheEntitySchema cacheEntitySchema = EasyCacheUtil.getCacheEntitySchema(entityClass);
        if (CacheMethodEnum.INSERT == cacheMethod) {
            if (CacheKvEntity.class.isAssignableFrom(entityClass)) {
                if (EasyStringUtil.isNotBlank(cacheKey)) {
                    cacheKeys.add(new DefaultCacheKey(cacheMethod, entityClass, cacheKey));
                } else {
                    List<CacheKey> expressionCacheKeys = getExpressionCacheKeys(triggerTime, receivedTime, entityMetadata, cacheMethod, cacheEntitySchema);
                    cacheKeys.addAll(expressionCacheKeys);
                }
            }
        } else if (CacheMethodEnum.UPDATE == cacheMethod) {
            if (EasyStringUtil.isNotBlank(cacheKey)) {
                cacheKeys.add(new DefaultCacheKey(cacheMethod, entityClass, cacheKey));
            } else {

                List<CacheKey> expressionCacheKeys = getExpressionCacheKeys(triggerTime, receivedTime, entityMetadata, cacheMethod, cacheEntitySchema);
                cacheKeys.addAll(expressionCacheKeys);
            }
        } else if (CacheMethodEnum.DELETE == cacheMethod) {
            if (EasyStringUtil.isNotBlank(cacheKey)) {
                cacheKeys.add(new DefaultCacheKey(cacheMethod, entityClass, cacheKey));
            } else {
                List<CacheKey> expressionCacheKeys = getExpressionCacheKeys(triggerTime, receivedTime, entityMetadata, cacheMethod, cacheEntitySchema);
                cacheKeys.addAll(expressionCacheKeys);
            }
        }
        return cacheKeys;
    }

    @Override
    public List<CacheKey> getCacheKeys(TriggerEvent triggerEvent) {
        EntityMetadata entityMetadata = triggerEvent.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(triggerEvent.getEntityClass());

        String tableName = entityMetadata.getTableName();

        if (triggerEvent.getEntities() == null) {
            return getCacheKeys(triggerEvent.getTriggerTime(), LocalDateTime.now(), tableName, getCacheMethod(triggerEvent.getType()), null);
        } else {
            List<CacheKey> cacheKeys = new ArrayList<>();
            CacheEntitySchema cacheEntitySchema = EasyCacheUtil.getCacheEntitySchema(entityMetadata.getEntityClass());
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(cacheEntitySchema.value());
            for (Object entity : triggerEvent.getEntities()) {
                String cacheKeyValue = getCacheEntityCacheKeyOrNull(columnMetadata, entity);
                cacheKeys.addAll(
                        getCacheKeys(triggerEvent.getTriggerTime(), LocalDateTime.now(), tableName, getCacheMethod(triggerEvent.getType()), cacheKeyValue)
                );
            }
            return cacheKeys;
        }
    }

    @Override
    public List<CacheDeleteEvent> getCacheEvents(TriggerEvent triggerEvent) {
        EntityMetadata entityMetadata = triggerEvent.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(triggerEvent.getEntityClass());
        LocalDateTime now = LocalDateTime.now();
        String tableName = entityMetadata.getTableName();
        List<CacheDeleteEvent> cacheDeleteEvents = new ArrayList<>();
        if (triggerEvent.getEntities() == null) {
            CacheDeleteEvent cacheDeleteEvent = CacheDeleteEvent.build(null, getCacheMethod(triggerEvent.getType()).getCode(), triggerEvent.getTriggerTime(), now, tableName);
            cacheDeleteEvents.add(cacheDeleteEvent);
        } else {
            Set<CacheDeleteEvent> events = new HashSet<>();
            CacheEntitySchema cacheEntitySchema = EasyCacheUtil.getCacheEntitySchema(entityMetadata.getEntityClass());
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(cacheEntitySchema.value());
            for (Object entity : triggerEvent.getEntities()) {
                String cacheKeyValue = getCacheEntityCacheKeyOrNull(columnMetadata, entity);
                CacheDeleteEvent cacheDeleteEvent = CacheDeleteEvent.build(cacheKeyValue, getCacheMethod(triggerEvent.getType()).getCode(), triggerEvent.getTriggerTime(), now, tableName);
                events.add(cacheDeleteEvent);
            }
            cacheDeleteEvents.addAll(events);
        }
        return cacheDeleteEvents;
    }


    private String getCacheEntityCacheKeyOrNull(ColumnMetadata columnMetadata, Object cacheEntity) {
        Object val = columnMetadata.getGetterCaller().apply(cacheEntity);
        if (val == null) {
            return null;
        }
        return val.toString();
    }

    private CacheMethodEnum getCacheMethod(TriggerTypeEnum triggerType) {
        switch (triggerType) {
            case INSERT:
                return CacheMethodEnum.INSERT;
            case UPDATE:
                return CacheMethodEnum.UPDATE;
            case DELETE:
                return CacheMethodEnum.DELETE;
            default:
                return CacheMethodEnum.UNKNOWN;
        }
    }
}
