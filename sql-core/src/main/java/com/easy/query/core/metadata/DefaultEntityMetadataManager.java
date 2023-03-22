package com.easy.query.core.metadata;

import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.common.cache.DefaultMemoryCache;

/**
 * @FileName: DefaultMetadataManager.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:16
 * @Created by xuejiaming
 */
public class DefaultEntityMetadataManager implements EntityMetadataManager {
    private final Cache<Class<?>,EntityMetadata> entityMetadataCache=new DefaultMemoryCache<>();
    private final EasyQueryConfiguration jdqcConfiguration;

    public DefaultEntityMetadataManager(EasyQueryConfiguration jdqcConfiguration){

        this.jdqcConfiguration = jdqcConfiguration;
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
            entityMetadata.init(jdqcConfiguration);
            return entityMetadata;
        });
    }
}
