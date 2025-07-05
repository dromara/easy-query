package com.easy.query.cache.core.manager;


import com.easy.query.cache.core.EasyCacheOption;

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
}
