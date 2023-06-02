package com.easy.query.core.basic.api.insert;

import java.util.Collection;

/**
 * create time 2023/6/2 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientInsertable<T> extends Insertable<T, ClientInsertable<T>> {
    @Override
    ClientInsertable<T> insert(T entity);

    @Override
    default ClientInsertable<T> insert(Collection<T> entities) {
        if (entities == null) {
            return this;
        }
        for (T entity : entities) {
            insert(entity);
        }
        return this;
    }
}
