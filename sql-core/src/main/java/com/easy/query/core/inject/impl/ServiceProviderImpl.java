package com.easy.query.core.inject.impl;

import com.easy.query.core.inject.ServiceDescriptor;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.util.ClassUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/5/5 17:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ServiceProviderImpl implements ServiceProvider {
    private final Map<Class<?>/*interface*/, ServiceDescriptor/*impl*/> servicesMapping;
    private final Map<Class<?>/*interface*/, Object> services;

    public ServiceProviderImpl(Map<Class<?>/*interface*/, ServiceDescriptor/*impl*/> servicesMapping) {
        this.servicesMapping = servicesMapping;
        services=new ConcurrentHashMap<>(servicesMapping.size());
    }
    public <T> T getService(Class<T> serviceType) {
        System.out.println("创建:"+ClassUtil.getSimpleName(serviceType));
        Object service = services.get(serviceType);
        if (service == null) {
            ServiceDescriptor serviceDescriptor = servicesMapping.get(serviceType);
            if(serviceDescriptor==null){
                throw new IllegalArgumentException("Service not found for type " + serviceType.getName());
            }
            Object o = resolveByServiceDescriptor(serviceDescriptor);
            service=services.computeIfAbsent(serviceType,k->o);
        }
        return serviceType.cast(service);
    }
    public Object getServiceObject(Class<?> serviceType) {
        Object service = services.get(serviceType);
        if (service == null) {
            ServiceDescriptor serviceDescriptor = servicesMapping.get(serviceType);
            if(serviceDescriptor==null){
                throw new IllegalArgumentException("Service not found for type " + serviceType.getName());
            }
            service=services.computeIfAbsent(serviceType,k->resolveByServiceDescriptor(serviceDescriptor));
        }
        return service;
    }
    private Object resolveByServiceDescriptor(ServiceDescriptor serviceDescriptor){
        if(serviceDescriptor.getImplementationInstance()!=null){
            return serviceDescriptor.getImplementationInstance();
        }
        if(serviceDescriptor.getImplementationFactory()!=null){
            return serviceDescriptor.getImplementationFactory().apply(this);
        }
        if(serviceDescriptor.getImplementationType()!=null){
            return createInstance(serviceDescriptor.getImplementationType());
        }
        throw new IllegalArgumentException("cant to resolve service type " + ClassUtil.getSimpleName(serviceDescriptor.getServiceType()));
    }
    private Object createInstance(Class<?> serviceType) {
        try {
            Constructor<?>[] constructors = serviceType.getConstructors();
            if (constructors.length != 1) {
                throw new IllegalArgumentException("Service type " + serviceType.getName() + " must have a single public constructor.");
            }
            Constructor<?> constructor = constructors[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] arguments = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                arguments[i] = getService(parameterTypes[i]);
            }
            return serviceType.cast(constructor.newInstance(arguments));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Failed to create instance of service type " + ClassUtil.getSimpleName(serviceType), e);
        }
    }
}
