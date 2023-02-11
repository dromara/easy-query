package org.jdqc.core.metadata;

import org.jdqc.core.abstraction.metadata.EntityMetadata;
import org.jdqc.core.common.cache.Cache;
import org.jdqc.core.abstraction.metadata.EntityMetadataManager;
import org.jdqc.core.common.cache.DefaultMemoryCache;
import org.jdqc.core.config.JDQCConfiguration;
import org.jdqc.core.config.NameConversion;
import org.jdqc.core.exception.JDQCException;

/**
 * @FileName: DefaultMetadataManager.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:16
 * @Created by xuejiaming
 */
public class DefaultEntityMetadataManager implements EntityMetadataManager {
    private final Cache<Class,EntityMetadata> entityMetadataCache=new DefaultMemoryCache<>();
    private final JDQCConfiguration jdqcConfiguration;

    public DefaultEntityMetadataManager(JDQCConfiguration jdqcConfiguration){

        this.jdqcConfiguration = jdqcConfiguration;
    }

    @Override
    public EntityMetadata getEntityMetadata(Class entityClass) {
        if(entityClass==null){
            throw new IllegalArgumentException("entityClass");
        }
        EntityMetadata cacheItem = entityMetadataCache.get(entityClass);
        if(cacheItem!=null){
            return cacheItem;
        }
        NameConversion nameConversion = jdqcConfiguration.getNameConversion();
        String tableName = nameConversion.getTableName(entityClass);
        if(tableName==null){
            throw new JDQCException(String.format("当前对象不是数据库对象:[%s]",entityClass.getSimpleName()));
        }
        EntityMetadata entityMetadata = new EntityMetadata(entityClass, tableName);
        return entityMetadataCache.computeIfAbsent(entityClass,key->{
            entityMetadata.init(jdqcConfiguration);
            return entityMetadata;
        });
    }
}
