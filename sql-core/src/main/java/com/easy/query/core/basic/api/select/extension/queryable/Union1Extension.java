package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/8/17 11:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Union1Extension<T1> {
    default ClientQueryable<T1> union(ClientQueryable<T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default ClientQueryable<T1> union(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ClientQueryable<T1> union(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2, ClientQueryable<T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ClientQueryable<T1> union(Collection<ClientQueryable<T1>> unionQueries);

    default ClientQueryable<T1> unionAll(ClientQueryable<T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default ClientQueryable<T1> unionAll(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default ClientQueryable<T1> unionAll(ClientQueryable<T1> unionQueryable1, ClientQueryable<T1> unionQueryable2, ClientQueryable<T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    ClientQueryable<T1> unionAll(Collection<ClientQueryable<T1>> unionQueries);
}
