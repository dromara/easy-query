package com.easy.query.core.metadata;

import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.common.cache.DefaultMemoryCache;
import com.easy.query.core.inject.ServiceProvider;

/**
 * @FileName: DefaultMetadataManager.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:16
 * @author xuejiaming
 */
public class DefaultEntityMetadataManager implements EntityMetadataManager {
    private final Cache<Class<?>,EntityMetadata> entityMetadataCache=new DefaultMemoryCache<>();
    private final ServiceProvider serviceProvider;

    public DefaultEntityMetadataManager(ServiceProvider serviceProvider){

        this.serviceProvider = serviceProvider;
    }

    @Override
    public EntityMetadata getEntityMetadata(Class<?> entityClass) {
        if(entityClass==null){
            throw new IllegalArgumentException("entityClass");
        }
        EntityMetadata cacheItem = entityMetadataCache.get(entityClass);
        if(cacheItem!=null){
            return cacheItem;
        }
//        if(tableName==null){
//            throw new JDQCException(String.format("当前对象不是数据库对象:[%s]",entityClass.getSimpleName()));
//        }
        return entityMetadataCache.computeIfAbsent(entityClass,key->{
            EntityMetadata entityMetadata = new EntityMetadata(entityClass);
            entityMetadata.init(serviceProvider);
            return entityMetadata;
        });
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
        return entityMetadata.isMultiTableMapping()&&!entityMetadata.isMultiDataSourceMapping();
    }

    @Override
    public boolean isShardingDataSource(Class<?> entityClass) {
        EntityMetadata entityMetadata = getEntityMetadata(entityClass);
        return entityMetadata.isMultiDataSourceMapping();
    }

    @Override
    public boolean isOnlyShardingDataSource(Class<?> entityClass) {
        EntityMetadata entityMetadata = getEntityMetadata(entityClass);
        return entityMetadata.isMultiDataSourceMapping()&&!entityMetadata.isMultiTableMapping();
    }
}
