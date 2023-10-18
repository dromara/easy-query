package com.easy.query.api4kt.func.column;

import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtColumnFuncSelectorImpl<T> implements SQLKtColumnFuncSelector<T> {

    private final ColumnFuncSelector columnConcatSelector;

    public SQLKtColumnFuncSelectorImpl(ColumnFuncSelector columnConcatSelector){

        this.columnConcatSelector = columnConcatSelector;
    }

    @Override
    public ColumnFuncSelector getColumnFuncSelector() {
        return columnConcatSelector;
    }
}
