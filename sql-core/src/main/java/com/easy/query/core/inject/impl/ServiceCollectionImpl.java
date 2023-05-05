package com.easy.query.core.inject.impl;

import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.inject.ServiceDescriptor;
import com.easy.query.core.inject.ServiceProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/5/5 17:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class ServiceCollectionImpl implements ServiceCollection {
    private final Map<Class<?>/*interface*/, ServiceDescriptor/*impl*/> services = new HashMap<>();

    @Override
    public <TImplement> ServiceCollection addService(Class<TImplement> implementType) {
        services.put(implementType,new ServiceDescriptor(implementType,implementType));
        return this;
    }

    @Override
    public <TImplement> ServiceCollection addService(TImplement implementInstance) {
        services.put(implementInstance.getClass(),new ServiceDescriptor(implementInstance.getClass(),implementInstance));
        return this;
    }

    public <TService,TImplement extends TService> ServiceCollection addService(Class<TService> serviceType, Class<TImplement> implementType){
        services.put(serviceType,new ServiceDescriptor(serviceType,implementType));
        return this;
    }

    public <TService,TImplement extends TService> ServiceCollection addService(Class<TService> serviceType, TImplement implementInstance){
        services.put(serviceType,new ServiceDescriptor(serviceType,implementInstance));
        return this;
    }

    @Override
    public <TService, TImplement extends TService> ServiceCollection addServiceFactory(Class<TService> serviceType, Function<ServiceProvider, TImplement> implementFactory) {
        services.put(serviceType,new ServiceDescriptor(serviceType,(Function)implementFactory));
        return this;
    }

    @Override
    public ServiceProvider build() {
        return new ServiceProviderImpl(services);
    }


}
