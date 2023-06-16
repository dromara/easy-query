package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtColumnSetSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSetSelector;

/**
 * create time 2023/6/16 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtColumnSetSelectorImpl<T1> implements SQLKtColumnSetSelector<T1> {
    private final ColumnSetSelector<T1> columnSetSelector;

    public SQLKtColumnSetSelectorImpl(ColumnSetSelector<T1> columnSetSelector){

        this.columnSetSelector = columnSetSelector;
    }
    @Override
    public ColumnSetSelector<T1> getColumnSetSelector() {
        return columnSetSelector;
    }
}
