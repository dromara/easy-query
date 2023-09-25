package com.easy.query.api.proxy.sql.expression.impl;

import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.expression.MultiProxyFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/17 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxyFilterImpl<T1Proxy extends ProxyEntity<T1Proxy,T1>,T1> implements MultiProxyFilter<T1Proxy> {
    private final T1Proxy t1Proxy;
    private final Filter filter;

    public MultiProxyFilterImpl(T1Proxy t1Proxy, Filter filter){
        this.t1Proxy = t1Proxy;

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
    public ProxyFilter castChain() {
        return this;
    }

    @Override
    public T1Proxy t() {
        return t1Proxy;
    }
}
