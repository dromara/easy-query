package com.easy.query.api4j.func.column;

import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnFuncSelectorImpl<T> implements SQLColumnFuncSelector<T> {

    private final ColumnFuncSelector columnConcatSelector;

    public SQLColumnFuncSelectorImpl(ColumnFuncSelector columnConcatSelector){

        this.columnConcatSelector = columnConcatSelector;
    }

    @Override
    public ColumnFuncSelector getColumnFuncSelector() {
        return columnConcatSelector;
    }
}
