package com.easy.query.api.proxy.select.extension.queryable3.sql;

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
public class MultiProxyFilter3Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> implements MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy> {


    private final Filter filter;
    private final T1Proxy t;
    private final T2Proxy t1;
    private final T3Proxy t2;
    public MultiProxyFilter3Impl(Filter filter, T1Proxy t, T2Proxy t1, T3Proxy t2) {
        this.filter = filter;
        this.t = t;
        this.t1 = t1;
        this.t2 = t2;
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
    public T3Proxy t2() {
        return t2;
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
