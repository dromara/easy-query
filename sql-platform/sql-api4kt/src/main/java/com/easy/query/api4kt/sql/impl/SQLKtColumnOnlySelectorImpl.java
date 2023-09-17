package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

/**
 * create time 2023/6/16 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtColumnOnlySelectorImpl<T1> implements SQLKtColumnOnlySelector<T1> {
    private final ColumnOnlySelector<T1> columnSetSelector;

    public SQLKtColumnOnlySelectorImpl(ColumnOnlySelector<T1> columnSetSelector){

        this.columnSetSelector = columnSetSelector;
    }
    @Override
    public ColumnOnlySelector<T1> getColumnOnlySelector() {
        return columnSetSelector;
    }

}
