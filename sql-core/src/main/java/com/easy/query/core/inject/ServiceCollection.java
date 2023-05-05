package com.easy.query.core.inject;

import java.util.function.Function;

/**
 * create time 2023/5/5 17:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ServiceCollection {
    <TImplement> ServiceCollection addService(Class<TImplement> implementType);
    <TImplement> ServiceCollection addService(TImplement implementInstance);
    <TService,TImplement extends TService> ServiceCollection addService(Class<TService> serviceType, Class<TImplement> implementType);
    <TService,TImplement extends TService> ServiceCollection addService(Class<TService> serviceType, TImplement implementInstance);
    <TService,TImplement extends TService> ServiceCollection addServiceFactory(Class<TService> serviceType, Function<ServiceProvider,TImplement> implementFactory);
    ServiceProvider build();
}
