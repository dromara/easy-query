package com.easy.query.core.metadata.bean;

import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.common.cache.DefaultMemoryCache;

/**
 * create time 2023/6/15 10:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class BasicEntityMetadataManager {
    private static final Cache<Class<?>, BasicEntityMetadata> basicEntityMetadataCache=new DefaultMemoryCache<>();

    public static BasicEntityMetadata getBasicEntityMetadata(Class<?> entityClass){

        if(entityClass==null){
            throw new IllegalArgumentException("entityClass");
        }
        BasicEntityMetadata original = basicEntityMetadataCache.get(entityClass);
        if(original!=null){
            return original;
        }
//        if(tableName==null){
//            throw new JDQCException(String.format("当前对象不是数据库对象:[%s]",entityClass.getSimpleName()));
//        }
        BasicEntityMetadata basicEntityMetadata = new BasicEntityMetadata(entityClass);
        return basicEntityMetadataCache.computeIfAbsent(entityClass,key->{
            return basicEntityMetadata;
        });
    }

}
