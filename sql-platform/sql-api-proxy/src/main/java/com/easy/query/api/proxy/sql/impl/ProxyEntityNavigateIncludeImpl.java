package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyEntityNavigateInclude;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/6 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyEntityNavigateIncludeImpl<T1,T1Proxy extends ProxyEntity<T1Proxy,T1>> implements ProxyEntityNavigateInclude<T1,T1Proxy> {
    private final T1Proxy t1Proxy;
    private final NavigateInclude<T1> navigateInclude;

    public ProxyEntityNavigateIncludeImpl(T1Proxy t1Proxy,NavigateInclude<T1> navigateInclude){
        this.t1Proxy = t1Proxy;

        this.navigateInclude = navigateInclude;
    }
    @Override
    public NavigateInclude<T1> getNavigateInclude() {
        return navigateInclude;
    }


    @Override
    public T1Proxy get1Proxy() {
        return t1Proxy;
    }

}
