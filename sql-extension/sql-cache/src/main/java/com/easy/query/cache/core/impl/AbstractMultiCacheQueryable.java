//package com.easy.query.cache.core.impl;
//
//import com.easy.query.cache.core.CacheMultiEntity;
//import com.easy.query.cache.core.EasyCacheStorageOption;
//import com.easy.query.cache.core.annotation.CacheMultiEntitySchema;
//import com.easy.query.cache.core.queryable.MultiCacheQueryable;
//import com.easy.query.core.util.EasyClassUtil;
//import com.easy.query.core.util.EasyStringUtil;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * create time 2023/5/16 12:26
// * 文件说明
// *
// * @author xuejiaming
// */
//public abstract class AbstractMultiCacheQueryable<TEntity extends CacheMultiEntity> extends AbstractBaseCacheQueryable<TEntity> implements MultiCacheQueryable<TEntity> {
//    protected final CacheMultiEntitySchema cacheMultiEntitySchema;
//    public AbstractMultiCacheQueryable(EasyCacheStorageOption cacheStorageOption, Class<TEntity> entityClass) {
//        super(cacheStorageOption, entityClass);
//        CacheMultiEntitySchema annotation = EasyClassUtil.getAnnotation(entityClass, CacheMultiEntitySchema.class);
//        if(annotation==null){
//            throw new IllegalArgumentException(entityClass.getSimpleName()+"需要指定注解@CacheMultiEntitySchema");
//        }
//        this.cacheMultiEntitySchema = annotation;
//    }
//
//    @Override
//    public TEntity firstOrNull(String aggregateId, String id) {
//        return firstOrDefault(aggregateId,id,null);
//    }
//    @Override
//    public TEntity firstOrDefault(String aggregateId, String id, TEntity def) {
//        if(EasyStringUtil.isBlank(aggregateId)||EasyStringUtil.isBlank(id)){
//            return def;
//        }
//        List<TEntity> entities = toList(aggregateId, Collections.singletonList(id));
//        if(entities.isEmpty()){
//            return def;
//        }
//        return entities.get(0);
//    }
//}
