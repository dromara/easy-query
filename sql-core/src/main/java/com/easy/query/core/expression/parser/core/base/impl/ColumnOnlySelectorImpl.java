package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/2/25 21:31
 */
public class ColumnOnlySelectorImpl<T> implements ColumnOnlySelector<T> {
    private final OnlySelector onlySelector;
    protected final TableAvailable table;

    public ColumnOnlySelectorImpl(TableAvailable table, OnlySelector updateSetSelector) {

        this.onlySelector = updateSetSelector;
        this.table = table;
    }

    @Override
    public OnlySelector getOnlySelector() {
        return onlySelector;
    }

    @Override
    public ColumnOnlySelector<T> columnKeys() {
         onlySelector.columnKeys(table);
        return this;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnOnlySelector<T> column(String property) {
        onlySelector.column(table,property);
        return this;
    }

    @Override
    public ColumnOnlySelector<T> columnNull(String property) {
        onlySelector.columnNull(table,property);
        return this;
    }

    @Override
    public ColumnOnlySelector<T> columnIgnore(String property) {
        onlySelector.columnIgnore(table,property);
        return this;
    }
    @Override
    public ColumnOnlySelector<T> columnAll() {
        onlySelector.columnAll(table);
        return this;
    }
}
