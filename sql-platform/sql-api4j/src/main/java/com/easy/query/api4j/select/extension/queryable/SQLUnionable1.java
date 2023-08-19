package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/8/17 11:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLUnionable1<T1> {
    default Queryable<T1> union(Queryable<T1> unionQueryable) {
        return union(Collections.singletonList(unionQueryable));
    }

    default Queryable<T1> union(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default Queryable<T1> union(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2, Queryable<T1> unionQueryable3) {
        return union(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    Queryable<T1> union(Collection<Queryable<T1>> unionQueries);

    default Queryable<T1> unionAll(Queryable<T1> unionQueryable) {
        return unionAll(Collections.singletonList(unionQueryable));
    }

    default Queryable<T1> unionAll(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2));
    }

    default Queryable<T1> unionAll(Queryable<T1> unionQueryable1, Queryable<T1> unionQueryable2, Queryable<T1> unionQueryable3) {
        return unionAll(Arrays.asList(unionQueryable1, unionQueryable2, unionQueryable3));
    }

    Queryable<T1> unionAll(Collection<Queryable<T1>> unionQueries);
}
