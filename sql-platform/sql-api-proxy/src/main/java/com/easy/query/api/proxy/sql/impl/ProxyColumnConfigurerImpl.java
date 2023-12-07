package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyColumnConfigurer;
import com.easy.query.core.expression.builder.Configurer;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/12/7 13:34
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
