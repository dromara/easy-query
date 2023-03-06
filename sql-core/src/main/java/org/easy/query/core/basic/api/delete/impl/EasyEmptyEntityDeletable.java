package org.easy.query.core.basic.api.delete.impl;

import org.easy.query.core.basic.api.delete.EntityDeletable;

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
    public void executeRows(Long expectRow, String error) {

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
