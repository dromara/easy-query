package com.easy.query.cache.core.bootstrapper;

import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.key.CacheKeyFactory;
import com.easy.query.cache.core.key.DefaultCacheKeyFactory;
import com.easy.query.cache.core.impl.DefaultEasyCacheClient;
import com.easy.query.cache.core.provider.DefaultCacheProvider;
import com.easy.query.cache.core.provider.EasyCacheProvider;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.inject.impl.ServiceCollectionImpl;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * create time 2024/1/25 15:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCacheBuilderConfiguration {
    private String name;
    private EasyCacheOption easyCacheOption = new EasyCacheOption();
    private final ServiceCollection serviceCollection = new ServiceCollectionImpl();

    public EasyCacheBuilderConfiguration() {
        defaultConfiguration();
    }

    public String getName() {
        return name;
    }

    public EasyCacheBuilderConfiguration setName(String name) {
        this.name = name;
        return this;
    }


    private void defaultConfiguration() {
        replaceService(CacheRuntimeContext.class)
                .replaceService(EasyCacheClient.class, DefaultEasyCacheClient.class)
                .replaceService(CacheKeyFactory.class, DefaultCacheKeyFactory.class)
                .replaceService(EasyCacheProvider.class, DefaultCacheProvider.class);
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param implementType 依赖注入当前实例类型
     * @param <TImplement>  实现类型
     * @return 当前服务集合
     */
    public <TImplement> EasyCacheBuilderConfiguration replaceService(Class<TImplement> implementType) {
        serviceCollection.addService(implementType);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param implementInstance 依赖注入当前实例
     * @param <TImplement>      实现类型
     * @return 当前服务集合
     */
    public <TImplement> EasyCacheBuilderConfiguration replaceService(TImplement implementInstance) {
        serviceCollection.addService(implementInstance);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType   依赖注入的接口
     * @param implementType 依赖注入的实现类
     * @param <TService>    接口类型
     * @param <TImplement>  实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyCacheBuilderConfiguration replaceService(Class<TService> serviceType, Class<TImplement> implementType) {
        serviceCollection.addService(serviceType, implementType);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType       依赖注入的接口
     * @param implementInstance 依赖注入的实现
     * @param <TService>        接口类型
     * @param <TImplement>      实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyCacheBuilderConfiguration replaceService(Class<TService> serviceType, TImplement implementInstance) {
        serviceCollection.addService(serviceType, implementInstance);
        return this;
    }

    /**
     * 添加服务如果已经存在则替换
     *
     * @param serviceType      依赖注入的接口
     * @param implementFactory 依赖注入的实现工厂
     * @param <TService>       接口类型
     * @param <TImplement>     实现类型
     * @return 当前服务集合
     */
    public <TService, TImplement extends TService> EasyCacheBuilderConfiguration replaceServiceFactory(Class<TService> serviceType, Function<ServiceProvider, TImplement> implementFactory) {
        serviceCollection.addServiceFactory(serviceType, implementFactory);
        return this;
    }

    public EasyCacheBuilderConfiguration optionConfigure(Consumer<EasyCacheOption> configure) {
        configure.accept(this.easyCacheOption);
        return this;
    }

    /**
     * 创建对应的查询器
     *
     * @return EasyQuery实例
     */
    public EasyCacheClient build() {
        replaceService(easyCacheOption);
        ServiceProvider serviceProvider = serviceCollection.build();
        return serviceProvider.getService(EasyCacheClient.class);
    }
}
