package com.easy.query.core.inject;

import java.util.function.Function;

/**
 * create time 2023/5/5 17:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ServiceCollection {
    /**
     * 添加服务如果已经存在则替换
     * @param implementType 依赖注入当前实例类型
     * @return 当前服务集合
     * @param <TImplement> 实现类型
     */
    <TImplement> ServiceCollection addService(Class<TImplement> implementType);

    /**
     * 添加服务如果已经存在则替换
     * @param implementInstance 依赖注入当前实例
     * @return 当前服务集合
     * @param <TImplement> 实现类型
     */
    <TImplement> ServiceCollection addService(TImplement implementInstance);

    /**
     * 添加服务如果已经存在则替换
     * @param serviceType 依赖注入的接口
     * @param implementType 依赖注入的实现类
     * @return 当前服务集合
     * @param <TService> 接口类型
     * @param <TImplement> 实现类型
     */
    <TService,TImplement extends TService> ServiceCollection addService(Class<TService> serviceType, Class<TImplement> implementType);

    /**
     * 添加服务如果已经存在则替换
     * @param serviceType 依赖注入的接口
     * @param implementInstance 依赖注入的实现
     * @return 当前服务集合
     * @param <TService> 接口类型
     * @param <TImplement> 实现类型
     */
    <TService,TImplement extends TService> ServiceCollection addService(Class<TService> serviceType, TImplement implementInstance);

    /**
     * 添加服务如果已经存在则替换
     * @param serviceType 依赖注入的接口
     * @param implementFactory 依赖注入的实现工厂
     * @return 当前服务集合
     * @param <TService> 接口类型
     * @param <TImplement> 实现类型
     */
    <TService,TImplement extends TService> ServiceCollection addServiceFactory(Class<TService> serviceType, Function<ServiceProvider,TImplement> implementFactory);

    /**
     * 创建服务提供者
     * @return 服务提供者拥有获取注册服务的实例的功能
     */
    ServiceProvider build();
}
