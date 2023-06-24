package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.core.expression.builder.Filter;

/**
 * create time 2023/6/22 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyFilterImpl implements ProxyFilter {
    private final Filter filter;

    public ProxyFilterImpl(Filter filter) {
        this.filter = filter;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

}
