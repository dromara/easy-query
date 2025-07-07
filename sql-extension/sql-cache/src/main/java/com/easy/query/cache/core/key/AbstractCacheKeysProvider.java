package com.easy.query.cache.core.key;

import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.common.DefaultCacheKey;
import com.easy.query.cache.core.util.EasyCacheUtil;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.trigger.TriggerEvent;
import com.easy.query.core.trigger.TriggerTypeEnum;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                String cacheKeyValue = getCacheEntityCacheKey(columnMetadata, entity, cacheEntitySchema);
                cacheKeys.addAll(
                        getCacheKeys(triggerEvent.getTriggerTime(), LocalDateTime.now(), tableName, getCacheMethod(triggerEvent.getType()), cacheKeyValue)
                );
            }
            return cacheKeys;
        }
    }

    private String getCacheEntityCacheKey(ColumnMetadata columnMetadata, Object cacheEntity, CacheEntitySchema cacheEntitySchema) {
        Object val = columnMetadata.getGetterCaller().apply(cacheEntity);
        if (val == null) {
            throw new IllegalArgumentException(String.format("CacheEntitySchema.value [%s] is null", cacheEntitySchema.value()));
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
