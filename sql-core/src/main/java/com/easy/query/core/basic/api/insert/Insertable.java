package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.abstraction.SqlExecuteRows;

import java.util.Collection;

/**
 * @FileName: Insertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 08:48
 * @Created by xuejiaming
 */
public interface Insertable<T> extends SqlExecuteRows {
    Insertable<T> insert(T entity);

    default Insertable<T> insert(Collection<T> entities) {
        for (T entity : entities) {
            insert(entity);
        }
        return this;
    }

    String toSql();
}
