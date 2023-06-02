package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 */
public class SQLColumnSelectorImpl<T1> implements SQLColumnSelector<T1> {
    private final ColumnSelector<T1> columnSelector;

    public SQLColumnSelectorImpl(ColumnSelector<T1> columnSelector) {
        this.columnSelector = columnSelector;
    }

    @Override
    public ColumnSelector<T1> getColumnSelector() {
        return columnSelector;
    }
}
