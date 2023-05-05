package com.easy.query.core.inject;

import com.easy.query.core.util.ClassUtil;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/5/5 21:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class ServiceDescriptor {
    private final Class<?> serviceType;
    private final Class<?> implementationType;
    private final Object implementationInstance;
    private final Function<ServiceProvider,Object> implementationFactory;

    public ServiceDescriptor(Class<?> serviceType, Class<?> implementationType) {

        if(serviceType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(serviceType));
        }
        this.serviceType = serviceType;
        if(implementationType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(implementationType));
        }
        this.implementationType = implementationType;
        this.implementationInstance = null;
        this.implementationFactory = null;
    }
    public ServiceDescriptor(Class<?> serviceType, Object implementationInstance) {

        if(serviceType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(serviceType));
        }
        this.serviceType = serviceType;
        if(implementationInstance==null){
            throw new IllegalArgumentException(ClassUtil.getInstanceSimpleName(implementationInstance));
        }
        this.implementationInstance = implementationInstance;
        this.implementationType = null;
        this.implementationFactory = null;
    }
    public ServiceDescriptor(Class<?> serviceType, Function<ServiceProvider,Object> implementationFactory) {

        if(serviceType==null){
            throw new IllegalArgumentException(ClassUtil.getSimpleName(serviceType));
        }
        this.serviceType = serviceType;
        if(implementationFactory==null){
            throw new IllegalArgumentException(ClassUtil.getInstanceSimpleName(implementationFactory));
        }
        this.implementationFactory = implementationFactory;
        this.implementationType = null;
        this.implementationInstance = null;
    }

    public Class<?> getServiceType() {
        return serviceType;
    }

    public Class<?> getImplementationType() {
        return implementationType;
    }

    public Object getImplementationInstance() {
        return implementationInstance;
    }

    public Function<ServiceProvider,Object> getImplementationFactory() {
        return implementationFactory;
    }
}
