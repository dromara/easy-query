package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.SQLExecuteRows;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: Insertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 08:48
 */
public interface Insertable<T, TChain> extends SQLExecuteRows, Interceptable<TChain>, TableReNameable<TChain>, SQLExecuteStrategy<TChain> {
    TChain insert(T entity);

    TChain insert(Collection<T> entities);

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
    String toSQL(Object entity);
}
