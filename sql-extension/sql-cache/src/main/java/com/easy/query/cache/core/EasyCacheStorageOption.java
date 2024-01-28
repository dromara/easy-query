package com.easy.query.cache.core;

import com.easy.query.core.api.client.EasyQueryClient;

/**
 * create time 2024/1/24 20:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCacheStorageOption {
    private final EasyCacheManager easyCacheManager;
    private final EasyCacheOption easyCacheOption;
    private final EasyQueryClient easyQueryClient;

    public EasyCacheStorageOption(
            EasyCacheManager easyCacheManager,
            EasyCacheOption easyCacheOption,
            EasyQueryClient easyQueryClient) {
        this.easyCacheManager = easyCacheManager;
        this.easyCacheOption = easyCacheOption;
        this.easyQueryClient = easyQueryClient;
    }

    public EasyCacheManager getRedisManager() {
        return easyCacheManager;
    }

    public EasyCacheOption getCacheOption() {
        return easyCacheOption;
    }

    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }
}
