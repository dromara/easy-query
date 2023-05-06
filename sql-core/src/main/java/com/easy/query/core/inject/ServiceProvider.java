package com.easy.query.core.inject;

/**
 * create time 2023/5/5 17:29
 * easy-query内置的简单服务提供器
 *
 * @author xuejiaming
 */
public interface ServiceProvider {
    /**
     * 返回注册的服务,仅支持单例服务
     * @param serviceType 注册服务类型
     * @return 服务实现实例
     * @param <T> 注册类型
     */
     <T> T getService(Class<T> serviceType);

    /**
     *  返回注册的服务,仅支持单例服务
     * @param serviceType 注册服务类型
     * @return 服务实现实例
     */
    Object getServiceObject(Class<?> serviceType);
}
