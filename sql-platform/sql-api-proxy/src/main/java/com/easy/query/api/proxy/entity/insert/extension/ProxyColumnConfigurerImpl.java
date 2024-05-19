package com.easy.query.api.proxy.entity.insert.extension;

import com.easy.query.core.expression.builder.Configurer;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2024/5/19 09:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyColumnConfigurerImpl<TProxy extends ProxyEntity<TProxy, T>,T> implements ProxyColumnConfigurer<TProxy,T> {
    private final Configurer configurer;

    public ProxyColumnConfigurerImpl(Configurer configurer){

        this.configurer = configurer;
    }
    @Override
    public Configurer getConfigurer() {
        return configurer;
    }
}
