package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/8/17 11:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtUnionable1<T1> {
    default KtQueryable<T1> union(KtQueryable<T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default KtQueryable<T1> union(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default KtQueryable<T1> union(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2, KtQueryable<T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    KtQueryable<T1> union(Collection<KtQueryable<T1>> unionQueries);

    default KtQueryable<T1> unionAll(KtQueryable<T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default KtQueryable<T1> unionAll(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default KtQueryable<T1> unionAll(KtQueryable<T1> unionQueryable1, KtQueryable<T1> unionQueryable2, KtQueryable<T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    KtQueryable<T1> unionAll(Collection<KtQueryable<T1>> unionQueries);
}
