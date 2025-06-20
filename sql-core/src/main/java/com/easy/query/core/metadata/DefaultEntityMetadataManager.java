package com.easy.query.core.metadata;

import com.easy.query.core.common.MapColumnNameChecker;
import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.common.cache.DefaultMemoryCache;
import com.easy.query.core.configuration.nameconversion.MapKeyNameConversion;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.util.EasyStringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejiaming
 * @FileName: DefaultMetadataManager.java
 * @Description: 文件说明
 * create time 2023/2/11 10:16
 */
public class DefaultEntityMetadataManager implements EntityMetadataManager {
    private final Cache<Class<?>, EntityMetadata> entityMetadataCache = new DefaultMemoryCache<>();
    private final Map<String, List<EntityMetadata>> tableEntityMetadataCache = new ConcurrentHashMap<>();
    private final ServiceProvider serviceProvider;
    private final MapColumnNameChecker mapColumnNameChecker;
    private final MapKeyNameConversion mapKeyNameConversion;

    public DefaultEntityMetadataManager(ServiceProvider serviceProvider, MapColumnNameChecker mapColumnNameChecker, MapKeyNameConversion mapKeyNameConversion) {

        this.serviceProvider = serviceProvider;
        this.mapColumnNameChecker = mapColumnNameChecker;
        this.mapKeyNameConversion = mapKeyNameConversion;
    }

    @Override
    public EntityMetadata getEntityMetadata(Class<?> entityClass) {
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass");
        }
        EntityMetadata cacheItem = entityMetadataCache.get(entityClass);
        if (cacheItem != null) {
            return cacheItem;
        }
//        if(EasyClassUtil.isBasicType(entityClass)){
//            return new EntityMetadata(entityClass);
//        }
//        if(tableName==null){
//            throw new JDQCException(String.format("当前对象不是数据库对象:[%s]",entityClass.getSimpleName()));
//        }
        if (Map.class.isAssignableFrom(entityClass)) {
            return entityMetadataCache.computeIfAbsent(entityClass, key -> {
                return new MapEntityMetadata(Map.class, mapColumnNameChecker, mapKeyNameConversion);
            });
        }

        EntityMetadata entityMetadata = new EntityMetadata(entityClass);
        entityMetadata.init(serviceProvider);


        addMetadata(entityMetadata.getTableName(), entityMetadata);
        return entityMetadataCache.computeIfAbsent(entityClass, key -> {
            return entityMetadata;
        });
    }

    public void addMetadata(String key, EntityMetadata metadata) {
        if (EasyStringUtil.isNotBlank(key)) {
            tableEntityMetadataCache.compute(key, (k, list) -> {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(metadata);
                return list;
            });
        }
    }

    @Override
    public boolean isSharding(Class<?> entityClass) {
        EntityMetadata entityMetadata = getEntityMetadata(entityClass);
        return entityMetadata.isMultiTableMapping() || entityMetadata.isMultiDataSourceMapping();
    }

    @Override
    public boolean isShardingTable(Class<?> entityClass) {
        EntityMetadata entityMetadata = getEntityMetadata(entityClass);
        return entityMetadata.isMultiTableMapping();
    }

    @Override
    public boolean isOnlyShardingTable(Class<?> entityClass) {
        EntityMetadata entityMetadata = getEntityMetadata(entityClass);
        return entityMetadata.isMultiTableMapping() && !entityMetadata.isMultiDataSourceMapping();
    }

    @Override
    public boolean isShardingDataSource(Class<?> entityClass) {
        EntityMetadata entityMetadata = getEntityMetadata(entityClass);
        return entityMetadata.isMultiDataSourceMapping();
    }

    @Override
    public boolean isOnlyShardingDataSource(Class<?> entityClass) {
        EntityMetadata entityMetadata = getEntityMetadata(entityClass);
        return entityMetadata.isMultiDataSourceMapping() && !entityMetadata.isMultiTableMapping();
    }

    @Nullable
    @Override
    public List<EntityMetadata> getEntityMetadataList(String tableName) {
        if (tableName == null) {
            return null;
        }
        return tableEntityMetadataCache.get(tableName);
    }

    @Override
    public List<EntityMetadata> getAllLoadedEntityMetadata() {
        return new ArrayList<>(entityMetadataCache.values());
    }
}
