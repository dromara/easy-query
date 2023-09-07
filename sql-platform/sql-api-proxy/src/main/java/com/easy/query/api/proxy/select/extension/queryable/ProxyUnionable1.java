package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/8/17 11:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyUnionable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    default ProxyQueryable<T1Proxy, T1> union(ProxyQueryable<T1Proxy, T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default ProxyQueryable<T1Proxy, T1> union(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ProxyQueryable<T1Proxy, T1> union(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2, ProxyQueryable<T1Proxy, T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ProxyQueryable<T1Proxy, T1> union(Collection<ProxyQueryable<T1Proxy, T1>> unionQueries);

    default ProxyQueryable<T1Proxy, T1> unionAll(ProxyQueryable<T1Proxy, T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default ProxyQueryable<T1Proxy, T1> unionAll(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ProxyQueryable<T1Proxy, T1> unionAll(ProxyQueryable<T1Proxy, T1> unionQueryable1, ProxyQueryable<T1Proxy, T1> unionQueryable2, ProxyQueryable<T1Proxy, T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ProxyQueryable<T1Proxy, T1> unionAll(Collection<ProxyQueryable<T1Proxy, T1>> unionQueries);
}
