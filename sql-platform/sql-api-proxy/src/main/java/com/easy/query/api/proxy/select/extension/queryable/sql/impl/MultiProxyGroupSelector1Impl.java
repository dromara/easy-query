package com.easy.query.api.proxy.select.extension.queryable.sql.impl;

import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyGroupSelector1;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/9/10 10:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiProxyGroupSelector1Impl<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements MultiProxyGroupSelector1<T1Proxy> {

    private final GroupSelector groupSelector;
    private final T1Proxy t;

    public MultiProxyGroupSelector1Impl(GroupSelector groupSelector, T1Proxy t) {
        this.groupSelector = groupSelector;
        this.t = t;
    }
    @Override
    public T1Proxy t() {
        return t;
    }

    @Override
    public GroupSelector getGroupSelector() {
        return groupSelector;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(groupSelector);
    }

    @Override
    public ProxyGroupSelector castTChain() {
        return this;
    }
}
