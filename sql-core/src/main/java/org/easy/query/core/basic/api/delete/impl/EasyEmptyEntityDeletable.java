package org.easy.query.core.basic.api.delete.impl;

import org.easy.query.core.basic.api.delete.EntityDeletable;
import org.easy.query.core.exception.EasyQueryConcurrentException;

/**
 * @FileName: EasyEmptyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:22
 * @Created by xuejiaming
 */
public class EasyEmptyEntityDeletable<T> implements EntityDeletable<T> {
    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if(rows!=expectRows){
            throw new EasyQueryConcurrentException(msg,code);
        }
    }

    @Override
    public String toSql() {
        return null;
    }

    @Override
    public EntityDeletable<T> disableLogicDelete() {
        return this;
    }
}
