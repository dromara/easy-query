package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

/**
 * create time 2023/6/16 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnOnlySelectorImpl<T1> implements SQLColumnOnlySelector<T1> {
    private final ColumnOnlySelector<T1> columnSetSelector;

    public SQLColumnOnlySelectorImpl(ColumnOnlySelector<T1> columnSetSelector){

        this.columnSetSelector = columnSetSelector;
    }
    @Override
    public ColumnOnlySelector<T1> getColumnOnlySelector() {
        return columnSetSelector;
    }
}
