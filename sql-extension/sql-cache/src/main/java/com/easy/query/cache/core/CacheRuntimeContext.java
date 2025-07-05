package com.easy.query.cache.core;

import com.easy.query.cache.core.key.CacheKeyFactory;
import com.easy.query.cache.core.provider.EasyCacheProvider;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.inject.ServiceProvider;

/**
 * create time 2024/1/24 20:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class CacheRuntimeContext {
    private final EasyCacheProvider easyCacheProvider;
    private final EasyCacheOption easyCacheOption;
    private final EasyQueryClient easyQueryClient;
    private final ServiceProvider serviceProvider;
    private final CacheKeyFactory cacheKeyFactory;

    public CacheRuntimeContext(
            ServiceProvider serviceProvider,
            EasyCacheProvider easyCacheProvider,
            EasyCacheOption easyCacheOption,
            EasyQueryClient easyQueryClient,
            CacheKeyFactory cacheKeyFactory) {
        this.easyCacheProvider = easyCacheProvider;
        this.easyCacheOption = easyCacheOption;
        this.easyQueryClient = easyQueryClient;
        this.serviceProvider = serviceProvider;
        this.cacheKeyFactory = cacheKeyFactory;
    }

    public EasyCacheProvider getRedisManager() {
        return easyCacheProvider;
    }

    public EasyCacheOption getEasyCacheOption() {
        return easyCacheOption;
    }

    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }

    public EasyCacheProvider getEasyCacheManager() {
        return easyCacheProvider;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }
    public CacheKeyFactory getCacheHashKeyFactory() {
        return cacheKeyFactory;
    }
}
