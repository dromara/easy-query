package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;

/**
 * create time 2023/9/6 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyNavigateIncludeImpl<T1> implements ProxyNavigateInclude<T1> {
    private final NavigateInclude<T1> navigateInclude;

    public ProxyNavigateIncludeImpl(NavigateInclude<T1> navigateInclude){

        this.navigateInclude = navigateInclude;
    }
    @Override
    public NavigateInclude<T1> getNavigateInclude() {
        return navigateInclude;
    }
}
