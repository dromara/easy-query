package com.easy.query.api4kt.insert;

import com.easy.query.core.basic.api.insert.Insertable;

import java.util.Collection;

/**
 * create time 2023/6/2 16:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface KtEntityInsertable<T> extends Insertable<T, KtEntityInsertable<T>> {
    @Override
    KtEntityInsertable<T> insert(T entity);

    @Override
    KtEntityInsertable<T> insert(Collection<T> entities);
}
