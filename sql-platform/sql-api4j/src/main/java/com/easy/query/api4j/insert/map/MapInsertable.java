package com.easy.query.api4j.insert.map;

import com.easy.query.core.basic.api.insert.Insertable;

import java.util.Collection;

/**
 * create time 2023/6/2 16:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapInsertable<T> extends Insertable<T, MapInsertable<T>> {
    @Override
    MapInsertable<T> insert(T entity);

    @Override
    MapInsertable<T> insert(Collection<T> entities);
}
