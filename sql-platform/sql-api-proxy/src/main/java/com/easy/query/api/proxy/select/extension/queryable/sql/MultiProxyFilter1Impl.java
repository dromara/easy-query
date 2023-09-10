package com.easy.query.api.proxy.select.extension.queryable.sql;

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
public class MultiProxyFilter1Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements MultiProxyFilter1<T1Proxy> {


    private final Filter filter;
    private final T1Proxy t;
    public MultiProxyFilter1Impl(Filter filter, T1Proxy t) {
        this.filter = filter;
        this.t = t;
    }
    @Override
    public T1Proxy t() {
        return t;
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
