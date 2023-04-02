package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.SqlExecuteRows;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Queryable;

import java.util.Collection;
import java.util.function.Function;

/**
 * @FileName: Insertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 08:48
 * @author xuejiaming
 */
public interface Insertable<T> extends SqlExecuteRows, Interceptable<Insertable<T>>, TableReNameable<Insertable<T>> {
    Insertable<T> insert(T entity);

    default Insertable<T> insert(Collection<T> entities) {
        for (T entity : entities) {
            insert(entity);
        }
        return this;
    }

    /**
     *
     * @param fillAutoIncrement
     * @return
     */
    long executeRows(boolean fillAutoIncrement);

    @Override
    default long executeRows(){
        return executeRows(false);
    }

    String toSql();
}
