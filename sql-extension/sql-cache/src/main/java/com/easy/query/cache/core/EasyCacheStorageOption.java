package com.easy.query.cache.core;

import com.easy.query.core.api.client.EasyQueryClient;

/**
 * create time 2024/1/24 20:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCacheStorageOption {
    private final EasyRedisManager easyRedisManager;
    private final EasyCacheOption easyCacheOption;
    private final EasyQueryClient easyQueryClient;

    public EasyCacheStorageOption(
            EasyRedisManager easyRedisManager,
            EasyCacheOption easyCacheOption,
            EasyQueryClient easyQueryClient) {
        this.easyRedisManager = easyRedisManager;
        this.easyCacheOption = easyCacheOption;
        this.easyQueryClient = easyQueryClient;
    }

    public EasyRedisManager getRedisManager() {
        return easyRedisManager;
    }

    public EasyCacheOption getCacheOption() {
        return easyCacheOption;
    }

    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }
}
