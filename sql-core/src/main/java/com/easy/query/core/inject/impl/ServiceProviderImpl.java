package com.easy.query.core.inject.impl;

import com.easy.query.core.inject.BeanCurrentlyInjectMarker;
import com.easy.query.core.inject.ServiceDescriptor;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.util.EasyClassUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/5/5 17:30
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ServiceProviderImpl implements ServiceProvider {
    private final Map<Class<?>/*interface*/, ServiceDescriptor> servicesMapping;
    private final Map<Class<?>/*interface*/, Object> services;

    private final BeanCurrentlyInjectMarker beanCurrentlyInjectMarker;

    public ServiceProviderImpl(Map<Class<?>/*interface*/, ServiceDescriptor> servicesMapping) {
        this.servicesMapping = servicesMapping;
        this.beanCurrentlyInjectMarker = new BeanCurrentlyInjectMarker();
        services = new ConcurrentHashMap<>(servicesMapping.size() + 1);
        services.put(ServiceProvider.class, this);
    }

    public <T> T getService(Class<T> serviceType) {
        Object service = getServiceObject0(serviceType);
        return serviceType.cast(service);
    }

    public Object getServiceObject(Class<?> serviceType) {
        return getServiceObject0(serviceType);
    }

    private Object getServiceObject0(Class<?> serviceType) {
        Object service = services.get(serviceType);
        if (service == null) {
            ServiceDescriptor serviceDescriptor = servicesMapping.get(serviceType);
            if (serviceDescriptor == null) {
                throw new IllegalArgumentException("Service not found for type " + serviceType.getName());
            }
            Object o = resolveByServiceDescriptor(serviceDescriptor);
            beanCurrentlyInjectMarker.beanCreated(serviceDescriptor);
            services.putIfAbsent(serviceType, o);//services.computeIfAbsent() jdk 1.8如果嵌套使用的情况下key的hashcode一样会造成死锁

            service = services.get(serviceType);
        }
        return service;
    }

    private Object resolveByServiceDescriptor(ServiceDescriptor serviceDescriptor) {
        beanCurrentlyInjectMarker.beanCreateMark(serviceDescriptor);
        if (serviceDescriptor.getImplementationInstance() != null) {
            return serviceDescriptor.getImplementationInstance();
        }
        if (serviceDescriptor.getImplementationFactory() != null) {
            return serviceDescriptor.getImplementationFactory().apply(this);
        }
        if (serviceDescriptor.getImplementationType() != null) {
            return createInstance(serviceDescriptor.getImplementationType());
        }
        throw new IllegalArgumentException("cant to resolve service type " + EasyClassUtil.getSimpleName(serviceDescriptor.getServiceType()));
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
            throw new IllegalArgumentException("Failed to create instance of service type " + EasyClassUtil.getSimpleName(serviceType), e);
        }
    }
}
