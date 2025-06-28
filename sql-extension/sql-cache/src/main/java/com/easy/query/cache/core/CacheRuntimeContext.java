package com.easy.query.cache.core;

import com.easy.query.cache.core.base.CacheKeyFactory;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.inject.ServiceProvider;

/**
 * create time 2024/1/24 20:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class CacheRuntimeContext {
    private final EasyCacheManager easyCacheManager;
    private final EasyCacheOption easyCacheOption;
    private final EasyQueryClient easyQueryClient;
    private final ServiceProvider serviceProvider;
    private final CacheKeyFactory cacheKeyFactory;

    public CacheRuntimeContext(
            ServiceProvider serviceProvider,
            EasyCacheManager easyCacheManager,
            EasyCacheOption easyCacheOption,
            EasyQueryClient easyQueryClient,
            CacheKeyFactory cacheKeyFactory) {
        this.easyCacheManager = easyCacheManager;
        this.easyCacheOption = easyCacheOption;
        this.easyQueryClient = easyQueryClient;
        this.serviceProvider = serviceProvider;
        this.cacheKeyFactory = cacheKeyFactory;
    }

    public EasyCacheManager getRedisManager() {
        return easyCacheManager;
    }

    public EasyCacheOption getEasyCacheOption() {
        return easyCacheOption;
    }

    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }

    public EasyCacheManager getEasyCacheManager() {
        return easyCacheManager;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }
    public CacheKeyFactory getCacheHashKeyFactory() {
        return cacheKeyFactory;
    }
}
