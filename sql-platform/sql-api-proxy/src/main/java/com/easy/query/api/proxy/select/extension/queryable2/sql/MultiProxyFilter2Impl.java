package com.easy.query.api.proxy.select.extension.queryable2.sql;

import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/9 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxyFilter2Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> implements MultiProxyFilter2<T1Proxy, T2Proxy> {


    private final Filter filter;
    private final T1Proxy t;
    private final T2Proxy t1;
    public MultiProxyFilter2Impl(Filter filter, T1Proxy t, T2Proxy t1) {
        this.filter = filter;
        this.t = t;
        this.t1 = t1;
    }
    @Override
    public T1Proxy t() {
        return t;
    }

    @Override
    public T2Proxy t1() {
        return t1;
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
