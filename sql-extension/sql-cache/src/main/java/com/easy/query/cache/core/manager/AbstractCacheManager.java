package com.easy.query.cache.core.manager;


import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.base.CacheMethodEnum;
import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.common.DefaultCacheKey;
import com.easy.query.cache.core.util.EasyCacheUtil;

/**
 * create time 2025/7/5 16:14
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractCacheManager implements EasyCacheManager {
    protected final EasyCacheOption easyCacheOption;

    public AbstractCacheManager(EasyCacheOption easyCacheOption){
        this.easyCacheOption = easyCacheOption;
    }
    public String getCacheKey(Class<?> entityClass, String key) {
        return easyCacheOption.getEntityKey(entityClass) + ":" + key;
    }

    @Override
    public void deleteBy(CacheKey cacheKey) {
        boolean cacheAllEntity = EasyCacheUtil.isCacheAllEntity(cacheKey.getEntityClass());
        if(cacheAllEntity&&(
                CacheMethodEnum.INSERT==cacheKey.getCacheMethod()||
                CacheMethodEnum.DELETE==cacheKey.getCacheMethod()
                )){
            deleteBy0(new DefaultCacheKey(cacheKey.getCacheMethod(),cacheKey.getEntityClass(), easyCacheOption.getCacheIndex()));
        }
        deleteBy0(cacheKey);
    }
    protected abstract void deleteBy0(CacheKey cacheKey);
}
