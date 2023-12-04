package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
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
public interface EntityUnionable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    default EntityQueryable<T1Proxy, T1> union(EntityQueryable<T1Proxy, T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default EntityQueryable<T1Proxy, T1> union(EntityQueryable<T1Proxy, T1> unionQueryable1, EntityQueryable<T1Proxy, T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default EntityQueryable<T1Proxy, T1> union(EntityQueryable<T1Proxy, T1> unionQueryable1, EntityQueryable<T1Proxy, T1> unionQueryable2, EntityQueryable<T1Proxy, T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    EntityQueryable<T1Proxy, T1> union(Collection<EntityQueryable<T1Proxy, T1>> unionQueries);

    default EntityQueryable<T1Proxy, T1> unionAll(EntityQueryable<T1Proxy, T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default EntityQueryable<T1Proxy, T1> unionAll(EntityQueryable<T1Proxy, T1> unionQueryable1, EntityQueryable<T1Proxy, T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default EntityQueryable<T1Proxy, T1> unionAll(EntityQueryable<T1Proxy, T1> unionQueryable1, EntityQueryable<T1Proxy, T1> unionQueryable2, EntityQueryable<T1Proxy, T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    EntityQueryable<T1Proxy, T1> unionAll(Collection<EntityQueryable<T1Proxy, T1>> unionQueries);
}
