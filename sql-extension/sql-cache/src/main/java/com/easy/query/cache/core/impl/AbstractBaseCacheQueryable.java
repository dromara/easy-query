package com.easy.query.cache.core.impl;

import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.EasyCacheStorageOption;
import com.easy.query.cache.core.EasyRedisManager;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.queryable.CacheQueryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * create time 2023/5/16 10:19
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseCacheQueryable<TEntity extends CacheEntity>  implements CacheQueryable {
    protected final EasyCacheOption easyCacheOption;
    protected final EasyRedisManager easyRedisManager;
    protected final EasyQueryClient easyQueryClient;
    protected final Class<TEntity> entityClass;
    protected final CacheEntitySchema cacheEntitySchema;
    private  List<CachePredicate<TEntity>> filters;

    public AbstractBaseCacheQueryable(EasyCacheStorageOption easyCacheStorageOption, Class<TEntity> entityClass){

        this.easyCacheOption = easyCacheStorageOption.getCacheOption();
        this.easyRedisManager = easyCacheStorageOption.getRedisManager();
        this.easyQueryClient = easyCacheStorageOption.getEasyQueryClient();
        this.entityClass = entityClass;
        CacheEntitySchema cacheEntitySchema = EasyClassUtil.getAnnotation(entityClass, CacheEntitySchema.class);
        if(cacheEntitySchema==null){
            throw new IllegalArgumentException(entityClass.getSimpleName()+"需要指定注解@CacheEntitySchema");
        }
        this.cacheEntitySchema = cacheEntitySchema;
    }

    @Override
    public String getEntityKey() {
        return this.easyCacheOption.getEntityKey(entityClass);
    }

    protected Pair<String,TEntity> getKeyAndEntity(TEntity entity){
        return new Pair<>(getKey(entity),entity);
    }
    protected  String getKey(TEntity entity){
        return entity.cacheIdValue();
    }
    protected String getIdProperty(){
        return cacheEntitySchema.value();
    }
    protected void addFilter(CachePredicate<TEntity> filter){
        if(filters==null){
            filters=new ArrayList<>();
        }
        filters.add(filter);
    }
    protected boolean hasFilter(){
        return EasyCollectionUtil.isNotEmpty(filters);
    }

    protected Stream<TEntity> filterResult(Stream<TEntity> source){
        if(filters!=null){
            return source.filter(o->{
                for (CachePredicate<TEntity> filter : filters) {
                    boolean ok = filter.apply(o);
                    if(!ok){
                        return false;
                    }
                }
                return true;
            });
        }
        return source;
    }

}
