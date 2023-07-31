package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.util.EasyObjectUtil;

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

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(filter);
    }

    @Override
    public ProxyFilter castTChain() {
        return this;
    }
}
