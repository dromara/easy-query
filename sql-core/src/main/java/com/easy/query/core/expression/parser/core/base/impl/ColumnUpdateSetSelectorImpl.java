package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.UpdateSetSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnUpdateSetSelector;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/25 21:31
 */
public class ColumnUpdateSetSelectorImpl<T> implements ColumnUpdateSetSelector<T> {
    private final UpdateSetSelector updateSetSelector;
    protected final TableAvailable table;

    public ColumnUpdateSetSelectorImpl(TableAvailable table, UpdateSetSelector updateSetSelector) {

        this.updateSetSelector = updateSetSelector;
        this.table = table;
    }

    @Override
    public UpdateSetSelector getUpdateSetSelector() {
        return updateSetSelector;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnUpdateSetSelector<T> column(String property) {
        updateSetSelector.column(table,property);
        return this;
    }

    @Override
    public ColumnUpdateSetSelector<T> columnIgnore(String property) {
        updateSetSelector.columnIgnore(table,property);
        return this;
    }
    @Override
    public ColumnUpdateSetSelector<T> columnAll() {
        updateSetSelector.columnAll(table);
        return this;
    }
}
